package jessicaAndTheDoubleDs;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SearchLogic {

	
	// Initialize radio button status
	Boolean orBtnSelected     = true,
			andBtnSelected    = false,
			phraseBtnSelected = false;
	
	// These need to be accessible outside of the GIU method
	// Search Tab text fields
	JTextField txtSearchTerms = new JTextField( "Enter search terms here", 40 );
	JTextArea txtResults = new JTextArea(22, 40);
	

	// Search Terms
	StringBuilder sbStringToParse = new StringBuilder();
}
