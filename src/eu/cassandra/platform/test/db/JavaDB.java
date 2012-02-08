package eu.cassandra.platform.test.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import eu.cassandra.platform.utilities.FileUtils;
import eu.cassandra.platform.utilities.Params;


public class JavaDB {

	private static Logger logger = Logger.getLogger(JavaDB.class);
	private static Connection dbConnection;
	private static Properties dbProperties;
	private static boolean isConnected = false;
	private static String dbName;

	private PreparedStatement addInstallationData;
	private PreparedStatement addActivityData;

	public JavaDB() {
		dbProperties = new Properties();

		dbProperties.put("derby.url", FileUtils.getString(Params.JAVADB_PROPS, "derby.url"));
		dbProperties.put("user", FileUtils.getString(Params.JAVADB_PROPS, "user"));
		dbProperties.put("password", FileUtils.getString(Params.JAVADB_PROPS, "password"));
		dbProperties.put("derby.driver", FileUtils.getString(Params.JAVADB_PROPS, "derby.driver"));
		dbProperties.put("db.schema", FileUtils.getString(Params.JAVADB_PROPS, "db.schema"));
		dbProperties.put("db.data", FileUtils.getString(Params.JAVADB_PROPS, "db.data"));
		dbName = FileUtils.getString(Params.JAVADB_PROPS, "db.schema");
		setDBSystemDir();
		String driverName = FileUtils.getString(Params.JAVADB_PROPS, "derby.driver"); 
		loadDatabaseDriver(driverName);
		if(!dbExists()) {
			createDatabase();
		}
		connect();
	}

	private boolean dbExists() {
		boolean bExists = false;
		String dbLocation = getDatabaseLocation();
		File dbFileDir = new File(dbLocation);

		if (dbFileDir.exists()) {
			bExists = true;
		}
		if(logger.isDebugEnabled())
			logger.debug(dbFileDir + " exists=" + bExists);
		return bExists;
	}

	private void setDBSystemDir() {
		// decide on the db system directory
		String userHomeDir = FileUtils.getString(Params.JAVADB_PROPS, "db.data");
		String systemDir = userHomeDir + "/" + FileUtils.getString(Params.JAVADB_PROPS, "db.schema");
		System.setProperty("derby.system.home", systemDir);
		dbProperties.put("derby.system.home", systemDir);
		// create the db system directory
		File fileSystemDir = new File(systemDir);
		fileSystemDir.mkdir();
	}

	private void loadDatabaseDriver(String driverName) {
		// load Derby driver
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	private boolean createTables(Connection dbConnection) {
		boolean bCreatedTables = false;
		Statement statement = null;
		try {
			statement = dbConnection.createStatement();
			if(logger.isDebugEnabled()) {
				logger.debug(createInstallationRegistry);
				logger.debug(createActivityRegistry);
			}
			statement.execute(createInstallationRegistry);
			statement.execute(createActivityRegistry);
			statement.close();
			bCreatedTables = true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return bCreatedTables;
	}

	private boolean createDatabase() {
		boolean bCreated = false;
		Connection dbConnection = null;
		dbProperties.put("create", "true");
		try {
			logger.info(FileUtils.getString(Params.JAVADB_PROPS, "derby.url"));
			for(Object key : dbProperties.keySet()) {
				logger.info(key + "\t" + dbProperties.getProperty(key.toString()) );
			}
			dbConnection = DriverManager.getConnection(
					FileUtils.getString(Params.JAVADB_PROPS, "derby.url") + 
					FileUtils.getString(Params.JAVADB_PROPS, "db.schema"),dbProperties);
			bCreated = createTables(dbConnection);
			dbConnection.close();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		dbProperties.remove("create");
		return bCreated;
	}


	public boolean connect() {
		if(!isConnected) {
			try {
				dbConnection = DriverManager.getConnection(
						FileUtils.getString(Params.JAVADB_PROPS, "derby.url") + 
						FileUtils.getString(Params.JAVADB_PROPS, "db.schema"),dbProperties);
				isConnected = dbConnection != null;
				if(logger.isDebugEnabled())
					logger.debug("Connected to JavaDB:" + isConnected);

				dbConnection.setAutoCommit(false);

				addInstallationData = dbConnection.prepareStatement("INSERT INTO " +
				"InstallationRegistry (InstallationID, Tick, Value) VALUES (?,?,?)");
				addActivityData = dbConnection.prepareStatement("INSERT INTO " +
				"ActivityRegistry (ActivityID, OnTick, Duration) VALUES (?,?,?)");
			} catch (SQLException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				isConnected = false;
			}
		}
		return isConnected;
	}

	public boolean addInstallationRegistryData(int installationID, long tick, double value) {
		return addInstallationRegistryData(installationID, tick, value, true);
	}

	private boolean addInstallationRegistryData(int installationID, long tick, double value, boolean commit) {
		try {
			addInstallationData.setInt(1,installationID);
			addInstallationData.setLong(2,tick);
			addInstallationData.setDouble(3,value);
			boolean result =  addInstallationData.execute();
			if(commit)
				dbConnection.commit();
			return result;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public PreparedStatement addInstallationRegistryData(long tick, double value) {
		try {
			addInstallationData.setInt(1,1);
			addInstallationData.setLong(2,tick);
			addInstallationData.setDouble(3,value);
			addInstallationData.execute();
			//addInstallationData.addBatch();
			return addInstallationData;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public boolean addActivityRegistryData(int activityID, long onTick, long duration) {
		return addActivityRegistryData(activityID, onTick, duration, true);
	}

	private boolean addActivityRegistryData(int activityID, long onTick, long duration, boolean commit) {
		try {
			addActivityData.setInt(1,activityID);
			addActivityData.setLong(2,onTick);
			addActivityData.setLong(3,duration);
			boolean result = addActivityData.execute();
			if(commit)
				dbConnection.commit();
			return result;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public boolean addInstallationRegistryData(int installationID, long tick[], double value[]) {
		try {
			boolean result = true;
			for(int i=0; i< value.length; i++) {
				long tickValue;
				if(tick == null || tick.length != value.length)
					tickValue = i;
				else
					tickValue = tick[i];
				if(result)
					result = addInstallationRegistryData(installationID, tickValue, value[i],false);
				else 
					addInstallationRegistryData(installationID, tickValue, value[i],false);
			}
			dbConnection.commit();
			return result;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public boolean addInstallationRegistryData(int installationID, double value[]) {
		return addInstallationRegistryData(installationID, null, value);
	}



	public boolean addActivityRegistryData(int activityID, long onTick[], long duration[]) {
		try {
			boolean result = true;
			for(int i=0; i< onTick.length; i++) {
				if(result)
					result = addActivityRegistryData(activityID, onTick[i], duration[i],false);
				else 
					addActivityRegistryData(activityID, onTick[i], duration[i],false);
			}
			dbConnection.commit();
			return result;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}




	public void disconnect() {
		if(isConnected) {
			if(logger.isDebugEnabled())
				logger.debug("Disconnecting from JavaDB");
			dbProperties.put("shutdown", "true");
			try {
				DriverManager.getConnection(
						FileUtils.getString(Params.JAVADB_PROPS, "derby.url") + 
						FileUtils.getString(Params.JAVADB_PROPS, "db.schema"),dbProperties);
			} catch (SQLException e) {
				//Derby by default throws an exception when closing
				logger.info(e.getMessage());
			}
			isConnected = false;
		}
	}

	public String getDatabaseLocation() {
		String dbLocation = System.getProperty("derby.system.home") + "/" + dbName;
		return dbLocation;
	}

	public String getDatabaseUrl() {
		String dbUrl = FileUtils.getString(Params.JAVADB_PROPS, "derby.url");
		return dbUrl;
	}

	private static final String createInstallationRegistry =
		"create table InstallationRegistry (" +
		"    InstallationID INT, \n" +
		"    Tick 			BIGINT, \n" +
		"    Value 			DOUBLE, \n" +
		"PRIMARY KEY (InstallationID,Tick) \n " + 
		")";

	private static final String createActivityRegistry =
		"create table ActivityRegistry (" +
		"    ActivityID BIGINT, \n" +
		"    OnTick 			INT, \n" +
		"    Duration 			DOUBLE, \n" +
		"PRIMARY KEY (ActivityID,OnTick) \n " + 
		")";

	//Tests
	public static void main(String args[]) {
		long start = Calendar.getInstance().getTimeInMillis();
		PropertyConfigurator.configure(Params.LOG_CONFIG_FILE);
		logger.info("Testing JavaDB");
		JavaDB javaDB = new JavaDB();
		try {
			PreparedStatement prepStmt = null;
			logger.info("Adding Batch");
			for(int i=0;i<10;i++) {
				//javaDB.addInstallationRegistryData(1, i, 0.5,false);
				prepStmt = javaDB.addInstallationRegistryData(i, 0.5);
			}
			//logger.info("Executing Batch");
			prepStmt.executeBatch();
			logger.info("Commit");
			dbConnection.commit();
			logger.info("Selecting");
			//			ResultSet res = dbConnection.createStatement().executeQuery("SELECT * FROM InstallationRegistry");
			//			int c = 0;
			//			while(res.next()) {
			//				res.getString(1);
			//				c++;
			//			}
			//			logger.info(c);
			logger.info("Time: " + ( Calendar.getInstance().getTimeInMillis()-start)/1000 + "s");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		javaDB.disconnect();
	}



}
