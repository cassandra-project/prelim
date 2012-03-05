package eu.cassandra.platform.utilities;

/**
 * Class to hold certain values
 * 
 * @author Cassandra developers
 *
 */
public abstract class Params {
	
	// Default properties files
	
	public static String ACT_PROPS;
	
	public static String APPS_PROPS;
	
	public static String DEMOG_PROPS;
	
	public static String SIM_PROPS;
	
	// Configuration files
	
	public static String LOG_CONFIG_FILE = "config/log.conf";
	
	public static String JAVADB_PROPS = "config/javaDB.conf";
	
	/** Defines the registry directory */
	public static String REGISTRIES_DIR = "registries/";
	
	/** Defines the properties directory */
	public static String PROPS_DIR = "props/";
	
}
