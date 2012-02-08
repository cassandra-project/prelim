package eu.cassandra.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Week;
import org.jfree.data.xy.XYDataset;

/**
 * A time series chart.
 */
public class Chart {

	private static Logger logger = Logger.getLogger(Chart.class);
//	private Font font = new Font("Arial",Font.BOLD,25);

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
				true, 
				true, 
				true
		);
////		chart.getLegend().setItemFont(font);
//		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//		renderer.setSeriesLinesVisible(0, true);
//		renderer.setSeriesShapesVisible(0, true);
//		renderer.setSeriesPaint(0, Color.RED);
//		renderer.setSeriesStroke(0, new BasicStroke(4f));

		DateAxis axis = (DateAxis) chart.getXYPlot().getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("mm:HH dd/MM"));//MMM/yy

//		XYPlot axisY = chart.getXYPlot();
//		ValueAxis valueAxis = axisY.getRangeAxis();

		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);

		//renderer.setSeriesPaint(chart.getXYPlot().getDataset().getSeriesCount()-1,Color.BLUE);

		return chart;
	}


	/**
	 * Creates a sample dataset.
	 * 
	 * @param potentialEventMap
	 * @return
	 */
	//	public TimeSeriesCollection createDataset(Vector<DateItemID> itemDataset,boolean allColor, boolean allSizes) {
	//		TimeSeriesCollection dataset = new TimeSeriesCollection();
	//		TimeSeries timeSeries = new TimeSeries("ALL");
	//		for(DateItemID itemWithDate : itemDataset) {
	//			Calendar date = itemWithDate.getDate();
	//			ItemInstancePurchased item = itemWithDate.getItem();
	//
	//			Week week = new Week(date.getTime());
	//			if(timeSeries.getDataItem(week) != null)
	//				timeSeries.addOrUpdate(week,timeSeries.getDataItem(week).getValue().intValue() + item.getItemQuantity());
	//			else
	//				timeSeries.addOrUpdate(week,item.getItemQuantity());
	//		}
	//
	//		HashMap<String,TimeSeries> allColorTimeSeries = new HashMap<String,TimeSeries>();
	//		HashMap<String,TimeSeries> allSizeTimeSeries = new HashMap<String,TimeSeries>();
	//
	//		if(allColor) {
	//			addColorSizeTimeSeries(itemDataset,allColorTimeSeries,allColor);
	//		}
	//		else if(allSizes) {
	//			addColorSizeTimeSeries(itemDataset,allSizeTimeSeries,!allSizes);
	//		}
	//
	//		//dataset.addSeries(timeSeries);
	//		for(TimeSeries tSeries : allColorTimeSeries.values())
	//			dataset.addSeries(tSeries);
	//		for(TimeSeries tSeries : allSizeTimeSeries.values())
	//			dataset.addSeries(tSeries);
	//
	//		return dataset;
	//	}

	/**
	 * 
	 * @param dateValueInfoPerCustomerTypeMap
	 * @return
	 */
	//	public TimeSeriesCollection createWhenCustomerTypesBuyDataset(HashMap<String,Vector<DateValueInfo>> dateValueInfoPerCustomerTypeMap) {
	//		TimeSeriesCollection dataset = new TimeSeriesCollection();
	//		Vector<String> customerTypes = new Vector<String>();
	//		customerTypes.add("Valueless");
	//		customerTypes.add("Uncertain");
	//		customerTypes.add("Loyal");
	//		customerTypes.add("High Roller");
	//		customerTypes.add("NA");
	//
	//		for(String customerType : customerTypes){
	//			TimeSeries timeSeries = new TimeSeries(customerType);
	//			Vector<DateValueInfo> dateValueInfoPerCustomerType = dateValueInfoPerCustomerTypeMap.get(customerType);
	//			if(dateValueInfoPerCustomerType != null) {
	//				for(DateValueInfo dateValueInfoItem : dateValueInfoPerCustomerType) {
	//					Week week = new Week(dateValueInfoItem.getDate());
	//					if(timeSeries.getDataItem(week) != null)
	//						timeSeries.addOrUpdate(week,timeSeries.getDataItem(week).getValue().intValue() + 1);
	//					else
	//						timeSeries.addOrUpdate(week,1);
	//				}
	//			}
	//			Iterator iter = timeSeries.getTimePeriods().iterator();
	//
	//			if(!timeSeries.isEmpty()) {
	//				Week startWeek =  (Week)timeSeries.getTimePeriod(0);
	//				Week endWeek =  (Week)timeSeries.getTimePeriod(timeSeries.getItemCount()-1);
	//				while(startWeek.getStart().before(endWeek.getStart())) {
	//					if(timeSeries.getDataItem(startWeek) != null) 
	//						System.out.println("\t" + startWeek + "\t" + timeSeries.getDataItem(startWeek).getValue());
	//					else {
	//						timeSeries.addOrUpdate(startWeek,0);
	//						System.out.println("\t" + startWeek + " updated");
	//					}
	//					startWeek = (Week)startWeek.next();
	//				}
	//			}
	//
	//			dataset.addSeries(timeSeries);
	//		}
	//		return dataset;
	//	}


	//	private void addColorSizeTimeSeries(Vector<DateItemID> itemDataset,	HashMap<String,TimeSeries> allColorSizeTimeSeries,boolean useColor) {
	//		for(DateItemID itemWithDate : itemDataset) {
	//			String attribute = null;
	//			if(useColor)
	//				attribute = itemWithDate.getItem().getItemColor().split(" ")[0];
	//			else
	//				attribute = itemWithDate.getItem().getItemSize();
	//			TimeSeries colorSizeTimeSeries = null;
	//			if(allColorSizeTimeSeries.containsKey(attribute))
	//				colorSizeTimeSeries = allColorSizeTimeSeries.get(attribute);
	//			else {
	//				colorSizeTimeSeries = new TimeSeries(attribute);
	//				allColorSizeTimeSeries.put(attribute,colorSizeTimeSeries);
	//			}
	//
	//			Calendar date = itemWithDate.getDate();
	//			ItemInstancePurchased item = itemWithDate.getItem();
	//			Week month = new Week(date.getTime());
	//			if(colorSizeTimeSeries.getDataItem(month) != null)
	//				colorSizeTimeSeries.addOrUpdate(month,colorSizeTimeSeries.getDataItem(month).getValue().intValue() + item.getItemQuantity());
	//			else
	//				colorSizeTimeSeries.addOrUpdate(month,item.getItemQuantity());
	//		}
	//	}


	/**
	 * 
	 * @param dataset
	 * @param title
	 */
//	public void saveJChart(String title,JFreeChart chart) {
//		try {
//			String fileName = title +  ".jpg";
//			File file = new File(fileName);
//			file.createNewFile();
//			ChartUtilities.saveChartAsJPEG(file, chart, 3000, 1500);
//		} catch (IOException e) {
//			logger.error(e.getMessage());
//			e.printStackTrace();
//		}
//
//	}




}