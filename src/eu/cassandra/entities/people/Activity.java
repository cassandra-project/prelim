package eu.cassandra.entities.people;

import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.log4j.Logger;

import eu.cassandra.entities.appliances.Appliance;
import eu.cassandra.platform.Event;
import eu.cassandra.platform.SimCalendar;
import eu.cassandra.platform.math.ProbabilityDistribution;
import eu.cassandra.platform.utilities.RNG;
import eu.cassandra.platform.utilities.Utils;

public class Activity {
	
	static Logger logger = Logger.getLogger(Activity.class);
	
	private final String name;
	private final HashMap<String, ProbabilityDistribution> nTimesGivenDay;
	private final ProbabilityDistribution probStartTime;
	private final ProbabilityDistribution probDuration;
	private Vector<Appliance> appliances;
	private Vector<Double> probApplianceUsed;
    public static class Builder {
    	// Required parameters
    	private final String name;
    	private final HashMap<String, ProbabilityDistribution> nTimesGivenDay;
    	private final ProbabilityDistribution probStartTime;
    	private final ProbabilityDistribution probDuration;
        // Optional parameters: not available    	
    	private Vector<Appliance> appliances;
    	private Vector<Double> probApplianceUsed;
        public Builder(String aname, ProbabilityDistribution start, 
        		ProbabilityDistribution duration) {
        	name = aname;
        	probStartTime = start;
        	probDuration = duration;
        	appliances = new Vector<Appliance>();
        	probApplianceUsed = new Vector<Double>();
        	nTimesGivenDay = new HashMap<String,ProbabilityDistribution>();
        }
        public Builder appliances(Appliance... apps) {
        	for(Appliance app : apps) {
        		appliances.add(app);
        	}
        	return this;
        }
        public Builder times(String day, ProbabilityDistribution timesPerDay) {
        	nTimesGivenDay.put(day, timesPerDay);
        	return this;
        }
        public Builder applianceUsed(Double... probs) {
        	for(Double prob : probs) {
        		probApplianceUsed.add(prob);
        	}
        	return this;
        }
        public Activity build() {
        	return new Activity(this);
        }
    }
    private Activity(Builder builder) {
    	name = builder.name;
    	appliances = builder.appliances;
    	nTimesGivenDay = builder.nTimesGivenDay;
    	probStartTime = builder.probStartTime;
    	probDuration = builder.probDuration;
    	probApplianceUsed = builder.probApplianceUsed;
	}
    
    public void addAppliance(Appliance a, Double prob) {
    	appliances.add(a);
    	probApplianceUsed.add(prob);
    }
    
    public String getName() {
    	return name;
    }
    
    public void updateDailySchedule(int tick,  PriorityBlockingQueue<Event> queue) {
    	/*
    	 *  Decide on the number of times the activity is going to be activated
    	 *  during a day
    	 */
    	ProbabilityDistribution numOfTimesProb;
    	if(SimCalendar.isWeekend(tick)) {
    		numOfTimesProb = nTimesGivenDay.get("weekend");
    	} else {
    		numOfTimesProb = nTimesGivenDay.get("weekday");
    	}
    	
    	int numOfTimes = numOfTimesProb.getPrecomputedBin();
    	logger.trace(numOfTimes);
    	/*
    	 * Decide the duration and start time for each activity activation
    	 */
    	while(numOfTimes > 0) {
    		int duration = Math.max(probDuration.getPrecomputedBin(), 1);
    		int startTime = probStartTime.getPrecomputedBin();
    		// Select appliances to be switched on
    		for(int j = 0; j < appliances.size(); j++) {
    			if(RNG.nextDouble() < probApplianceUsed.get(j).doubleValue()) {
    				Appliance a = appliances.get(j);
    				int appDuration = duration;
    				int appStartTime = startTime;
    				String hash = 
    						Utils.hashcode((
    								new Long(RNG.nextLong()).toString()));
    				Event eOn = 
    						new Event(tick + appStartTime, 
    								Event.SWITCH_ON, 
    								a,
    								hash);
    				queue.offer(eOn);
    				Event eOff = 
    						new Event(tick + appStartTime + appDuration, 
    								Event.SWITCH_OFF, 
    								a,
    								hash);
    				queue.offer(eOff);
    			}
    		}
    		numOfTimes--;
    	}
    }
    
}
