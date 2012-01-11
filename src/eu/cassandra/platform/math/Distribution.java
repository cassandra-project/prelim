package eu.cassandra.platform.math;

public interface Distribution {
    public int getNumberOfParameters();
    public double getParameter(int index);
    /* Note that this will only works for doubles.
       Makes the parsing process simple. */
    public void setParameter(int index, double value);
    public boolean isInitialized();
    public void setDefaultPdf();
    public void setDefaultCdf();
    public double getPdf(double x);
    public double getCdf(double x);
    /* Depending on what you would like to return as a value. */
    public double getProbability(double x);
}
