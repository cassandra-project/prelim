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

import eu.cassandra.platform.utilities.Charts;
import eu.cassandra.platform.utilities.RNG;

/**
 * @author Antonios Chrysopoulos
 * @version prelim
 * @since 2012-28-06
 */
public class Uniform implements ProbabilityDistribution
{
  // For precomputation
  protected boolean precomputed;
  protected int numberOfBins;
  protected double precomputeFrom;
  protected double precomputeTo;
  protected double[] histogram;

  public Uniform ()
  {
    precomputeFrom = 0;
    precomputeTo = 0;
    precomputed = false;
  }

  /**
   * @param start
   *          Starting value of the Uniform distribution.
   * @param end
   *          Ending value of the Uniform distribution.
   */
  public Uniform (double start, double end)
  {
    precomputeFrom = start;
    precomputeTo = end;
    precomputed = false;

  }

  public String getDescription ()
  {
    String description = "Uniform probability density function";
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
      return precomputeFrom;
    case 1:
      return precomputeTo;
    default:
      return 0.0;
    }

  }

  public void setParameter (int index, double value)
  {
    switch (index) {
    case 0:
      precomputeFrom = value;
      break;
    case 1:
      precomputeTo = value;
      break;
    default:
      return;
    }
  }

  public void precompute (double startValue, double endValue, int nBins)
  {
    precomputeFrom = startValue;
    precomputeTo = endValue;
    numberOfBins = nBins;
    histogram = new double[nBins];

    if (startValue > endValue) {
      System.out.println("Starting poing greater than ending point");
      return;
    }
    else if (startValue == endValue) {

      numberOfBins = 1;
      histogram[(int) startValue] = 1.0;

    }
    else {

      for (int i = (int) precomputeFrom; i < precomputeTo; i++) {

        histogram[i] = 1.0 / (double) (precomputeTo - precomputeFrom + 1);

      }
    }
    precomputed = true;
  }

  public double getProbability (double x)
  {
    if (x > precomputeTo || x < precomputeFrom) {
      return 0.0;
    }
    else {
      if (precomputeTo == precomputeFrom && x == precomputeTo)
        return 1.0;
      else
        return 1.0 / (double) (precomputeTo - precomputeFrom + 1);
    }
  }

  public double getPrecomputedProbability (double x)
  {
    if (!precomputed) {
      return -1;
    }

    return histogram[(int) x];
  }

  public int getPrecomputedBin ()
  {
    if (!precomputed) {
      return -1;
    }
    if (precomputeTo == precomputeFrom) {
      return (int) precomputeTo;
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
    System.out.print("Uniform Distribution with ");
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
    System.out.println("Testing Start Time Uniform Distribution Creation.");

    double start = (int) (1440 * Math.random());
    double end = (int) (start + (1440 - start) * Math.random());

    Uniform u = new Uniform(start, end);
    u.precompute(start, end, 1440);

    u.status();

    Charts.createHistogram("TestHist", "minute", "Possibility", u.histogram);
    Charts.createMixtureDistribution("TestMix", "minute", "Possibility",
                                     u.histogram);

    RNG.init();
    System.out.println("Testing Random Bins");
    for (int i = 0; i < 10; i++) {
      int temp = u.getPrecomputedBin();
      System.out.println("Random Bin: " + temp + " Possibility Value: "
                         + u.getPrecomputedProbability(temp));
    }

    System.out.println("Testing Duration Uniform Distribution Creation.");

    start = 10;
    end = 10;

    u = new Uniform(start, end);
    u.precompute(start, end, (int) end + 1);

    u.status();

    Charts.createHistogram("TestHist", "minute", "Possibility", u.histogram);
    Charts.createMixtureDistribution("TestMix", "minute", "Possibility",
                                     u.histogram);

    RNG.init();
    System.out.println("Testing Random Bins");
    for (int i = 0; i < 10; i++) {
      int temp = u.getPrecomputedBin();
      System.out.println("Random Bin: " + temp + " Possibility Value: "
                         + u.getPrecomputedProbability(temp));
    }

  }
}
