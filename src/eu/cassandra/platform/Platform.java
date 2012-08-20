/*   
   Copyright 2011-2012 The Cassandra Consortium (cassandra-fp7.eu)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

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
