/**
 * Background Panel 
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import controller.PlayerController;

public class GuitarBackgroundPane extends JPanel {

	private static final int GUITAR_WIDTH = 450;
	private GuitarPane guitarPane;

	/**
	 * Instantiates a new guitar background pane.
	 * 
	 * @param playerController the player controller
	 */
	public GuitarBackgroundPane(PlayerController playerController) {
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

		guitarPane = new GuitarPane(playerController);
		guitarPane.setPreferredSize(new Dimension(GUITAR_WIDTH, 0));

		add(guitarPane, c);
	}

	/**
	 * Gets the guitar pane.
	 * 
	 * @return the guitar pane
	 */
	public GuitarPane getGuitarPane() {
		return guitarPane;
	}
}
