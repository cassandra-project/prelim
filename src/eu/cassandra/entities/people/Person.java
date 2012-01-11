package eu.cassandra.entities.people;

import java.util.Vector;

import eu.cassandra.entities.installations.Installation;

public class Person {
	private static int idCounter = 0;
    private int id;
    private String name;
    private Installation house;
    private Vector<Activity> activities = new Vector<Activity>();

    // Default constructor
    public Person() {
        id = idCounter++;
        name = null;
        house = null;
    }
    
 // Default constructor
    public Person(String aName, Installation aInstallation, 
    		Vector<Activity> aactivities) {
        id = idCounter++;
        name = aName;
        house = aInstallation;
        activities = aactivities;
    }

    // SETTERS - GETTERS //
    public void setId(int newid) {
        id = newid;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void setInstallation(Installation newInstallation) {
        house = newInstallation;
    }

    public void setActivities(Vector<Activity> aactivities) {
    	activities = aactivities;
    }
    
    public void addActivity(Activity aactivity) {
        activities.add(aactivity);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Installation getInstallation() {
        return house;
    }

    public Vector<Activity> getActivities() {
        return activities;
    }

    public void nextStep(long tick) {
        for (Activity activity : activities) {
            activity.act(tick);
        }
    }
}
