package eu.cassandra.platform.utilities;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * The class Utils provides static methods for helping with various chores. In
 * particular:
 * <li>
 * <ul>Read values and arrays of values from files that follow a Java Properties
 * format.<ul>
 * </li>
 * 
 * @author Cassandra developers
 *
 */
public abstract class Utils {
	
	public static Properties loadProperties(String filename) {
		Properties props = new Properties();
		try {
			Reader r = new FileReader(filename);
			props.load(r);
			r.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
	
	public static boolean getBool(String propsFile, String key) {
		return getBool(loadProperties(propsFile), key);
	}
	
	public static boolean getBool(Properties props, String key) {
		String s = props.getProperty(key);
		return Boolean.parseBoolean(s);
	}
	
	public static double getDouble(String propsFile, String key) {
		return getDouble(loadProperties(propsFile), key);
	}
	
	public static double getDouble(Properties props, String key) {
		String s = props.getProperty(key);
		return Double.parseDouble(s);
	}
	
	public static double[] getDoubleArray(String propsFile, String key) {
		return getDoubleArray(loadProperties(propsFile), key);
	}
	
	public static double[] getDoubleArray(Properties props, String key) {
		String s = props.getProperty(key);
		if(s.equals(null)) return null;
		String[] tokens = s.split("\\,");
		int length = tokens.length;
		double[] arr = new double[length];
		for(int i = 0; i < length; i++) {
			arr[i] = Double.parseDouble(tokens[i]);
		}
		return arr;
	}
	
	public static int getInt(String propsFile, String key) {
		return getInt(loadProperties(propsFile), key);
	}
	
	public static int getInt(Properties props, String key) {
		String s = props.getProperty(key);
		return Integer.parseInt(s);
	}
	
	public static int[] getIntArray(String propsFile, String key) {
		return getIntArray(loadProperties(propsFile), key);
	}
	
	public static int[] getIntArray(Properties props, String key) {
		String s = props.getProperty(key);
		if(s.equals(null)) return null;
		String[] tokens = s.split("\\,");
		int length = tokens.length;
		int[] arr = new int[length];
		for(int i = 0; i < length; i++) {
			arr[i] = Integer.parseInt(tokens[i]);
		}
		return arr;
	}
	
	public static String[] getStringArray(String propsFile, String key) {
		return getStringArray(loadProperties(propsFile), key);
	}
	
	public static String[] getStringArray(Properties props, String key) {
		String s = props.getProperty(key);
		if(s.equals(null)) return null;
		String[] arr = s.split("\\,");
		return arr;
	}
	
	public static void main(String[] args) {
		Properties props = loadProperties("props/appliances.props");
		System.out.println(props.getProperty("appliances"));
	}

}
