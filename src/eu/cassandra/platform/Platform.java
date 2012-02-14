package eu.cassandra.platform;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import eu.cassandra.platform.gui.GUI;
import eu.cassandra.platform.utilities.Params;
import eu.cassandra.platform.utilities.RNG;

/**
 * The class Platform contains the entry point (main method) for running the 
 * Cassandra platform.
 * 
 * @author Kyriakos C. Chatzidimitriou (kyrcha [at] iti [dot] gr)
 * @version prelim
 *
 */
public class Platform {
	
	static Logger logger = Logger.getLogger(Platform.class);

	/**
	 * The entry point of the Cassandra platform
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		init();
	}
	
	private static void init() {
		PropertyConfigurator.configure(Params.LOG_CONFIG_FILE);
		logger.info("Platform initialization.");
		RNG.init();
		GUI gui = new GUI();
		gui.launchFrame();
		// Run without GUI
//    	RNG.init();
//        Simulation sim = new Simulation();
//        sim.setup();
//        sim.simulate();
//        sim.flush();
	}

}
