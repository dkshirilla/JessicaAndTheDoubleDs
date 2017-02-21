/*Created by Jessica Stepp, Douglas Shirilla,
 * Douglas Linkhart, and Brandon Quijano
 * Java II 2017 Project 3 - Search Engine:
 * creating a GUI file search
 * that includes file upload and deletion tools.
 */
package jessicaAndTheDoubleDs; // Team name

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class SearchEngine extends JPanel implements ActionListener{	
	
	//Eclipse whines if this line isn't here...
	private static final long serialVersionUID = 1L;
	
	// Initialize radio button status
	Boolean orBtnSelected     = true,
			andBtnSelected    = false,
			phraseBtnSelected = false;
	
	// These need to be accessible outside of the SearchEngine method
	// Search Tab text fields
	JTextField txtSearchTerms = new JTextField( "Enter search terms here", 40 );
	JTextArea txtResults = new JTextArea(22, 40);
	
	// Add text area for file tab
	JTextArea fileAdd = new JTextArea(22,40);
		
	// Search Terms
	StringBuilder sbStringToParse = new StringBuilder();
	
	public SearchEngine(){
		//used to set tabs to top left
		super (new GridLayout(1,1));
		
		//Build tabs pane
		JTabbedPane tabbedPane = new JTabbedPane();
		
		// Add Search panel
		JComponent searchPanel = textPanel( "" );
		// Add Search tab
		tabbedPane.addTab("Search", searchPanel);
		
		// Add border to Search Term text box
		txtSearchTerms.setBorder(BorderFactory.createLineBorder(Color.black));
		// Add Search Terms text box to panel
		searchPanel.add(txtSearchTerms);
		
		// Create buttons
		JButton btnSearch = new JButton( "Search" );
		btnSearch.setToolTipText("Click to search indexed files");
		btnSearch.setActionCommand( "search" ); 
	    btnSearch.addActionListener( this );
	    
	    JRadioButton btnOr = new JRadioButton( "OR" );
        JRadioButton btnAnd = new JRadioButton( "AND" );
        JRadioButton btnPhrase = new JRadioButton( "Phrase" );
        btnOr.setActionCommand( "or" ); 
	    btnOr.addActionListener( this );
	    btnAnd.setActionCommand( "and" ); 
	    btnAnd.addActionListener( this );
	    btnPhrase.setActionCommand( "phrase" ); 
	    btnPhrase.addActionListener( this );
 
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
		
		// Set buttons to according to status that was initialized previously
		btnOr.setSelected( orBtnSelected );
		btnAnd.setSelected( andBtnSelected );
		btnPhrase.setSelected( phraseBtnSelected );
		
		// Add result text area to show matched files when search is completed
		// Set line wrapping
		txtResults.setLineWrap(true);
		txtResults.setWrapStyleWord(true);
		fileAdd.setLineWrap(true);
		// Add border to Results box
		txtResults.setBorder(BorderFactory.createLineBorder(Color.black));
		// Add Results box to panel
		searchPanel.add(txtResults);
		
		// Add File Upload/Update panel
		JComponent files = textPanel("");
		// Add Files tab 
		tabbedPane.addTab("Files", files);
				
		//create buttons for file tab
		JButton btnAddFile = new JButton("Add File");
		btnAddFile.setToolTipText("Open browse window to select and add a file");
		btnAddFile.setActionCommand("addFile");
		btnAddFile.addActionListener(this);
		
		JButton btnRmvFile = new JButton("Remove File");
		btnRmvFile.setToolTipText("Remove selected file");
		btnRmvFile.setActionCommand("rmvFile");
		btnRmvFile.addActionListener(this);
		
		JButton btnUpdateFiles = new JButton("Update Index");
		btnUpdateFiles.setToolTipText("Update the index if they have been modified");
		btnUpdateFiles.setActionCommand("updateFiles");
		btnUpdateFiles.addActionListener(this);
		
		//add text area and buttons to file tab
		fileAdd.setBorder(BorderFactory.createLineBorder(Color.black));
		files.add(fileAdd);
		files.add(btnAddFile);
		files.add(btnRmvFile);
		files.add(btnUpdateFiles);
			
		// Build string for About tab using HTML
		StringBuilder sbAbout = new StringBuilder();
		sbAbout.append( "<html>" );
		sbAbout.append( "<font face='Times New Roman'>" );
		sbAbout.append( "<font size='+1'>");
		sbAbout.append( "<br> Search Engine 1.0 <br>");
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
	}//end SearchEngine()
	
	// Event handler
	public void actionPerformed(ActionEvent e) 
	{
		String nextLexeme = "",
			   fileName	  = "";
		
		if (e.getActionCommand().equals("search")) 
		{
			JOptionPane.showMessageDialog( 
	  		null, 
	  		"You clicked the Search button...", 
	  		"SEARCH!!!", 
	  		JOptionPane.INFORMATION_MESSAGE );
			
			sbStringToParse.append( txtSearchTerms.getText() );
			
			StringBuilder sbResults = new StringBuilder();
			sbResults.append( "No results found. \r\n \r\n" );
			sbResults.append( "You searched for:\r\n \r\n" );
			
			// While there are still Search Terms in the string
			while ( sbStringToParse.length() > 0 ) 
			{
				nextLexeme = getNextLexeme(); // Get the next Search Term (lexeme)
				sbResults.append( nextLexeme + " " ); 
				
				// If OR and not end of search-term string
				if ( orBtnSelected && sbStringToParse.length() > 0 ) 
					sbResults.append( "OR " );
								
				// If AND and not end of search-term string
				if ( andBtnSelected && sbStringToParse.length() > 0 )
					sbResults.append( "AND " );
								
				// If PHRASE and end of search-term string
				if ( phraseBtnSelected && sbStringToParse.length() <= 0 )
					sbResults.append( "(PHRASE; terms in this order) " );
			} // While
			
			// Write string to results text area
			txtResults.setText( sbResults.toString() );
			
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
			JOptionPane.showMessageDialog(null,"You clicked the add file button!", "ADDDDD!!!",
					JOptionPane.INFORMATION_MESSAGE);
			
			//prototype of windows explorer to add file directories using JFileChooser
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(null);
			File f = chooser.getSelectedFile();
			
			if (f != null)
				fileName = f.getAbsolutePath();
			else // Handle possibility of null (no file selected), which would cause exception
				JOptionPane.showMessageDialog( 
						null, 
						"You didn't select a file", 
						"NO FILE SELECTED!!!", 
						JOptionPane.WARNING_MESSAGE );
			
			// Write file path name to text area
			fileAdd.setText(fileName);
		} // If Add File
		
		else if (e.getActionCommand().equals("rmvFile"))
		{
			JOptionPane.showMessageDialog(null,"You clicked the remove file button!", "REMOVE!!",
					JOptionPane.INFORMATION_MESSAGE);
		} // If Remove File
		
		else if (e.getActionCommand().equals("updateFiles"))
		{
			JOptionPane.showMessageDialog(null,"You clicked the update index button!", "UPDATE!!",
					JOptionPane.INFORMATION_MESSAGE);
		} // If Update Index
	} // actionPerformed
	
	
	// Parses the Search Term string by returning the next lexeme
	public String getNextLexeme()
	{
		int i; // i needs to be in-scope outside of for loop
		String lexeme;
		
		// Loop to look at each character in the string
		for ( i = 0; i < sbStringToParse.length(); i++ ) 
			// If a space is found, marking the end of a lexeme...
			if ( sbStringToParse.substring(i, i + 1).equals( " " ) ) 
				break; // Break out of the loop
		
		// Copy the first lexeme found in the string
		lexeme = sbStringToParse.substring(0, i);
		
		// Remove the lexeme from the string
		sbStringToParse.delete( 0, i + 1);  

		return lexeme;
	} // getNextLexeme
	
	// Creates a panel with label containing specified text
	protected JComponent textPanel(String text){
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.add(filler);
		return panel; 
	} //end filler component
	
	//build main container
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Search Engine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//add tabbed pane to main container
		frame.add(new SearchEngine(), BorderLayout.CENTER);
		
		frame.setSize( 500, 500);
		frame.setLocationRelativeTo( null ); // Center frame on screen
		frame.setVisible( true );
		frame.setResizable(false); // dont let user resize window to keep gui formatting constant 
	}//end creatAndShowGUI()

	public static void main(String[] args) {
		createAndShowGUI();
	}//end main

}//end class SearchEngine
