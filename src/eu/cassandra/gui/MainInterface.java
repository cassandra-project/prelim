package eu.cassandra.gui;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import eu.cassandra.entities.installations.Installation;
import eu.cassandra.platform.Observer;
import eu.cassandra.platform.utilities.RNG;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 
 * @author Konstantinos N. Vavliakis (kvavliak@issel.ee.auth.gr)
 *
 */
public class MainInterface implements Runnable {

	private TimeSeriesCollection dataset;
	// Initialize all swing objects.
	private JFrame f = new JFrame("Cassanda GUI"); 
	private JPanel buttonPanel = new JPanel();  
	private JScrollPane buttonScrollPane = new JScrollPane(buttonPanel);
	private JScrollPane graphScrollPane;

	private JTextField projectFileField = new JTextField("Select project file");
	private JButton startButton = new JButton("Start");
	private JButton exitButton = new JButton("Exit");
	private JComboBox installationCombo = new JComboBox();

	// Menu
	private JMenuBar menuBar = new JMenuBar(); // Menubar
	private JMenu menuFile = new JMenu("File"); // File Entry on Menu bar
	private JMenuItem menuItemQuit = new JMenuItem("Quit"); // Quit sub item
	private JMenu menuHelp = new JMenu("Help"); // Help Menu entry
	private JMenuItem menuItemAbout = new JMenuItem("About"); // About Entry

	/**
	 * 
	 */
	public MainInterface(){

		installationCombo.addActionListener(new ListenInstallationComboBox());
		installationCombo.setPreferredSize(new Dimension(300, 20));

		startButton.addActionListener(new ListenStartButton());
		exitButton.addActionListener(new ListenExitButton());
		projectFileField.addMouseListener(new ListenProjectFileField());

		projectFileField.setEditable(false);

		f.setJMenuBar(menuBar);

		menuFile.add(menuItemQuit);  
		menuHelp.add(menuItemAbout); 
		menuBar.add(menuFile);       
		menuBar.add(menuHelp);


		TimeSeries series = new TimeSeries("");
		dataset = new TimeSeriesCollection(series);
		JFreeChart chart = createChart("Consumption of: 1", dataset);
		ChartPanel chartPanel = new ChartPanel(chart); 
		graphScrollPane = new JScrollPane(chartPanel);

		buttonPanel.add(projectFileField);
		buttonPanel.add(startButton);
		buttonPanel.add(installationCombo);
		buttonPanel.add(exitButton);

		f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add(graphScrollPane, BorderLayout.CENTER);
		f.getContentPane().add(buttonScrollPane, BorderLayout.SOUTH);

		f.addWindowListener(new ListenCloseWdw());
		menuItemQuit.addActionListener(new ListenMenuQuit());
	}

	Observer obs = null;

	public class ListenStartButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			startButton.setEnabled(false);	
			installationCombo.setEnabled(false);
			obs = new Observer();
			Thread t = new Thread(gui);
			t.start();
		}
	}

	public void run() {
		obs.setup();
		obs.simulate();
		installationCombo.addItem("All installations");
		for(Installation inst : Observer.installations) {
			installationCombo.addItem(inst.getId());
		}
		startButton.setEnabled(true);	
		installationCombo.setEnabled(true);
	}

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

	public class ListenInstallationComboBox implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			TimeSeries	series = new TimeSeries("");
			//series.clear();
			Calendar cal = Calendar.getInstance();
			Minute minute = new Minute();
			minute.peg(cal);
			if(installationCombo.getSelectedIndex() == 0){
				if(Observer.installations.size() > 0) {
					for(int i=0; i <Observer.installations.get(0).getRegistry().getValues().length;i++ ) {
						float mean = 0;
						for(Installation inst: Observer.installations) {
							mean += inst.getRegistry().getValues()[i];
						}		
						//mean /= Observer.installations.size();
						minute = new Minute(minute.next().getStart());
						series.add(minute, mean);
					}
				}
			}
			else{
				Installation inst = Observer.installations.get(installationCombo.getSelectedIndex()+1);
				float[] values = inst.getRegistry().getValues();
				for(int i =0; i<values.length;i++) {
					minute = new Minute(minute.next().getStart());
					series.add(minute, values[i]);
				}
			}
			dataset.removeSeries(0);
			dataset.addSeries(series);
		}
	}

	public class ListenProjectFileField  implements MouseListener {
		public void mousePressed(MouseEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
			
			CassandraProjectFileFilter filter = new CassandraProjectFileFilter();
			fc.setFileFilter(filter);
			int returnVal = fc.showOpenDialog(f);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("You chose to open this file: " + fc.getSelectedFile().getName());
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {

		}

		@Override
		public void mouseExited(MouseEvent arg0) {

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {

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