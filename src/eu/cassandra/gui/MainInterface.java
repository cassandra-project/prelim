package eu.cassandra.gui;

import javax.swing.*;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.statistics.SimpleHistogramDataset;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import eu.cassandra.entities.installations.Installation;
import eu.cassandra.platform.Observer;
import eu.cassandra.platform.utilities.RNG;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

/**
 * 
 * @author Konstantinos N. Vavliakis (kvavliak@issel.ee.auth.gr)
 *
 */
public class MainInterface implements Runnable {
	TimeSeriesCollection dataset;
	private static Logger logger = Logger.getLogger(MainInterface.class);

	ChartPanel chartPanel ;
	
	// Initialize all swing objects.
	private JFrame f = new JFrame("Cassanda GUI"); 

	private JPanel buttonPanel = new JPanel();  
	private JScrollPane buttonScrollPane = new JScrollPane(buttonPanel);
	private JScrollPane graphScrollPane;

	// Buttons some there is something to put in the panels
	private JButton startButton = new JButton("Start");
	private JButton pauseButton = new JButton("Pause");
	private JButton stopButton = new JButton("Stop");
	private JButton exitButton = new JButton("Exit");

	private JComboBox installationCombo = new JComboBox();

	// Menu
	private JMenuBar menuBar = new JMenuBar(); // Menubar
	private JMenu menuFile = new JMenu("File"); // File Entry on Menu bar
	private JMenuItem menuItemQuit = new JMenuItem("Quit"); // Quit sub item
	private JMenu menuHelp = new JMenu("Help"); // Help Menu entry
	private JMenuItem menuItemAbout = new JMenuItem("About"); // About Entry


	public MainInterface(){

		installationCombo.addActionListener(new ListenInstallationComboBox());
		installationCombo.setPreferredSize(new Dimension(300, 20));

		startButton.addActionListener(new ListenStartButton());
		pauseButton.addActionListener(new ListenPauseButton());
		stopButton.addActionListener(new ListenStopButton());
		exitButton.addActionListener(new ListenExitButton());

		// Set menubar
		f.setJMenuBar(menuBar);

		//Build Menus
		menuFile.add(menuItemQuit);  // Create Quit line
		menuHelp.add(menuItemAbout); // Create About line
		menuBar.add(menuFile);        // Add Menu items to form
		menuBar.add(menuHelp);


		TimeSeries series = new TimeSeries("", Minute.class);
		dataset = new TimeSeriesCollection(series);
		Chart c = new Chart();
		JFreeChart chart = c.createChart("Consumption of: 1", dataset);
		chartPanel = new ChartPanel(chart); 
		graphScrollPane = new JScrollPane(chartPanel);

		// Add Buttons
		buttonPanel.add(startButton);
		buttonPanel.add(pauseButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(installationCombo);
		buttonPanel.add(exitButton);

		// Setup Main Frame
		f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add(graphScrollPane, BorderLayout.CENTER);
		f.getContentPane().add(buttonScrollPane, BorderLayout.SOUTH);

		// Allows the Swing App to be closed
		f.addWindowListener(new ListenCloseWdw());

		//Add Menu listener
		menuItemQuit.addActionListener(new ListenMenuQuit());
	}

	Observer obs = null;

	public class ListenStartButton implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			startButton.setEnabled(false);	
			obs = new Observer();
			Thread t = new Thread(gui);
			t.start();
		}
	}

	public void run() {
		obs.setup();
		for(Installation inst : Observer.installations) {
			installationCombo.addItem(inst.getId());
		}
		obs.simulate();
		startButton.setEnabled(true);	
	}
//
//	public class ListenData implements DatasetChangeListener  {
//		public void datasetChanged(DatasetChangeEvent arg0) {
//			//   generateChartMethod();         
//		}
//	}

	public class ListenPauseButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		}
	}

	public class ListenStopButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		}
	}

	public class ListenInstallationComboBox implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			TimeSeries	series = new TimeSeries("", Minute.class);
			//series.clear();
			Calendar cal = Calendar.getInstance();
			Installation inst = Observer.installations.get(installationCombo.getSelectedIndex());
			float[] values = inst.getRegistry().getValues();
			Minute minute = new Minute();
			minute.peg(cal);
			for(int i =0; i<values.length;i++) {
				minute = new Minute(minute.next().getStart());
				series.add(minute, values[i]);
			}
			dataset.removeSeries(0);
			dataset.addSeries(series);
		}
	}

	public class ListenExitButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	public class ListenMenuQuit implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.exit(0);         
		}
	}

	public class ListenCloseWdw extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			System.exit(0);         
		}
	}

	public void launchFrame(){
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.setPreferredSize(new Dimension(1000,700));
		f.pack();
	}

	private static MainInterface gui = null;
	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]){
		RNG.init();
		gui = new MainInterface();
		gui.launchFrame();
	}
}