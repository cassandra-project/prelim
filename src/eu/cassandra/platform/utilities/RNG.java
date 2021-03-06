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

package eu.cassandra.platform.utilities;

import java.util.Random;

/**
 * Wrapper class around java.util.Random for easier universal handling and 
 * access.
 * 
 * @author Cassandra developers
 *
 */
public class RNG {
	
	private static Random random;
	
	private RNG() { }
	
	public static void init() {
		random = new Random();
		random.setSeed(System.currentTimeMillis());
		random.nextDouble();
		random.nextDouble();
		random.nextDouble();
		random.nextDouble();
		random.nextDouble();
	}
	
	public static void setSeed(long seed) {
		random.setSeed(seed);
	}
	
	public static int nextInt() {
		return random.nextInt();
	}
	
	public static int nextInt(int n) {
		return random.nextInt(n);
	}
	
	public static long nextLong() {
		return random.nextLong();
	}
	
	public static double nextDouble() {
		return random.nextDouble();
	}
	
	public static double nextDoublePlusMinus() {
		return 2 * random.nextDouble() - 1;
	}
	
	public static void main(String[] args) {
		RNG.init();
		System.out.println(RNG.nextLong());
		System.out.println(RNG.nextInt());
	}
	
}
