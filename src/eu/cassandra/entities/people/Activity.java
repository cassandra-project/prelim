package eu.cassandra.entities.people;

import java.util.HashMap;
import java.util.Vector;

import eu.cassandra.entities.appliances.Appliance;
import eu.cassandra.platform.math.Distribution;
import eu.cassandra.platform.math.Gaussian;
import eu.cassandra.platform.utilities.Constants;
import eu.cassandra.platform.utilities.RNG;

public class Activity {
    private final double onPrior;
    private final double timePrior;
    private final Distribution onDistribution;
    private final Distribution offDistribution;
    private final Vector<Appliance> appliances;
    private boolean started;
    private long startedTick;
    public static class Builder {
    	// Required parameters
    	private final double onPrior;
        private final double timePrior;
        private final Distribution onDistribution;
        private final Distribution offDistribution;
        private final Vector<Appliance> appliances;
        // Optional parameters
        private boolean started = false;
        private long startedTick = -1;
        public Builder(double aonPrior, double atimePrior, Distribution onDist,
        		Distribution offDist) {
        	onPrior = aonPrior;
        	timePrior = atimePrior;
        	onDistribution = onDist;
        	offDistribution = offDist;
        	appliances = new Vector<Appliance>();
        }
        public Builder appliances(Appliance... apps) {
        	for(Appliance app : apps) {
        		appliances.add(app);
        	}
        	return this;
        }
        public Activity build() {
        	return new Activity(this);
        }
    }
    private Activity(Builder builder) {
    	onPrior = builder.onPrior;
    	timePrior = builder.timePrior;
    	onDistribution = builder.onDistribution;
    	offDistribution = builder.offDistribution;
    	appliances = builder.appliances;
    	started = builder.started;
    	startedTick = builder.startedTick;
	}

    public boolean isInitialized() {
        return started;
    }

    public Distribution getOnDistribution() {
        return onDistribution;
    }

    public Distribution getOffDistribution() {
        return offDistribution;
    }

    public double turnOnProbability(long tick) {
        if (started) {
            return 0.0;
        }
        double tickOfDay = (double) (tick % Constants.MIN_IN_DAY);
        return onDistribution.getProbability(tickOfDay) * onPrior / timePrior;
    };

    public double turnOffProbability(long tick) {
        if (!started) {
            return 0.0;
        }
        double onDuration = (double) (tick - startedTick);
        return offDistribution.getProbability(onDuration);
    }

    public void act(long tick) {
        double r = RNG.nextDouble();
        if(isInitialized() && (r <= turnOffProbability(tick))) {
        	started = false;
        	for (Appliance appliance : appliances){
        		appliance.turnOff();
        	}
        }
        if(!isInitialized() && (r <= turnOnProbability(tick))) {
        	started = true;
        	startedTick = tick;
        	for (Appliance appliance : appliances){
        		appliance.turnOn(tick);
        	}
        }
    }
    
    public static Vector<Activity> availableActivities(Vector<Appliance> apps) {
    	Vector<Activity> vec = new Vector<Activity>();
    	// Put them in a hash map
    	HashMap<String, Appliance> hashmap = new HashMap<String, Appliance>();
    	for(Appliance a : apps) {
    		hashmap.put(a.getName(), a);
    	}
    	
    	if(hashmap.containsKey("coffee-maker")) {
    		double durationInMins = 30;
    		double onPrior = durationInMins/Constants.MIN_IN_DAY;
    		double timePrior = 1/(Constants.MIN_IN_DAY - durationInMins);
    		double timeOfDay = 12 + RNG.nextDoublePlusMinus() * 12;
    		Gaussian gaussianOn = new Gaussian(60 * timeOfDay, 5);
    		Gaussian gaussianOff = new Gaussian(60 * 0.5, 10);
    		gaussianOff.setDefaultCdf();
    		Activity activity = 
					new Activity.Builder(
							onPrior, 
							timePrior, 
							gaussianOn, 
							gaussianOff).
							appliances(hashmap.get("coffee-maker")).build();
    		vec.add(activity);
    	}
    	
    	if(hashmap.containsKey("dvd-player") && 
    			hashmap.containsKey("television")) {
    		double durationInMins = 120;
    		double onPrior = durationInMins/Constants.MIN_IN_DAY;
    		double timePrior = 1/(Constants.MIN_IN_DAY - durationInMins);
    		double timeOfDay = 12 + RNG.nextDoublePlusMinus() * 12;
    		Gaussian gaussianOn = new Gaussian(60 * timeOfDay, 60);
    		Gaussian gaussianOff = new Gaussian(60 * 2, 30);
    		gaussianOff.setDefaultCdf();
    		Activity activity = 
					new Activity.Builder(
							onPrior, 
							timePrior, 
							gaussianOn, 
							gaussianOff).
							appliances(hashmap.get("dvd-player"),
									hashmap.get("television")).build();
    		vec.add(activity);
    	}

    	if(hashmap.containsKey("television")) {
    		double durationInMins = 240;
    		double onPrior = durationInMins/Constants.MIN_IN_DAY;
    		double timePrior = 1/(Constants.MIN_IN_DAY - durationInMins);
    		double timeOfDay = 12 + RNG.nextDoublePlusMinus() * 12;
    		Gaussian gaussianOn = new Gaussian(60 * timeOfDay, 60);
    		Gaussian gaussianOff = new Gaussian(60 * 4, 60);
    		gaussianOff.setDefaultCdf();
    		Activity activity = 
					new Activity.Builder(
							onPrior, 
							timePrior, 
							gaussianOn, 
							gaussianOff).
							appliances(hashmap.get("television")).build();
    		vec.add(activity);
    	}
    	
    	if(hashmap.containsKey("stove-oven")) {
    		double durationInMins = 90;
    		double onPrior = durationInMins/Constants.MIN_IN_DAY;
    		double timePrior = 1/(Constants.MIN_IN_DAY - durationInMins);
    		double timeOfDay = 17 + RNG.nextDoublePlusMinus() * 7;
    		Gaussian gaussianOn = new Gaussian(60 * timeOfDay, 30);
    		Gaussian gaussianOff = new Gaussian(60 * 1.5, 30);
    		gaussianOff.setDefaultCdf();
    		Activity activity = 
					new Activity.Builder(
							onPrior, 
							timePrior, 
							gaussianOn, 
							gaussianOff).
							appliances(hashmap.get("stove-oven")).build();
    		vec.add(activity);
    	}
    	
    	if(hashmap.containsKey("microwave-oven")) {
    		double durationInMins = 10;
    		double onPrior = durationInMins/Constants.MIN_IN_DAY;
    		double timePrior = 1/(Constants.MIN_IN_DAY - durationInMins);
    		double timeOfDay = 12 + RNG.nextDoublePlusMinus() * 12;
    		Gaussian gaussianOn = new Gaussian(60 * timeOfDay, 30);
    		Gaussian gaussianOff = new Gaussian(60 * 0.25, 5);
    		gaussianOff.setDefaultCdf();
    		Activity activity = 
					new Activity.Builder(
							onPrior, 
							timePrior, 
							gaussianOn, 
							gaussianOff).
							appliances(hashmap.get("microwave-oven")).build();
    		vec.add(activity);
    	}
    	
    	if(hashmap.containsKey("dishwasher")) {
    		double durationInMins = 60;
    		double onPrior = durationInMins/Constants.MIN_IN_DAY;
    		double timePrior = 1/(Constants.MIN_IN_DAY - durationInMins);
    		double timeOfDay = 12 + RNG.nextDoublePlusMinus() * 12;
    		Gaussian gaussianOn = new Gaussian(60 * timeOfDay, 30);
    		Gaussian gaussianOff = new Gaussian(60 * 1, 10);
    		gaussianOff.setDefaultCdf();
    		Activity activity = 
					new Activity.Builder(
							onPrior, 
							timePrior, 
							gaussianOn, 
							gaussianOff).
							appliances(hashmap.get("dishwasher")).build();
    		vec.add(activity);
    	}
    	
    	if(hashmap.containsKey("clothes-washer")) {
    		double durationInMins = 240;
    		double onPrior = durationInMins/Constants.MIN_IN_DAY;
    		double timePrior = 1/(Constants.MIN_IN_DAY - durationInMins);
    		double timeOfDay = 12 + RNG.nextDoublePlusMinus() * 12;
    		Gaussian gaussianOn = new Gaussian(60 * timeOfDay, 60);
    		Gaussian gaussianOff = new Gaussian(60 * 4, 120);
    		gaussianOff.setDefaultCdf();
    		Activity activity = 
					new Activity.Builder(
							onPrior, 
							timePrior, 
							gaussianOn, 
							gaussianOff).
							appliances(hashmap.get("clothes-washer")).build();
    		vec.add(activity);
    	}
    	
    	return vec;

    }
    
}
