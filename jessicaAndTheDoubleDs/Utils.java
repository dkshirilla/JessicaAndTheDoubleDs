package jessicaAndTheDoubleDs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utils {
			

			public static void parseFile(File file)
			{
				try
				{
					Scanner addedFile = new Scanner(file);
					while(addedFile.hasNext())
					{
						// Read and index words
						Index.wordIndex.add(addedFile.next()); 
					} // While
				
					// Close the file
					addedFile.close();
				} // Try
				catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				} // Catch
		} // parseFile


}//end utils class
