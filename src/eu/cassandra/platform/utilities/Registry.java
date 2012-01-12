package eu.cassandra.platform.utilities;

import java.util.Vector;

import eu.cassandra.entities.installations.Installation;

/**
 * Stores double numbers sequentially (i.e. time series) in a Vector class with
 * operations:
 * <li>
 * <ul>Add a value.</ul>
 * <ul>Select values between time ticks.</ul>
 * <ul>Calculate the mean between time ticks.</ul>
 * <ul>Calculate the standard deviation between time ticks.</ul>
 * </li> 
 * 
 * @author Cassandra developers
 * 
 * TODO make it agnostic of any Cassandra semantics i.e. CO2
 */
public class Registry {

	private String name;

	private Vector<Double> values = new Vector<Double>();

	public Registry(String aName) {
		name = aName;
	}

	public String getName() {
		return name;
	}

	public void setName(String s) {
		name = s;
	}

	public double getCurrentValue() {
		return values.lastElement().doubleValue();
	}

	public void setCurrentValue(double value) {
		values.add(new Double(value));
	}

	public double getValue(int tick) {
		return values.get(tick).doubleValue();
	}
	
	public Vector<Double> getValues() {
		return values;
	}

	public void setValue(int tick, double value) {
		values.set(tick, new Double(value));
	}

	public double getMean(int startTick, int endTick) {
		double mean = 0.0;
		for (int i = 0; i < values.size(); i ++) {
			mean += values.get(i);
		}
		mean /= values.size();
		return mean;
	}

	public double getVariance(int startTick, int endTick) {
		double var = 0.0;
		double mean = getMean(startTick, endTick);

		for (int i = 0; i < values.size(); i ++) {
			var += (values.get(i) - mean) * (values.get(i) - mean);
		}
		var /= values.size();
		return var;
	}

	/* setCurrentValue() with a simpler name. */
	public void add(double value) {
		setCurrentValue(value);
	}

}