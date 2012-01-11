package eu.cassandra.entities.appliances;

import eu.cassandra.entities.installations.Installation;
import eu.cassandra.platform.utilities.Params;
import eu.cassandra.platform.utilities.RNG;
import eu.cassandra.platform.utilities.Registry;
import eu.cassandra.platform.utilities.Utils;

public class Appliance {
	private final int id;
	private final String name;
	private final Installation installation;
	private final double[] consumption;
	private final int[] periods;
	private final int totalCycleTime;
	private final double standByConsumption;
	private final boolean base;
	
	private boolean inUse;
	private long onTick;
	private Registry registry;
	
	public static class Builder {
		private static int idCounter = 0;
		// Required variables
		private final int id;
		private final String name;
		private final Installation installation;
		private final double[] consumption;
		private final int[] periods;
		private final int totalCycleTime;
		private final double standByConsumption;
		private final boolean base;
		// Optional or state related variables
		private long onTick = -1;
		private Registry registry = null;
		public Builder(String aname, Installation ainstallation) {
			id = idCounter++;
			name = aname;
			installation = ainstallation;
			consumption = 
					Utils.getDoubleArray(Params.APPS_PROPS, name+".power");
			periods = Utils.getIntArray(Params.APPS_PROPS, name+".periods");
			int sum = 0;
			for(int i = 0; i < periods.length; i++) {
				sum += periods[i];
			}
			totalCycleTime = sum;
			standByConsumption = 
					Utils.getDouble(Params.APPS_PROPS, name+".stand-by");
			base = Utils.getBool(Params.APPS_PROPS, name+".base");
		}
		public Builder registry(Registry aregistry) {
			registry = aregistry; return this;
		}
		public Appliance build() {
			return new Appliance(this);
		}
	}
	
	private Appliance(Builder builder) {
		id = builder.id;
		name = builder.name;
		installation = builder.installation;
		standByConsumption = builder.standByConsumption;
		consumption = builder.consumption;
		periods = builder.periods;
		totalCycleTime = builder.totalCycleTime;
		base = builder.base;
		registry = builder.registry;
		inUse = (base) ? true : false;
		onTick = (base) ? RNG.nextInt(100) : builder.onTick; 
	}
	
	public void createRegistry(Registry aregistry) {
		registry = aregistry;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Installation getInstallation() {
		return installation;
	}

	public boolean isInUse() {
		return inUse;
	}

	public double getPower(long tick) {
		double power;
		if(isInUse()) {
			long relativeTick = tick - onTick;
			long tickInCycle = relativeTick % totalCycleTime;
			int ticks = 0;
			int periodIndex = 0;
			for(int i = 0; i < periods.length; i++) {
				ticks += periods[i];
				if(tickInCycle < ticks) {
					periodIndex = i;
					break;
				}
			}
			power = consumption[periodIndex];
		} else {
			power = standByConsumption;
		}
		if(registry != null) updateRegistry(power);
		return power;
	}

	private void updateRegistry(double power) {
		getRegistry().add(power);
	}

	public void turnOff() {
		if(!base) {
			inUse = false;
			onTick = -1;
		}
	}

	public void turnOn(long tick) {
		inUse = true;
		onTick = tick;
	}

	public long getOnTick() {
		return onTick;
	}

	public double getCurrentPower() {
		return getRegistry().getCurrentValue();
	}

	public Registry getRegistry() {
		return registry;
	}
	
	public static void main(String[] args) {
		Appliance frige = new Appliance.Builder("refrigerator", 
				null).registry(new Registry("power")).build();
		System.out.println(frige.getId());
		System.out.println(frige.getName());
		Appliance freezer = new Appliance.Builder("freezer", 
				null).registry(new Registry("power")).build();
		System.out.println(freezer.getId());
		System.out.println(freezer.getName());
		for(int i = 0; i < 100; i++) {
			System.out.println(freezer.getPower(i));
		}
	}
	
}
