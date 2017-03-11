package jessicaAndTheDoubleDs;

import java.awt.BorderLayout;
import java.awt.Color;
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

public class GUI extends JPanel implements ActionListener{
	
	//Eclipse whines if this line isn't here...
		private static final long serialVersionUID = 1L;
		
		// long lastMod;
		
		public GUI(){
			//used to set tabs to top left
			super (new GridLayout(1,1));
			
			//Build tabs pane
			JTabbedPane tabbedPane = new JTabbedPane();
			
			// Add Search panel
			JComponent searchPanel = textPanel( "" );
			// Add Search tab
			tabbedPane.addTab("Search", searchPanel);
			
			JComponent txtSearchTerms;
			// Add border to Search Term text box
			txtSearchTerms.setBorder(BorderFactory.createLineBorder(Color.black));
			// Add Search Terms text box to panel
			searchPanel.add(txtSearchTerms);
			
			
				
			//Create JTable for files tab
			DefaultTableModel fileTableModel = new DefaultTableModel();
			Object[] row = new Object[2];
			JTable fileTable = new JTable();
			String[] columnNames = {"File","Status"};
			fileTableModel.setColumnIdentifiers(columnNames);
			fileTable.setModel(fileTableModel);
			fileTable.setPreferredScrollableViewportSize(new Dimension(500,300));
			JScrollPane jps = new JScrollPane(fileTable);
			fileTable.setFillsViewportHeight(true);
			
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
		
			//add jtable to file tab
			files.add(jps);
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
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				
				if (f != null)
				{
					fileName = f.getAbsolutePath();
				
	//add file to jtable in file tab
//				lastMod = f.lastModified();
	//Date dt = new Date(lastMod);
//				row[fileColumn] = fileName;
				
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
					
	// writeFilePath(fileName, f, numFiles);
					
				} // If file name not null
				else // Handle possibility of null (no file selected), which would cause exception
					JOptionPane.showMessageDialog( 
							null, 
							"You didn't select a file", 
							"NO FILE SELECTED!!!", 
							JOptionPane.WARNING_MESSAGE );
			} // If Add File
			
			else if (e.getActionCommand().equals("rmvFile"))
			{
				JOptionPane.showMessageDialog(null,"You clicked the remove file button!", "REMOVE!!",
						JOptionPane.INFORMATION_MESSAGE);
				
	/* Need a way to select the file to be removed
	 * Decrement number of files in index 
	 * Remove file pathname from list of file pathnames
	 * Eventually, the indexed words pertaining to this file will have to removed from the 
	 * data structure (or at least not written back to disk), but this is probably too much 
	 * for Part II of the project
	 * Rewrite the index			
	 */
			} // If Remove File
			
			else if (e.getActionCommand().equals("updateFiles"))
			{
				JOptionPane.showMessageDialog(null,"You clicked the update index button!", "UPDATE!!",
						JOptionPane.INFORMATION_MESSAGE);
				
	/* For each file in index...
	 * 		Get the file timestamp from the file to be indexed 
	 * 		Parse the file into words (can use getNextLexeme() or scanner()) 
	 *      Update data structure 
	 *      If file does not exist, remove it and its words from index
	 * Rewrite the index
	 * Update each file status display to "indexed" 			
	*/
			
				// I did part of this (below), but more needs to be added  - Doug Linkhart
				
				// Need to remove files that no longer exist (and their contents) from index
				
				// Clear index so that it can be regenerated
				wordIndex.clear();
							
				for (int i = 0; i <= (numFiles - 1); ++i)
				{
					/* Create a reference to the file 
	 		   		   and get its last modified date/time */
					File file = new File(fileNames.get(i));
					long timeModified = (long)file.lastModified();
					
					// Set the last modified parameter in the data structure
					lastMod.set(i, timeModified);
								
					parseFile(file);
					
					writeIndexFile();
					
					// Update table
	    		    fileTableModel.setValueAt("Indexed", i, statusColumn);
				} // For
			
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
		
		// Read the index file
		
		//Write file path to index file with time stamp of last modified
	/*	public void writeFilePath(String fileName, File f, int numFiles)
		{
			try
			{
			BufferedWriter bw = null;
			FileWriter fw = null;
			fw = new FileWriter(indexFile.getAbsoluteFile(),true);
			bw = new BufferedWriter(fw);
			String fileInfo = (fileName + " " + f.lastModified());
			bw.write(fileInfo);
			bw.newLine();
			bw.close();
			fw.close();
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			numFiles++;
		} */
		
		public void parseFile(File file)
		{
			try
			{
				Scanner addedFile = new Scanner(file);
				while(addedFile.hasNext())
				{
					// Read and index words
					wordIndex.add(addedFile.next()); 
					// Later, the indices will have to be read, also
				} // While
			
				// Close the file
				addedFile.close();
			}
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			} // Catch
		} // parseFile
		
		//Write the Index file
/*		public void writeIndexFile()
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
						// Later, the indices will have to be written, also
					} // For
				} // If files have been indexed
				
				// Close the file
				outputFile.close();
			} // Try
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			} // Catch
		} // writeIndexFile*/
		
		
/*		public void readIndexFile()
		// Need to be able to handle corrupted file? - Doug Linkhart
		{
		//	Scanner inputFile;
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
						// This is only for testing
	/*					JOptionPane.showMessageDialog( 
						  		null, 
						  		("File Names = " + inputFile.next() ), 
						  		"SEARCH!!!", 
						  		JOptionPane.INFORMATION_MESSAGE ); 
						// Read last modification date/time
						lastMod.add(inputFile.nextLong()); 
					} // For
					while(inputFile.hasNext())
					{
						// Read indexed words
						wordIndex.add(inputFile.next()); 
						// Later, the indices will have to be read, also
					} // While
				} // If files have been indexed
				
				inputFile.close(); // Close the file
			} // Try
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			} // Catch
		} // readIndexFile*/
	
	
	// Creates a panel with label containing specified text
	protected JComponent textPanel(String text){
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.add(filler);
		return panel; 
	} //end filler component
	
	
	//build main container
		static void createAndShowGUI() {
			
			
			
			JFrame frame = new JFrame("Search Engine");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize( 500, 500);
			frame.setLocationRelativeTo( null ); // Center frame on screen
			frame.setVisible( true );
			frame.setResizable(false); // dont let user resize window to keep gui formatting constant 
			
			
			
		}//end creatAndShowGUI()

}
