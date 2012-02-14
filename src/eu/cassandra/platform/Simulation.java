package eu.cassandra.platform;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.log4j.Logger;

import eu.cassandra.entities.appliances.Appliance;
import eu.cassandra.entities.installations.Installation;
import eu.cassandra.entities.people.Activity;
import eu.cassandra.entities.people.Person;
import eu.cassandra.platform.math.Gaussian;
import eu.cassandra.platform.math.ProbabilityDistribution;
import eu.cassandra.platform.utilities.Constants;
import eu.cassandra.platform.utilities.Params;
import eu.cassandra.platform.utilities.RNG;
import eu.cassandra.platform.utilities.Registry;
import eu.cassandra.platform.utilities.FileUtils;

/**
 * The Simulation class can simulate up to 4085 years of simulation.
 * 
 * @author Kyriakos C. Chatzidimitriou (kyrcha [at] iti [dot] gr)
 *
 */
public class Simulation implements Runnable {
	
	static Logger logger = Logger.getLogger(Simulation.class);

	private Vector<Installation> installations;
	
	private PriorityBlockingQueue<Event> queue;

	private int tick = 0;

	private int endTick; 

	private Registry registry;
	
	public Collection<Installation> getInstallations() {
		return installations;
	}
	
	public Installation getInstallation(int index) {
		return installations.get(index);
	}
	
	public int getCurrentTick() {
		return tick;
	}
	
	public int getEndTick() {
		return endTick;
	}
	
	public Registry getRegistry() {
		return registry;
	}

	public void simulate() {
		Thread t = new Thread(this);
		try {
			t.start();
	        t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}

	public void run() {
		while(tick < endTick) {
			// If it is the beginning of the day create the events
			if(tick % Constants.MIN_IN_DAY == 0) {
				logger.info("Day " + ((tick / Constants.MIN_IN_DAY) + 1));
				for(Installation installation : installations) {
					installation.updateDailySchedule(tick, queue);
				}
				logger.info("Daily queue size: " + queue.size() + 
						"(" + SimCalendar.isWeekend(tick) +")");
			}
			Event top = queue.peek();
			while(top != null && top.getTick() == tick) {
				Event e = queue.poll();
				e.apply();
				top = queue.peek();
			}
			/*
			 *  Calculate the total power for this simulation step for all the
			 *  installations.
			 */
			float sumPower = 0;
			for(Installation installation : installations) {
				installation.nextStep(tick);
				float power = installation.getPower(tick);
				sumPower += power;
				String name = installation.getName();
				logger.trace("Tick: " + tick + " \t " + "Name: " + name + 
						" \t " + "Power: " + power);
			}
			registry.setValue(tick, sumPower);
			tick++;
		}
	}

	/**
	 * Flush the contents of registers to the file system.
	 */
	public void flush() {
		File folder = new File(Params.REGISTRIES_DIR + 
				Calendar.getInstance().getTimeInMillis() + "/");
		FileUtils.createFolderStucture(folder);
		// Flush installations and appliances
		for(Installation installation : installations) {
			installation.getRegistry().saveRegistry(folder);
		}
		registry.saveRegistry(folder);
	}
	
	public void setup() {
		installations = new Vector<Installation>();
		logger.info("Simulation setup started.");
		// Initialize simulation variables
		int numOfDays = FileUtils.getInt(Params.SIM_PROPS, "days");
		int numOfInstallations = 
				FileUtils.getInt(Params.SIM_PROPS, "installations");
		endTick = Constants.MIN_IN_DAY * numOfDays;
		registry = new Registry("Total", endTick);
		queue = new  PriorityBlockingQueue<Event>(2 * numOfInstallations);
		
		// Read the different kinds of appliances
		String[] appliances = 
				FileUtils.getStringArray(Params.APPS_PROPS, "appliances");
		// Read appliances statistics
		double[] ownershipPerc = new double[appliances.length];
		for(int i = 0; i < appliances.length; i++) {
			ownershipPerc[i] = 
					FileUtils.getDouble(Params.DEMOG_PROPS, 
							appliances[i]+".perc");
		}
		// Create the installations and put appliances inside
		for(int i = 0; i < numOfInstallations; i++) {
			// Make the installation
			Installation inst = new Installation.Builder(i, i+"").
					registry(new Registry(i+"", endTick)).build();
			// Create the appliances
			for(int j = 0; j < appliances.length; j++) {
				double dice = RNG.nextDouble();
				if(dice < ownershipPerc[j]) {
					Appliance app = 
							new Appliance.Builder(appliances[j], inst).build();
					inst.addAppliance(app);
					logger.trace(i + " " + appliances[j]);
				}
			}
			installations.add(inst);
		}
		// Load possible activities
		String[] activities = 
				FileUtils.getStringArray(Params.ACT_PROPS, "activities");
		// Put persons inside installations along with activities
		for(int i = 0; i < numOfInstallations; i++) {
			Installation inst = installations.get(i);
			int type = RNG.nextInt(3) + 1;
			Person person = 
					new Person.Builder("Person " + i, type, inst).build();
			inst.addPerson(person);
			for(int j = 0; j < activities.length; j++) {
				String[] appsNeeded = 
						FileUtils.getStringArray(Params.ACT_PROPS, 
								activities[j]+".apps");
				Vector<Appliance> existing = new Vector<Appliance>();
				for(int k = 0; k < appsNeeded.length; k++) {
					Appliance a = inst.applianceExists(appsNeeded[k]);
					if(a != null) {
						existing.add(a);
					}
				}
				if(existing.size() > 0) {
					logger.trace(i + " " + activities[j]);
					double mu = FileUtils.getDouble(
									Params.ACT_PROPS, 
									activities[j]+".startTime.mu."+type);
					double sigma = FileUtils.getDouble(
									Params.ACT_PROPS, 
									activities[j]+".startTime.sigma."+type);
					ProbabilityDistribution start = new Gaussian(mu, sigma);
					start.precompute(0, 1439, 1440);
					
					mu = FileUtils.getDouble(
									Params.ACT_PROPS, 
									activities[j]+".duration.mu."+type);
					sigma = FileUtils.getDouble(
									Params.ACT_PROPS, 
									activities[j]+".duration.sigma."+type);
					ProbabilityDistribution duration = new Gaussian(mu, sigma);
					duration.precompute(1, 1439, 1439);
					
					mu = FileUtils.getDouble(
							Params.ACT_PROPS, 
							activities[j]+".weekday.mu."+type);
					sigma = FileUtils.getDouble(
							Params.ACT_PROPS, 
							activities[j]+".weekday.sigma."+type);
					ProbabilityDistribution weekday = new Gaussian(mu, sigma);
					weekday.precompute(0, 3, 4);
					
					mu = FileUtils.getDouble(
							Params.ACT_PROPS, 
							activities[j]+".weekend.mu."+type);
					sigma = FileUtils.getDouble(
							Params.ACT_PROPS, 
							activities[j]+".weekend.sigma."+type);
					ProbabilityDistribution weekend = new Gaussian(mu, sigma);
					weekend.precompute(0, 3, 4);
					
					Activity act = new Activity.Builder(
							activities[j], 
							start, 
							duration).
							times("weekday", weekday).
							times("weekend", weekend).
							build();
					for(Appliance e : existing) {
						act.addAppliance(e, 1.0 / existing.size());
					}
					person.addActivity(act);
				}
			}
		}
		logger.info("Simulation setup finished.");
	}
	
}
