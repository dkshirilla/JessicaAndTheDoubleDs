/*Created by Jessica Stepp, Douglas Shirilla,
 * Douglas Linkhart, and Brandon Quijano
 * Java II 2017 Project 4 - Search Engine:
 * creating a GUI file search engine
 * that includes file addition and deletion tools.
 */
package jessicaAndTheDoubleDs; // Team name

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.util.List;

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
	JTextArea txtResults = new JTextArea(25, 70);
	
	// File Table
	DefaultTableModel fileTableModel = new DefaultTableModel();
	JTable fileTable = new JTable();
	Object[] row = new Object[2];
	final int fileColumn = 0;
	final int statusColumn = 1;
	
	// Index file
	File indexFile;
	
	// Index data structure
	int numFiles;
	ArrayList<String> fileNames = new ArrayList<>();
	List<Long> lastMod = new ArrayList<Long>();
	ArrayList<String> wordIndex = new ArrayList<>();

	public SearchEngine(){
		// Set tabs to top left
		super (new GridLayout(1,1));
		
		// Build tabs pane
		JTabbedPane tabbedPane = new JTabbedPane();
		
		// Add Search panel
		JComponent searchPanel = textPanel( "" );
		// Add Search tab
		tabbedPane.addTab("Search", searchPanel);
		
		// Add border to Search Term text box
		txtSearchTerms.setBorder(BorderFactory.createLineBorder(Color.black));
		// Add Search Terms text box to panel
		searchPanel.add(txtSearchTerms);
			
		// Create JTable for files tab
		String[] columnNames = {"File", "Status"};
		fileTableModel.setColumnIdentifiers(columnNames);
		fileTable.setModel(fileTableModel);
		fileTable.setPreferredScrollableViewportSize(new Dimension(760, 300));
		JScrollPane jps = new JScrollPane(fileTable);
		fileTable.setFillsViewportHeight(true);
		
		// Create buttons for Search tab
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
		// Add border to Results box
		txtResults.setBorder(BorderFactory.createLineBorder(Color.black));
		// Add Results box to panel
		searchPanel.add(txtResults);
		
		// Add File Add/Remove/Index Update panel
		JComponent files = textPanel("");
		// Add Files tab 
		tabbedPane.addTab("Files", files);
				
		// Create buttons for file tab
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
		
		// Add jtable to file tab
		files.add(jps);
		// Add buttons to file tab
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
		
		indexFile = new File( "index.txt" ); 
	
		// Initialize file table and data structure 
		// If the index file exists...
		if ( indexFile.exists() )
		{
			readIndexFile();
			
			// Populate files table
		    if (numFiles > 0)
		    {
		    	// Loop through files in index
		    	for (int i = 0; i <= (numFiles - 1); ++i)
		    	{
		    		// Put the file name in the table
		    		row[fileColumn] = fileNames.get(i);
				    
		    		// Create a reference to the file 
		    		File file = new File(fileNames.get(i));
		    		// If the file still exists...
		    		if ( file.exists() )
		    		{
			    		// Check last modified date/time  of file
		    			long timeModified = (long)file.lastModified();
    		
		    			// If the file has not changed...
		    			if (lastMod.get(i) == timeModified)
		    			{
		    				row[statusColumn] = "Indexed";
		    			} // If file unchanged
		    			else // The file changed
		    			{
		    				row[statusColumn] = "File changed since last indexed";
		    			} // Else the file changed
		    		} // If file exists
		    		else // The file no longer exists...
		    		{
		    			row[statusColumn] = "File no longer exists";
		    		}
		    		// Update table
		    		fileTableModel.addRow(row);
		    	} // For 
			} // If numFiles > 0
		} // If index file exists
		else // The index file does not exist
		{
			numFiles = 0;
			writeIndexFile();
		}
	} // SearchEngine()
	
	// Event handler
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getActionCommand().equals("search")) 
		{
			doSearch();
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
			addFile();
		} // If Add File
		
		else if (e.getActionCommand().equals("rmvFile"))
		{
			removeFile();
		} // If Remove File
		
		else if (e.getActionCommand().equals("updateFiles"))
		{
			updateIndex();
		} // If Update Index
	
	} // actionPerformed
	
	public void removeFile()
	{
		// Get row selected by user for deletion
		int rowToRemove = fileTable.getSelectedRow();
	
		// Remove the row from the table
		fileTableModel.removeRow(rowToRemove);
	
		// Update data structure
		--numFiles;
		fileNames.remove(rowToRemove);
		lastMod.remove(rowToRemove);

		updateIndex();
	} // removeFile
	
	// Add a file
	public void addFile()
	{
		// File chooser dialog box
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		File f = chooser.getSelectedFile();
	
		if (f != null) // If a file was selected...
		{
			String fileName = f.getAbsolutePath();
	
			// Update file table
			row[fileColumn] = fileName;
			row[statusColumn] = "Indexed";
			fileTableModel.addRow(row);

			// Increment number of files
			++numFiles;
	
			// Add file name to data structure
			fileNames.add(fileName);
	
			// Create a reference to the file 
			File file = new File(fileName);
		
			// Add last modified date/time of file to data structure
			lastMod.add( file.lastModified() );

			// Parse the file and add the words to the data structure
			parseFile(file);
		
			// Write the updated data to the index file
			writeIndexFile();
		
		} // If file name not null
		else // Handle possibility of null (no file selected), which would cause exception
			JOptionPane.showMessageDialog( 
					null, 
					"You didn't select a file", 
					"NO FILE SELECTED!!!", 
					JOptionPane.WARNING_MESSAGE );
	} // add File
	
	// Do a Search
	public void doSearch()
	{
		Scanner searchTerms = new Scanner(txtSearchTerms.getText());
	
		StringBuilder sbResults = new StringBuilder();
		sbResults.append( "No results found. \r\n \r\n" );
		sbResults.append( "You searched for:\r\n \r\n" );
	
		// While there are still search terms...
		while(searchTerms.hasNext()) 
		{
			sbResults.append( searchTerms.next() + " " ); 
		
			// If OR and not last search term
			if ( orBtnSelected && searchTerms.hasNext() ) 
				sbResults.append( "OR " );
						
			// If AND and not last search term
			if ( andBtnSelected && searchTerms.hasNext() )
				sbResults.append( "AND " );
						
			// If PHRASE and last search term
			if ( phraseBtnSelected && !searchTerms.hasNext() )
				sbResults.append( "(PHRASE; terms in this order) " );
		} // While
	
		// Write string to results text area
		txtResults.setText( sbResults.toString() );
	
		// Close scanner
		searchTerms.close();
	} // doSearch
	
	// Updates the index file
	public void updateIndex()
	{
		// Clear index so that it can be regenerated
		wordIndex.clear();
		
		// numFiles may change in loop, so assign to loop control variable
		int numFilesInit = numFiles;
					
		for (int i = 0; i <= (numFilesInit - 1); ++i)
		{
			if (i > (numFiles - 1)) // If all files have been indexed already... 
			{
				// Break out of the loop				
				break;
			}
			
			// Create a reference to the file 
		   	File file = new File(fileNames.get(i));
    		if ( file.exists() )  // If the file still exists...
    		{
    			// Get its last modified time/date
    			long timeModified = (long)file.lastModified();
			
			   // Set the last modified parameter in the data structure
			   lastMod.set(i, timeModified);
						
			   parseFile(file);
			
			   // Update table
		       fileTableModel.setValueAt("Indexed", i, statusColumn);
    		} // If file exists
    		else // File no longer exists...
    		{
    			// Remove the file from the table
    			fileTableModel.removeRow(i);
    			
    			// Update data structure 
    			--numFiles;
    			fileNames.remove(i);
    			lastMod.remove(i);
    			
    			// Make sure all files are indexed
    			updateIndex();
    		} // Else file no longer exists
		} // For
		writeIndexFile();
	} // updateIndex 
	
	// Parses file
	public void parseFile(File file)
	{
		try
		{
			Scanner addedFile = new Scanner(file);
			while(addedFile.hasNext())
			{
				// Read and index words//this is a test
				wordIndex.add(addedFile.next()); 
			} // While
		
			// Close the file
			addedFile.close();
		} // Try
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} // Catch
	} // parseFile
	
	//Write the Index file
	public void writeIndexFile()
	{
		PrintWriter outputFile;
		try 
		{
			outputFile = new PrintWriter(indexFile);
			outputFile.println(numFiles);
			if (numFiles > 0) // If files have been indexed
			{
				// Loop to write file names and last modification dates/times
				for (int i = 0; i <= (numFiles - 1); ++i) 
				{
					// Write file Name
					outputFile.println(fileNames.get(i));

					// Write last modification date/time
					outputFile.println(lastMod.get(i));
				} // For
				// Loop to write indexed words
				for (int i = 0; i <= (wordIndex.size() - 1); ++i)
				{
					// Write indexed words
					outputFile.println(wordIndex.get(i));
				} // For
			} // If files have been indexed
			
			// Close the file
			outputFile.close();
		} // Try
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} // Catch
	} // writeIndexFile
	
	// Read the index file
	public void readIndexFile()
	// Need to be able to handle corrupted file? - Doug Linkhart
	{
		try 
		{
			Scanner inputFile = new Scanner(indexFile);
			numFiles = inputFile.nextInt();
			if (numFiles > 0) // If files have been indexed
			{
				// Loop to read file names and last modification dates/times
				for (int i = 1; i <= numFiles; ++i) 
				{
					// Read file Name
					fileNames.add(inputFile.next()); // Read file Name

					// Read last modification date/time
					lastMod.add(inputFile.nextLong()); 
				} // For
				while(inputFile.hasNext()) // While there are more words...
				{
					// Read indexed words
					wordIndex.add(inputFile.next()); 
				} // While
			} // If files have been indexed
			
			inputFile.close(); // Close the file
		} // Try
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} // Catch
	} // readIndexFile
	
	// Creates a panel with label containing specified text
	protected JComponent textPanel(String text){
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.add(filler);
		return panel; 
	} //end filler component
	
	// Build main container
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Search Engine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//add tabbed pane to main container
		frame.add(new SearchEngine(), BorderLayout.CENTER);
		
		frame.setSize( 800, 500);
		frame.setLocationRelativeTo( null ); // Center frame on screen
		frame.setVisible( true );
		frame.setResizable(false); // dont let user resize window to keep gui formatting constant 
	} // creatAndShowGUI()

	public static void main(String[] args) {
		createAndShowGUI();
	} // main
}//end class SearchEngine