package eu.cassandra.platform.utilities;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.NormalDistributionFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Charts
{
  public static void createHistogram (String title, String x, String y,
                                      double[] data)
  {

    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    for (int i = 0; i < data.length; i++) {
      dataset.addValue(data[i], y, (Comparable) i);
    }

    PlotOrientation orientation = PlotOrientation.VERTICAL;
    boolean show = false;
    boolean toolTips = false;
    boolean urls = false;
    JFreeChart chart =
      ChartFactory.createBarChart(title, x, y, dataset, orientation, show,
                                  toolTips, urls);
    int width = 500;
    int height = 300;

    try {
      ChartUtilities.saveChartAsPNG(new File("Charts/" + title + ".PNG"),
                                    chart, width, height);
    }
    catch (IOException e) {
    }

  }

  public static void createNormalDistribution (String title, String x,
                                               String y, double mean,
                                               double sigma)
  {

    if (sigma < 0.01)
      sigma = 0.01;
    Function2D normal = new NormalDistributionFunction2D(mean, sigma);
    XYDataset dataset =
      DatasetUtilities.sampleFunction2D(normal, 0, (mean + 4 * sigma), 100,
                                        "Normal");
    JFreeChart chart =
      ChartFactory.createXYLineChart(title, x, y, dataset,
                                     PlotOrientation.VERTICAL, true, true,
                                     false);
    int width = 500;
    int height = 300;
    try {
      ChartUtilities.saveChartAsPNG(new File("Charts/Normal Distribution/"
                                             + title + ".PNG"), chart, width,
                                    height);
    }
    catch (IOException e) {
    }

  }

  public static void createMixtureDistribution (String title, String x,
                                                String y, double[] data)
  {
    XYSeries series1 = new XYSeries("First");

    for (int i = 0; i < data.length; i++) {
      series1.add(i, data[i]);
    }

    final XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(series1);

    PlotOrientation orientation = PlotOrientation.VERTICAL;
    boolean show = false;
    boolean toolTips = false;
    boolean urls = false;
    JFreeChart chart =
      ChartFactory.createXYLineChart(title, x, y, dataset, orientation, show,
                                     toolTips, urls);
    int width = 500;
    int height = 300;

    try {
      ChartUtilities.saveChartAsPNG(new File("Charts/" + title + ".PNG"),
                                    chart, width, height);
    }
    catch (IOException e) {
    }

  }
}
