package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class GuitarBackgroundPane extends JPanel {
	
	private static final int GUITAR_WIDTH = 300;
	
	public GuitarBackgroundPane() {
		super();
		
		GridBagLayout layout = new GridBagLayout();
		
		setLayout(layout);
		setOpaque(false);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.CENTER;
		c.weighty = 1.0;
		
		GuitarPane p = new GuitarPane();
		p.setPreferredSize(new Dimension(GUITAR_WIDTH, 0));
		
		add(p, c);
	}
}
