package eu.cassandra.platform;

import eu.cassandra.platform.utilities.RNG;

public class Platform {

    public static void main(String[] args) {
    	RNG.init();
        Observer observer = new Observer();
        observer.simulate();
        observer.flushRegistriesOnFiles();
    }
    
}
