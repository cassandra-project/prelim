package eu.cassandra.gui;

import javax.swing.*;
import javax.swing.event.*;

import eu.cassandra.platform.Observer;
import eu.cassandra.platform.utilities.RNG;

import java.awt.*;
import java.awt.event.*;

/**
 * 
 * @author Konstantinos N. Vavliakis (kvavliak@issel.ee.auth.gr)
 *
 */
public class MainInterface{
	// Initialize all swing objects.
	private JFrame f = new JFrame("Cassanda GUI"); 
	
	private JPanel buttonPanel = new JPanel();  
	private JScrollPane buttonScrollPane = new JScrollPane(buttonPanel);
	private JPanel graphPanel = new JPanel();
	private JScrollPane graphScrollPane = new JScrollPane(graphPanel);

	// Buttons some there is something to put in the panels
	private JButton startButton = new JButton("Start");
	private JButton pauseButton = new JButton("Pause");
	private JButton stopButton = new JButton("Stop");
	
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
		
		// Set menubar
		f.setJMenuBar(menuBar);

		//Build Menus
		menuFile.add(menuItemQuit);  // Create Quit line
		menuHelp.add(menuItemAbout); // Create About line
		menuBar.add(menuFile);        // Add Menu items to form
		menuBar.add(menuHelp);

		// Add Buttons
		buttonPanel.add(startButton);
		buttonPanel.add(pauseButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(installationCombo);

	
		// Setup Main Frame
		f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add(graphScrollPane, BorderLayout.CENTER);
		f.getContentPane().add(buttonScrollPane, BorderLayout.SOUTH);

		// Allows the Swing App to be closed
		f.addWindowListener(new ListenCloseWdw());

		//Add Menu listener
		menuItemQuit.addActionListener(new ListenMenuQuit());
	}

	public class ListenStartButton implements ActionListener {
	    public void actionPerformed(ActionEvent e) {
	    	RNG.init();
	        Observer observer = new Observer();
	        observer.simulate();
	        observer.flush();
	    }
	}
	
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
		f.pack();
		f.setVisible(true);
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]){
		MainInterface gui = new MainInterface();
		gui.launchFrame();
	}
}