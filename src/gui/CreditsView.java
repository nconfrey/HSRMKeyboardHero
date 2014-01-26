package gui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class CreditsView extends GHPanel {
	
	JLabel creditTitle;
	JLabel creditText;
	JPanel creditTitlePanel;
	JLabel hsrmTitle;
	
	public CreditsView(){
		
		creditTitle = new JLabel("Credits");
		hsrmTitle = new JLabel("Hochschule RheinMain");
		creditText = new JLabel();
		creditText.setText("<html><body>Keyboard Hero Version 1.0<br> <br>Ein EIBO Projekt von:<br> <br>"
				+ " Simon Seyer <br> Moritz Möller <br> Martin Juhasz <br> Julia Kraft <br> <br> </body></html>");
		
		
		this.setLayout(new MigLayout("insets 0 200 20 200, fillx","[][]20[]", "[]20[]"));
		
		this.add(this.creditTitle, "wrap, growx");
		this.add(this.hsrmTitle, "wrap, growx");
		this.add(this.creditText, "wrap, growx");
		
		
		this.setBackground(Color.WHITE);
		
	}

}
