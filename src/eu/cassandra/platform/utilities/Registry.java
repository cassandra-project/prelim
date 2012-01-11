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
	private Vector<Double> valuesCO2 = new Vector<Double>();

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

	public double getCurrentCO2Value() {
		return valuesCO2.lastElement().doubleValue();
	}

	public void setCurrentValue(double value) {
		values.add(new Double(value));
	}

	public void setCurrentC02Value(double co2Value) {
		valuesCO2.add(new Double(co2Value));
	}

	public double getValueCO2(int tick) {
		return valuesCO2.get(tick).doubleValue();
	}

	public double getValue(int tick) {
		return values.get(tick).doubleValue();
	}
	
	public Vector<Double> getValues() {
		return values;
	}

	public void setValueCO2(int tick, double co2Value) {
		valuesCO2.set(tick, new Double(co2Value));
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

	public double getMeanCO2PerPerson(int startTick, int endTick, 
			Installation installation) {
		double mean = 0.0;
		for (int i = 0; i < valuesCO2.size(); i ++) {
			mean += valuesCO2.get(i);
		}
		mean /= valuesCO2.size();
		mean /= installation.getPersons().size();
		return mean;
	}

	public double getVarianceCO2PerPerson(int startTick, int endTick,
			Installation installation) {
		double var = 0.0;
		int numOfPersons = installation.getPersons().size();
		double mean = getMeanCO2PerPerson(startTick, endTick,installation);

		for (int i = 0; i < valuesCO2.size(); i ++) {
			var += (valuesCO2.get(i)/numOfPersons - mean) * 
			(valuesCO2.get(i)/numOfPersons - mean);
		}
		var /= (valuesCO2.size() / numOfPersons);
		return var;
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

	public void addCO2(double co2Value) {
		setCurrentC02Value(co2Value);
	}
}