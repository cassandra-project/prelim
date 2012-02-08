package eu.cassandra.gui;

import java.text.SimpleDateFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.data.xy.XYDataset;

/**
 * A time series chart.
 */
public class Chart {

	/**
	 * Creates a chart.
	 * 
	 * @param dataset  the dataset.
	 * 
	 * @return a chart.
	 */
	public JFreeChart createChart(String title, final XYDataset dataset) {
		final JFreeChart chart = ChartFactory.createTimeSeriesChart(
				title, 
				"Time", 
				"Consumption (W)",
				dataset, 
				false, 
				true, 
				true
		);

		DateAxis axis = (DateAxis) chart.getXYPlot().getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("mm:HH dd/MM"));//MMM/yy

		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);

		return chart;
	}
}