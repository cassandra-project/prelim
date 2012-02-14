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