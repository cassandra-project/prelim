/*   
   Copyright 2011-2012 The Cassandra Consortium (cassandra-fp7.eu)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package eu.cassandra.platform.math;

import eu.cassandra.platform.utilities.RNG;

/**
 * @author Christos Diou <diou remove this at iti dot gr>
 * @version prelim
 * @since 2012-22-01
 */
public class Gaussian implements ProbabilityDistribution
{
  protected double mean;
  protected double sigma;

  // For precomputation
  protected boolean precomputed;
  protected int numberOfBins;
  protected double precomputeFrom;
  protected double precomputeTo;
  protected double[] histogram;

  // return phi(x) = standard Gaussian pdf
  private static double phi (double x)
  {
    return Math.exp(-(x * x) / 2) / Math.sqrt(2 * Math.PI);
  }

  // return phi(x, mu, s) = Gaussian pdf with mean mu and stddev s
  private static double phi (double x, double mu, double s)
  {
    return phi((x - mu) / s) / s;
  }

  // return Phi(z) = standard Gaussian cdf using Taylor approximation
  private static double bigPhi (double z)
  {
    if (z < -8.0) {
      return 0.0;
    }
    if (z > 8.0) {
      return 1.0;
    }

    double sum = 0.0;
    double term = z;
    for (int i = 3; Math.abs(term) > 1e-5; i += 2) {
      sum += term;
      term *= (z * z) / i;
    }
    return 0.5 + sum * phi(z);
  }

  // return Phi(z, mu, s) = Gaussian cdf with mean mu and stddev s
  protected static double bigPhi (double z, double mu, double s)
  {
    return bigPhi((z - mu) / s);
  }

  /**
   * Constructor. Sets the parameters of the standard normal
   * distribution, with mean 0 and standard deviation 1.
   */
  public Gaussian ()
  {
    mean = 0.0;
    sigma = 1.0;
    precomputed = false;
  }

  /**
   * @param mu
   *          Mean value of the Gaussian distribution.
   * @param s
   *          Standard deviation of the Gaussian distribution.
   */
  public Gaussian (double mu, double s)
  {
    mean = mu;
    sigma = s;
    precomputed = false;
  }

  public String getDescription ()
  {
    String description = "Gaussian probability density function";
    return description;
  }

  public int getNumberOfParameters ()
  {
    return 2;
  }

  public double getParameter (int index)
  {
    switch (index) {
    case 0:
      return mean;
    case 1:
      return sigma;
    default:
      return 0.0;
    }

  }

  public void setParameter (int index, double value)
  {
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

  public void precompute (double startValue, double endValue, int nBins)
  {
    if (startValue >= endValue) {
      // TODO Throw an exception or whatever.
      return;
    }
    precomputeFrom = startValue;
    precomputeTo = endValue;
    numberOfBins = nBins;

    double div = (endValue - startValue) / (double) nBins;
    histogram = new double[nBins];

    histogram[0] = bigPhi(startValue + div, mean, sigma);
    for (int i = 1; i < nBins - 1; i++) {
      double x = startValue + i * div;
      histogram[i] = bigPhi(x + div, mean, sigma) - bigPhi(x, mean, sigma);
    }
    if (nBins > 1) {
      histogram[nBins - 1] = 1 - bigPhi(endValue - div, mean, sigma);
    }
    precomputed = true;
  }

  public double getProbability (double x)
  {
    return phi(x, mean, sigma);
  }

  public double getPrecomputedProbability (double x)
  {
    if (!precomputed) {
      return -1;
    }
    double div = (precomputeTo - precomputeFrom) / (double) numberOfBins;
    int bin = (int) Math.floor((x - precomputeFrom) / div);
    if (bin == numberOfBins) {
      bin--;
    }
    return histogram[bin];
  }

  public int getPrecomputedBin ()
  {
    if (!precomputed) {
      return -1;
    }
    // double div = (precomputeTo - precomputeFrom) / (double) numberOfBins;
    double dice = RNG.nextDouble();
    double sum = 0;
    for (int i = 0; i < numberOfBins; i++) {
      sum += histogram[i];
      // if(dice < sum) return (int)(precomputeFrom + i * div);
      if (dice < sum)
        return i;
    }
    return -1;
  }

  public void status ()
  {
    System.out.print("Normal Distribution with");
    System.out.print(" Mean: " + getParameter(0));
    System.out.println(" Sigma: " + getParameter(1));
    System.out.println("Precomputed: " + precomputed);
    if (precomputed) {
      System.out.print("Number of Beans: " + numberOfBins);
      System.out.print(" Starting Point: " + precomputeFrom);
      System.out.println(" Ending Point: " + precomputeTo);
    }
    System.out.println();

  }

  public static void main (String[] args)
  {
    System.out.println("Testing num of time per day.");
    Gaussian g = new Gaussian(1, 0.00001);
    g.precompute(0, 1439, 1440);
    g.status();
    double sum = 0;
    for (int i = 0; i <= 3; i++) {
      sum += g.getPrecomputedProbability(i);
      System.out.println(g.getPrecomputedProbability(i));
    }
    System.out.println("Sum = " + sum);
    RNG.init();
    System.out.println(g.getPrecomputedBin());
    System.out.println(g.getPrecomputedBin());
    System.out.println(g.getPrecomputedBin());
    System.out.println(g.getPrecomputedBin());
    System.out.println(g.getPrecomputedBin());
    System.out.println("Testing start time.");
    g = new Gaussian(620, 200);
    g.precompute(0, 1439, 1440);
    System.out.println(g.getPrecomputedBin());
    System.out.println(g.getPrecomputedBin());
    System.out.println(g.getPrecomputedBin());
    System.out.println(g.getPrecomputedBin());
    System.out.println(g.getPrecomputedBin());
    System.out.println(g.getPrecomputedBin());
    System.out.println(g.getPrecomputedBin());
    System.out.println(g.getPrecomputedBin());
    System.out.println(g.getPrecomputedBin());
    System.out.println(g.getPrecomputedBin());
    System.out.println("Testing duration.");
    g = new Gaussian(240, 90);
    g.precompute(1, 1439, 1440);
    System.out.println(g.getPrecomputedBin());
    System.out.println(g.getPrecomputedBin());
    System.out.println(g.getPrecomputedBin());
    System.out.println(g.getPrecomputedBin());
    System.out.println(g.getPrecomputedBin());
  }

}
