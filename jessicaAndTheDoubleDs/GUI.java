package jessicaAndTheDoubleDs;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class GUI {
	
	
	//build main container
		static void createAndShowGUI() {
			JFrame frame = new JFrame("Search Engine");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//add tabbed pane to main container
			frame.add(new SearchEngine(), BorderLayout.CENTER);
			
			frame.setSize( 500, 500);
			frame.setLocationRelativeTo( null ); // Center frame on screen
			frame.setVisible( true );
			frame.setResizable(false); // dont let user resize window to keep gui formatting constant 
		}//end creatAndShowGUI()

}
