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

package eu.cassandra.platform.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 *  Definition of the Cassandra project files via ending.
 */
public class CassandraProjectFileFilter extends FileFilter {
	
	private final String EXTENSION = "cas";

	/**
	 * Given a file returns if the file has an acceptable ending or not.
	 * 
	 * @return true if acceptable file, false if not
	 */
	public boolean accept(File f) {
		if(f.isDirectory()) {
			return true;
		}
		if(f.toString() != null) {
			if ( f.toString().endsWith(EXTENSION)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * Returns the description of this filter.
	 * 
	 * @return the description of this filter
	 */
	public String getDescription() {
		return "Cassandra Project Files";
	}
	
}