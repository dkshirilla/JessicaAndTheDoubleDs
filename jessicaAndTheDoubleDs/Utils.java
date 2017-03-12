package jessicaAndTheDoubleDs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utils {


	
	// Parses the Search Term string by returning the next lexeme
			public static String getNextLexeme()
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


}
