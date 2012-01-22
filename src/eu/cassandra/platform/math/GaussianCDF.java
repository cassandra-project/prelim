package eu.cassandra.platform.math;

import java.lang.Math;

/**
 * @author Christos Diou <diou remove this at iti dot gr>
 * @version prelim
 * @since 2012-22-01
 */
public class GaussianCDF implements Distribution {
    private double mean;
    private double sigma;

    // For precomputation
    private boolean precomputed;
    private int numberOfBins;
    private double precomputeFrom;
    private double precomputeTo;
    private double[] histogram;


    // return phi(x) = standard Gaussian pdf
    private static double phi(double x) {
        return Math.exp(-(x * x) / 2) / Math.sqrt(2 * Math.PI);
    }

    // return phi(x, mu, s) = Gaussian pdf with mean mu and stddev s
    private static double phi(double x, double mu, double s) {
        return phi((x - mu) / s) / s;
    }

    // return Phi(z) = standard Gaussian cdf using Taylor approximation
    private static double bigPhi(double z) {
        if (z < -8.0) {
            return 0.0;
        }
        if (z >  8.0) {
            return 1.0;
        }

        double sum = 0.0;
        double term = z;
        for (int i = 3; Math.abs(term) < 1e-5; i += 2) {
            sum  += term;
            term *= (z * z) / i;
        }
        return 0.5 + sum * phi(z);
    }

    // return Phi(z, mu, s) = Gaussian cdf with mean mu and stddev s
    private static double bigPhi(double z, double mu, double s) {
        return bigPhi((z - mu) / s);
    }

    /**
     * Constructor. Sets the parameters of the standard normal
     * distribution, with mean 0 and standard deviation 1.
     */
    public GaussianCDF() {
        mean = 0.0;
        sigma = 1.0;
        precomputed = false;
    }

    /**
     * @param mu Mean value of the Gaussian distribution.
     * @param s Standard deviation of the Gaussian distribution.
     */
    public GaussianCDF(double mu, double s) {
        mean = mu;
        sigma = s;
        precomputed = false;
    }

    public String getDescription() {
        String description = "Gaussian cumulative probability density function";
        return description;
    }

    public int numberOfParameters() {
        return 2;
    }

    public double getParameter(int index) {
        switch (index) {
        case 0:
            return mean;
        case 1:
            return sigma;
        default:
            return 0.0;
        }
    }

    public void setParameter(int index, double value) {
        switch (index) {
        case 0:
            mean = value;
            break;
        case 1:
            sigma = value;
            break;
        default:
            return;
        }
    }

    public void precompute(double startValue, double endValue, int nBins) {
        if (startValue >= endValue) {
            // TODO Throw an exception or whatever.
            return;
        }
        precomputedFrom = startValue;
        precomputedTo = endValue;
        numberOfBins = nBins;

        double div = (endValue - startValue) / (double) nBins;
        histogram = new int[nBins];

        for (i = 0; i < nBins; i ++) {
            double x = startValue + i * div;
            // Value of bin is the probability at the beginning of the
            // value range.
            histogram[i] = bigPhi(x, mean, sigma);
        }
        precomputed = true;
    }

    public double getProbability(double x) {
        return bigPhi(x, mean, sigma);
    }

    public double getPrecomputedProbability(double x) {
        if (!precomputed) {
            return -1;
        }
        double div = (precomputedFrom - precomputedTo) / (double) numberOfBins;
        int bin = Math.floor((x - precomputedFrom) / div);
        if (bin == numberOfBins) {
            bin --;
        }
        return histogram[bin];
    }
}
