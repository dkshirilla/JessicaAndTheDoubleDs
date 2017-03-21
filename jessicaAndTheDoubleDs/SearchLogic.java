package jessicaAndTheDoubleDs;

import java.util.Scanner;

public class SearchLogic{
	
	public static void doSearch()
	{
		Scanner searchTerms = new Scanner(( GUI.txtSearchTerms).getText());
	
		StringBuilder sbResults = new StringBuilder();
		sbResults.append( "No results found. \r\n \r\n" );
		sbResults.append( "You searched for:\r\n \r\n" );
	
		// While there are still search terms...
		while(searchTerms.hasNext()) 
		{
			sbResults.append( searchTerms.next() + " " ); 
		
			// If OR and not last search term
			if ( GUI.orBtnSelected && searchTerms.hasNext() ) 
				sbResults.append( "OR " );
						
			// If AND and not last search term
			if ( GUI.andBtnSelected && searchTerms.hasNext() )
				sbResults.append( "AND " );
						
			// If PHRASE and last search term
			if ( GUI.phraseBtnSelected && !searchTerms.hasNext() )
				sbResults.append( "(PHRASE; terms in this order) " );
		} // While
	
		// Write string to results text area
		(GUI.txtResults).setText( sbResults.toString() );
	
		// Close scanner
		searchTerms.close();
} // doSearch
	
}


