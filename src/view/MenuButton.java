/**
 * a JButton for the main menu
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package view;

import helper.KeyboardHeroConstants;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class MenuButton extends JButton {

	private Color backgroundColor;

	/**
	 * Instantiates a new menu button.
	 */
	public MenuButton() {
		super();
		backgroundColor = new Color(KeyboardHeroConstants.COLOR_PRIMARY);
		setDefaultParams();
	}

	/**
	 * Instantiates a new menu button.
	 * 
	 * @param text the text
	 */
	public MenuButton(String text) {
		super(text);
		backgroundColor = new Color(KeyboardHeroConstants.COLOR_PRIMARY);
		setDefaultParams();
	}

	/**
	 * Instantiates a new menu button.
	 * 
	 * @param text the text
	 * @param backgroundColor the background color
	 */
	public MenuButton(String text, Color backgroundColor) {
		super(text);
		this.backgroundColor = backgroundColor;
		setDefaultParams();
	}

	/**
	 * Sets the default params.
	 */
	private void setDefaultParams() {
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		this.setBackground(backgroundColor);
		this.setOpaque(true);
		this.setForeground(Color.WHITE);
		this.setFont(new Font("SansSerif", Font.BOLD, 14));
	}

}
