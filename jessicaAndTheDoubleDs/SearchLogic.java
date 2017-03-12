package jessicaAndTheDoubleDs;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SearchLogic {


	// Search Terms
    static StringBuilder sbStringToParse = new StringBuilder();
	static String nextLexeme = "";
	
    static void Search(){
    	
    	JOptionPane.showMessageDialog( 
		  		null, 
		  		"You clicked the Search button...", 
		  		"SEARCH!!!", 
		  		JOptionPane.INFORMATION_MESSAGE );
				
				sbStringToParse.append(txtSearchTerms.getText() );
				
				StringBuilder sbResults = new StringBuilder();
				sbResults.append( "No results found. \r\n \r\n" );
				sbResults.append( "You searched for:\r\n \r\n" );
				
				// While there are still Search Terms in the string
				while ( sbStringToParse.length() > 0 ) 
				{
					nextLexeme = Utils.getNextLexeme(); // Get the next Search Term (lexeme)
					sbResults.append(nextLexeme + " " ); 
					
					// If OR and not end of search-term string
					if ( GUI.orBtnSelected && sbStringToParse.length() > 0 ) 
						sbResults.append( "OR " );
									
					// If AND and not end of search-term string
					if ( GUI.andBtnSelected && sbStringToParse.length() > 0 )
						sbResults.append( "AND " );
									
					// If PHRASE and end of search-term string
					if ( GUI.phraseBtnSelected && sbStringToParse.length() <= 0 )
						sbResults.append( "(PHRASE; terms in this order) " );
				} // While
				
				// Write string to results text area
				txtResults.setText( sbResults.toString() );
				
			} //search
    
    
    static void or(){
    	
		GUI.orBtnSelected     = true;
		GUI.andBtnSelected    = false;
		GUI.phraseBtnSelected = false;
    }
    	
    }
	

