package jessicaAndTheDoubleDs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Index {

	
	final static int fileColumn = 0;
	final static int statusColumn = 1;	
	static File indexFile = new File( "index.txt" );

	// Index data structure
	static int numFiles;
	static ArrayList<String> fileNames = new ArrayList<>();
	static List<Long> lastMod = new ArrayList<Long>();
	static ArrayList<String> wordIndex = new ArrayList<>();
	/* Ref for 2-D array lists:  List<List<String>> listOfLists = new ArrayList<List<String>>();
	   listOfLists.add(new ArrayList<String>()) */
	
	//parseFile
	public static void parseFile(File file)
	{
		try
		{
			Scanner addedFile = new Scanner(file);
			while(addedFile.hasNext())
			{
				// Read and index words
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
	
	// Add a file
	public static void addFile()
	{
		// File chooser dialog box
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		File f = chooser.getSelectedFile();
	
		if (f != null) // If a file was selected...
		{
			String fileName = f.getAbsolutePath();
			//for jtable on file tab
			DefaultTableModel fileTableModel = new DefaultTableModel();
			Object[] row = new Object[2];

	
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
			Utils.parseFile(file);
		
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
	
	//remove file
	public static void removeFile()
	{
		// Get row selected by user for deletion
		int rowToRemove =  GUI.fileTable.getSelectedRow();
	
		// Remove the row from the table
		GUI.fileTableModel.removeRow(rowToRemove);
	
		// Update data structure
		--numFiles;
		fileNames.remove(rowToRemove);
		lastMod.remove(rowToRemove);

		updateIndex();
} // removeFile	
	
	// Updates the index file
	public static void updateIndex()
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
		       GUI.fileTableModel.setValueAt("Indexed", i, statusColumn);
    		} // If file exists
    		else // File no longer exists...
    		{
    			// Remove the file from the table
    			GUI.fileTableModel.removeRow(i);
    			
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
	
	//Write the Index file
	public static void writeIndexFile()
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
		} // readIndexFile Method
}//end of Index Class
