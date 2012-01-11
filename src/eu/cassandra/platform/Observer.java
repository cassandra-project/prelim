package eu.cassandra.platform;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import eu.cassandra.entities.appliances.Appliance;
import eu.cassandra.entities.installations.Installation;
import eu.cassandra.entities.people.Activity;
import eu.cassandra.entities.people.Person;
import eu.cassandra.platform.utilities.Constants;
import eu.cassandra.platform.utilities.Params;
import eu.cassandra.platform.utilities.RNG;
import eu.cassandra.platform.utilities.Registry;
import eu.cassandra.platform.utilities.Utils;

public class Observer implements Runnable {
	
	static Logger logger = Logger.getLogger(Observer.class);

	private Vector<Installation> installations = new Vector<Installation>();

	private long tick = 0;

	private long endTick = Constants.MIN_IN_DAY * 3;

	private Registry registry = new Registry("group");

	public Observer() {
		PropertyConfigurator.configure(Params.LOG_CONFIG_FILE);
		// Read number of installations and create them
		int numOfInstallations = Utils.getInt(Params.DEMOG_PROPS, "installations");
		// Read the different kinds of appliances
		String[] appliances = Utils.getStringArray(Params.APPS_PROPS, "appliances");
		// Read appliances statistics
		double[] ownershipPerc = new double[appliances.length];
		for(int i = 0; i < appliances.length; i++) {
			ownershipPerc[i] = 
					Utils.getDouble(Params.DEMOG_PROPS, appliances[i]+".perc");
		}
		for(int i = 0; i < numOfInstallations; i++) {
			// Make the installation
			Installation inst = new Installation.Builder(i+"").
					registry(new Registry(i+"")).build();
			// Create the appliances
			for(int j = 0; j < appliances.length; j++) {
				double dice = RNG.nextDouble();
				if(dice < ownershipPerc[j]) {
					Appliance app = 
							new Appliance.Builder(appliances[j], inst).build();
					String regName = inst.getId() + "." + app.getName() + "." +
							app.getId();
//					app.createRegistry(new Registry(regName));
					inst.addAppliance(app);
				}
			}
			// Create a person
			Person person = new Person(); // TODO Builder pattern
			Vector<Activity> activities = 
					Activity.availableActivities(inst.getAppliances());
			for(Activity a : activities) {
				person.addActivity(a);
			}
			inst.addPerson(person);
			installations.add(inst);
		}
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
			Iterator<Installation> iter = installations.iterator();
			// Calculate the next step of the installations
			while(iter.hasNext()) {
				Installation installation = iter.next();
				installation.nextStep(tick);
			}
			// Gather information about the installations
			Iterator<Installation> iter2 = installations.iterator();
			double sumPower = 0;
			while(iter2.hasNext()) {
				Installation installation = iter2.next();
				double power = installation.getCurrentPower();
				String name = installation.getName();
				sumPower += power;
				logger.info("Tick: " + tick + " \t " + "Name: " + name + 
						" \t " + "Power: " + power);
			}
			registry.add(sumPower);
			tick++;
		}
	}

	public void flushRegistriesOnFiles() {
		File folder = new File(Params.REGISTRIES_DIR + 
				Calendar.getInstance().getTimeInMillis() + "/");
		createFolderStucture(folder);

		//Flush installations and appliances
		for(Installation installation : installations) {
//			saveRegistry(folder,installation.getRegistry());

			for(Appliance appliance : installation.getAppliances()) {
//				saveRegistry(folder, appliance.getRegistry());
			}
		}
		saveRegistry(folder,registry);
	}

	/**
	 * Create folder structure
	 * 
	 * @param folder
	 */
	private void createFolderStucture(File folder) {
		File tempFolder = new File(folder.getPath());
		Vector<File> folders = 	new Vector<File>();
		while(tempFolder.getParentFile() != null && 
				tempFolder.getParent() != tempFolder.getPath()) {
			folders.add(tempFolder);
			tempFolder = tempFolder.getParentFile();
		}
		if(folders != null && folders.size() > 0) {
			if(!folders.get(0).getParentFile().exists())
				folders.get(0).getParentFile().mkdir();
		}
		for(int i = folders.size()-1; i>=0; i--) {
			if(!folders.get(i).exists())
				folders.get(i).mkdir();
		}
	}

	private void saveRegistry(File parrentFolder, Registry registry) {
		try {
			File file = new File(parrentFolder.getPath() + "/" + 
					registry.getName() + ".csv");
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufWriter = new BufferedWriter(fileWriter);
			for(int i =0; i<registry.getValues().size(); i++) {
				bufWriter.write(i + "," + registry.getValues().get(i));
				bufWriter.newLine();
			}
			bufWriter.flush();
			bufWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
