package jessicaAndTheDoubleDs;

public class Utils {


	
	// Parses the Search Term string by returning the next lexeme
			//Need to move this to Utils Class
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

}
