package eu.cassandra.platform.math;

import java.lang.Math;


public class Gaussian implements Distribution {
    private double mean;
    private double sigma;
    private boolean initialized;
    private boolean pdf;

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

    public Gaussian() {
        mean = 0.0;
        sigma = 1.0;
        initialized = false;
        pdf = true;
    }

    public Gaussian(double m, double s) {
        mean = m;
        sigma = s;
        pdf = true;
        initialized = true;
    }

    public void initialize(double m, double s) {
        mean = m;
        sigma = s;
        pdf = true;
        initialized = true;
    }

    
    // Afto den tha eprepe na einai idio me tin teleftaia synartisi ??
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
       // return 0.0; // Will never reach;
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

    public boolean isInitialized() {
        return initialized;
    }

    public double getPdf(double x) {
        if (!initialized) {
            return 0.0; // Should replace with error check
        }
        else {
            return (1 / (Math.sqrt(2 * Math.PI) * sigma)) *
                Math.exp(-(x - mean) * (x - mean) / (2 * sigma * sigma));
        }
    }
    
    public double getCdf(double x) {
        if (!initialized) {
            return 0.0; // Should replace with error check
        }
        else {
            return bigPhi(x, mean, sigma);
        }
    }
    
    public void setDefaultPdf() {
        pdf = true;
    }
    
    public void setDefaultCdf() {
        pdf = false;
    }
    
    public double getProbability(double x) {
        if (pdf) {
            return getPdf(x);
        } else {
            return getCdf(x);
        }
    }

	@Override
	public int getNumberOfParameters() {
		// TODO Auto-generated method stub
		return 0;
	}

}
