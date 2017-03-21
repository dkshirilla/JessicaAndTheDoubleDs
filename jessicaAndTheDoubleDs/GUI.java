package jessicaAndTheDoubleDs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class GUI extends JPanel{ //implements ActionListener{
	
	//Eclipse whines if this line isn't here...
		private static final long serialVersionUID = 1L;
		
        // These need to be accessable to other classes
		public static boolean orBtnSelected;
		public static boolean andBtnSelected;
		public static boolean phraseBtnSelected;
		public static Object txtResults;
		public static Object txtSearchTerms;
		static DefaultTableModel fileTableModel = new DefaultTableModel();

		public static Object fileTable;
		
		// long lastMod;
		
		public GUI(){
			//used to set tabs to top left
			super (new GridLayout(1,1));
			
			//Build tabs pane
			JTabbedPane tabbedPane = new JTabbedPane();
			//new TheHandler object for buttons
			thehandler handler = new thehandler();
			
			// Add Search panel
			JComponent searchPanel = textPanel( "" );
			// Add Search tab
			tabbedPane.addTab("Search", searchPanel);
			

			
			// Add border to Search Term text box
			((JComponent) txtSearchTerms).setBorder(BorderFactory.createLineBorder(Color.black));
			// Add Search Terms text box to panel
			searchPanel.add((Component) txtSearchTerms);
						
			// Create buttons for search panel
			JButton btnSearch = new JButton( "Search" );
			btnSearch.setToolTipText("Click to search indexed files");
			btnSearch.setActionCommand( "search" ); 
		    btnSearch.addActionListener( handler );
		    
		    JRadioButton btnOr = new JRadioButton( "OR" );
	        JRadioButton btnAnd = new JRadioButton( "AND" );
	        JRadioButton btnPhrase = new JRadioButton( "Phrase" );
	        btnOr.setActionCommand( "or" ); 
		    btnOr.addActionListener( handler );
		    btnAnd.setActionCommand( "and" ); 
		    btnAnd.addActionListener( handler );
		    btnPhrase.setActionCommand( "phrase" ); 
		    btnPhrase.addActionListener( handler );
	 
	        // Group the radio buttons
	        ButtonGroup group = new ButtonGroup();
	        group.add( btnOr );
	        group.add( btnAnd );
	        group.add( btnPhrase ); 
	 
	        // Add buttons to Search panel
			searchPanel.add( btnSearch );
			searchPanel.add( btnOr );
			searchPanel.add( btnAnd );
			searchPanel.add( btnPhrase );
			
			// Initialize radio button status
			Boolean orBtnSelected     = true,
					andBtnSelected    = false,
					phraseBtnSelected = false;			

			new StringBuilder();
			
			// Set buttons to according to status that was initialized previously
			btnOr.setSelected( orBtnSelected );
			btnAnd.setSelected( andBtnSelected );
			btnPhrase.setSelected( phraseBtnSelected );
			
			// Add result text area to show matched files when search is completed
			// Set line wrapping
			((JTextArea) txtResults).setLineWrap(true);
			((JTextArea) txtResults).setWrapStyleWord(true);
			// Add border to Results box
			((JComponent) txtResults).setBorder(BorderFactory.createLineBorder(Color.black));
			// Add Results box to panel
			searchPanel.add((Component) txtResults);
			
			// Add File Upload/Update panel
			JComponent files = textPanel("");
			// Add Files tab 
			tabbedPane.addTab("Files", files);
			
					
			
			//Create JTable for files tab
			
			JTable fileTable = new JTable();
			String[] columnNames = {"File","Status"};
			fileTableModel.setColumnIdentifiers(columnNames);
			fileTable.setModel(fileTableModel);
			fileTable.setPreferredScrollableViewportSize(new Dimension(500,300));
			JScrollPane jps = new JScrollPane(fileTable);
			fileTable.setFillsViewportHeight(true);
		
			//add jtable to file tab
			files.add(jps);
			
			//create buttons for file tab
			JButton btnAddFile = new JButton("Add File");
			btnAddFile.setToolTipText("Open browse window to select and add a file");
			btnAddFile.setActionCommand("addFile");
			btnAddFile.addActionListener(handler);
			
			JButton btnRmvFile = new JButton("Remove File");
			btnRmvFile.setToolTipText("Remove selected file");
			btnRmvFile.setActionCommand("rmvFile");
			btnRmvFile.addActionListener(handler);
			
			JButton btnUpdateFiles = new JButton("Update Index");
			btnUpdateFiles.setToolTipText("Update the index if they have been modified");
			btnUpdateFiles.setActionCommand("updateFiles");
			btnUpdateFiles.addActionListener(handler);
			
			//add buttons to file tab
			files.add(btnAddFile);
			files.add(btnRmvFile);
			files.add(btnUpdateFiles);
				
			// Build string for About tab using HTML
			StringBuilder sbAbout = new StringBuilder();
			sbAbout.append( "<html>" );
			sbAbout.append( "<font face='Times New Roman'>" );
			sbAbout.append( "<font size='+1'>");
			sbAbout.append( "<br> Search Engine 1.1 <br>");
			sbAbout.append( "<br> COP 2805  Project 3 <br>");
			sbAbout.append( "<font size='-1'>");
			sbAbout.append( "<br> by Jessica and the Double Ds: <br>");
			sbAbout.append( "<i> Douglas Linkhart <br>");
			sbAbout.append( "Brandon Quijano <br>");
			sbAbout.append( "Douglas Shirilla <br>");
			sbAbout.append( "Jessica Stepp <br> </i> </font>");
			sbAbout.append( "<font face='Arial'>" );
			sbAbout.append( "<font size='-1'>");
			sbAbout.append( "<br> Accompanying index data file: &nbsp; TBD");
			sbAbout.append( "</font>");
			sbAbout.append( "</html>" );
			
			// Instantiate About panel and add string
			JComponent aboutPanel = textPanel(sbAbout.toString());
		
			// Add tab and About panel
			tabbedPane.addTab("About", aboutPanel);
			
			add(tabbedPane);			
			
		}
		
		// Event handler
		private class thehandler implements ActionListener{
			
			// Event handler
			public void actionPerformed(ActionEvent e) 
			{
				if (e.getActionCommand().equals("search")) 
				{
					SearchLogic.doSearch();
				} // If search
				
				else if (e.getActionCommand().equals("or"))
				{
					orBtnSelected     = true;
					andBtnSelected    = false;
					phraseBtnSelected = false;
				} // If OR
				
				else if (e.getActionCommand().equals("and"))
				{
					orBtnSelected     = false;
					andBtnSelected    = true;
					phraseBtnSelected = false;
				} // If AND
				
				else if (e.getActionCommand().equals("phrase"))
				{
					orBtnSelected     = false;
					andBtnSelected    = false;
					phraseBtnSelected = true;
				} // If PHRASE
				
				else if (e.getActionCommand().equals("addFile"))
				{
					Index.addFile();
				} // If Add File
				
				else if (e.getActionCommand().equals("rmvFile"))
				{
					Index.removeFile();
				} // If Remove File
				
				else if (e.getActionCommand().equals("updateFiles"))
				{
					Index.updateIndex();
				} // If Update Index
			
		} // actionPerformed
	}//thehandler
		
		
	
	
	
	protected JComponent textPanel(String text){
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.add(filler);
		return panel; 
	} //
	
	
	//build main container
		static void createAndShowGUI() {
			JFrame frame = new JFrame("Search Engine");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//adding the tabbed pane to the JFrame
			frame.add(new GUI(), BorderLayout.CENTER);
			frame.setSize( 500, 500);
			frame.setLocationRelativeTo( null ); // Center frame on screen
			frame.setVisible( true );
			frame.setResizable(false); // dont let user resize window to keep gui formatting constant 
			
		}//end creatAndShowGUI()

}
