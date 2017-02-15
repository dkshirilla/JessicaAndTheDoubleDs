/*Created by Jessica Stepp, Douglas Shirilla,
 * Douglas Linkhart, and Brandon Quijano
 * Java II 2017 Project 3 - Search Engine:
 * creating a GUI file search
 * that includes file upload and deletion tools.
 */
package jessicaAndTheDoubleDs; // Team name



/*
 * 1)get screen to not resize from user
 * 2)fill in file tab..add buttons textfields etc
 * 3)make sure project has the right tag for submission
 * 
 * part 2
 * 
 * 
 * 1)figure out browse button
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SearchEngine extends JPanel implements ActionListener{	
	
	//Eclipse whines if this line isn't here...
	private static final long serialVersionUID = 1L;
	
	// Initialize radio button status
	Boolean orBtnSelected     = true,
			andBtnSelected    = false,
			phraseBtnSelected = false;
	
	// These need to be accessible outside of the SearchEngine method
	JTextField txtSearchTerms = new JTextField( "Enter search terms here", 40 );
	JTextArea txtResults = new JTextArea(22, 40);
	
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
			
        /*To use absolute layout, use this code:
 		searchPanel.setLayout(null);
		btnSearch.setBounds(50, 50, 100, 100); 
		This also kills the initial panel text,
		but it could be put back with a label */ 
		
		// add a comment here
	    btnSearch.setActionCommand( "search" ); 
	    btnSearch.addActionListener( this );
	    
	    JRadioButton btnOr     = new JRadioButton( "OR" );
        JRadioButton btnAnd    = new JRadioButton( "AND" );
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
 
        // Add buttons to panel
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
		// Add border to Results box
		txtResults.setBorder(BorderFactory.createLineBorder(Color.black));
		// Add Results box to panel
		searchPanel.add(txtResults);
		
		// Add File Upload/Update panel
		JComponent files = textPanel("File upload/update panel");
		// Add Files tab 
		tabbedPane.addTab("Files", files);
		
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
		String nextLexeme = "";
		
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
			
			while ( sbStringToParse.length() > 0 ) // While there are still Search Terms in the string
			{
				nextLexeme = getNextLexeme(); // Get the next Search Term (lexeme)
				sbResults.append( nextLexeme + " " ); 
				
				if ( orBtnSelected && sbStringToParse.length() > 0 )
					sbResults.append( "OR " );
								
				if ( andBtnSelected && sbStringToParse.length() > 0 )
					sbResults.append( "AND " );
								
				if ( phraseBtnSelected && sbStringToParse.length() <= 0 )
					sbResults.append( "(PHRASE; terms in this order) " );
			} // While
			
			txtResults.setText( sbResults.toString() );
			
		} // If search
		else if (e.getActionCommand().equals("or"))
		{
			orBtnSelected     = true;
			andBtnSelected    = false;
			phraseBtnSelected = false;
		}
		else if (e.getActionCommand().equals("and"))
		{
			orBtnSelected     = false;
			andBtnSelected    = true;
			phraseBtnSelected = false;
		}
		else if (e.getActionCommand().equals("phrase"))
		{
			orBtnSelected     = false;
			andBtnSelected    = false;
			phraseBtnSelected = true;
		}
	} // actionPerformed
	
	/*this is just a filler component
	 * it should be a text box for 
	 * the user to type into
	 */
	/*With the text box created, is this comment still necessary?
	 * 
	 */
	
	// Parses the Search Term string by returning the next lexeme
	public String getNextLexeme()
	{
		int i;
		String lexeme;
		
		for ( i = 0; i < sbStringToParse.length(); i++ ) // Loop to look at each character in the string
			if ( sbStringToParse.substring(i, i + 1).equals( " " ) ) // If a space is found, marking the end of a lexeme
				break;
				
		lexeme = sbStringToParse.substring(0, i); // Copy the first lexeme found in the string
		sbStringToParse.delete( 0, i + 1);  // Remove the lexeme from the string

		return lexeme;
	} // getNextLexeme
	
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
	}//end creatAndShowGUI()

	public static void main(String[] args) {
		createAndShowGUI();
	}//end main

}//end class SearchEngine
