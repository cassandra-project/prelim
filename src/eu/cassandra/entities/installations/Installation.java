package eu.cassandra.entities.installations;

import java.util.Vector;

import eu.cassandra.entities.appliances.Appliance;
import eu.cassandra.entities.people.Person;
import eu.cassandra.platform.utilities.Registry;

public class Installation {
	private final int id;
    private final String name;
    private Vector<Person> persons;
    private Vector<Appliance> appliances;
    private Registry registry;
    
    public static class Builder {
    	private static int idCounter = 0;
    	// Required variables
    	private final int id;
        private final String name;
        // Optional or state related variables
        private Vector<Person> persons = new Vector<Person>();
        private Vector<Appliance> appliances = new Vector<Appliance>();
        private Registry registry = null;
        public Builder(String aname) {
        	id = idCounter++;
			name = aname;
        }
        public Builder registry(Registry aregistry) {
			registry = aregistry; return this;
		}
		public Installation build() {
			return new Installation(this);
		}
    }
    
    private Installation(Builder builder) {
		id = builder.id;
		name = builder.name;
		persons = builder.persons;
		appliances = builder.appliances;
		registry = builder.registry;
	}

	public void nextStep(long tick) {
		for(Person person : getPersons()) {
			person.nextStep(tick);
		}
		updateRegistry(tick);
	}

	public void updateRegistry(long tick) {
		double power = 0.0;
		for(Appliance appliance : getAppliances()) {
			power += appliance.getPower(tick);
		}
		getRegistry().add(power);
	}

	public double getCurrentPower() {
		return getRegistry().getCurrentValue();
	}
	
    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public Registry getRegistry() {
        return registry;
    }
    
    public Vector<Person> getPersons() {
    	return persons;
    }

    public void addPerson(Person person) {
        persons.add(person);
    }

    public Vector<Appliance> getAppliances() {
        return appliances;
    }

    public void addAppliance(Appliance appliance) {
        this.appliances.add(appliance);
    }
}
