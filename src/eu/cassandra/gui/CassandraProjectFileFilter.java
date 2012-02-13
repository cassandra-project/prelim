package eu.cassandra.gui;

import java.io.File;
import javax.swing.filechooser.*;

public class CassandraProjectFileFilter extends FileFilter {

	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		if (f.toString() != null) {
			if ( f.toString().endsWith("cas")) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	//The description of this filter
	public String getDescription() {
		return "Cassandra Project Files";
	}
}