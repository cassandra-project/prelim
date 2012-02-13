package eu.cassandra.platform;

import eu.cassandra.gui.MainInterface;
import eu.cassandra.platform.utilities.RNG;

public class Platform {

	/**
	 * The entry point of the Cassandra platform
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		RNG.init();
		MainInterface gui = new MainInterface();
		gui.launchFrame();

		//    	RNG.init();
		//        Observer observer = new Observer();
		//        observer.setup();
		//        observer.simulate();
		//        observer.flush();
	}

}
