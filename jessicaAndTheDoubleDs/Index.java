package jessicaAndTheDoubleDs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Index {
	
	//for jtable
	DefaultTableModel fileTableModel = new DefaultTableModel();
	Object[] row = new Object[2];
	final int fileColumn = 0;
	final int statusColumn = 1;
	
	
	File indexFile = new File( "index.txt" );

	// Index data structure
	int numFiles;
	ArrayList<String> fileNames = new ArrayList<>();
	List<Long> lastMod = new ArrayList<Long>();
	ArrayList<String> wordIndex = new ArrayList<>();
	/* Ref for 2-D array lists:  List<List<String>> listOfLists = new ArrayList<List<String>>();
	   listOfLists.add(new ArrayList<String>()) */
	
	
	
	
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
			} // writeIndexFile
			
			public void readIndexFile()
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
							  		JOptionPane.INFORMATION_MESSAGE ); */
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
			 // readIndexFile


	
	
	// If the index file exists...
	if ( indexFile.exists() )
	{
		readIndexFile();

		// This is only for testing
		JOptionPane.showMessageDialog( 
		  		null, 
		  		("numFiles = " + Integer.toString( numFiles )), 
		  		"SEARCH!!!", 
		  		JOptionPane.INFORMATION_MESSAGE );
		
		//populate jtable in files tab

//              Date date = new Date(lastMod);
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
	
			    		//row[statusColumn] = date;
			    		
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
	
/* Suggested index file format:
* 2                  // Number of indexed files (0 if none)... these comments will not be in the file!
* c:\data.txt        // First file pathname
* (timestamp in integer form) 
* c:\data2.txt       // Second file pathname
* (timestamp in integer form)
* These              // Indexed words to EOF...
* are 
* words
* that
* were 
* in
* the
* files
* EOF
*/

}
			}//end class
