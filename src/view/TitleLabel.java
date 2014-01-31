/**
 * TitleLabel setting the Title Font  
 * 
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

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import model.KeyboardHeroFontModel;

public class TitleLabel extends JLabel {

	/**
	 * Instantiates a new title label.
	 */
	public TitleLabel() {
		setFont(KeyboardHeroFontModel.getInstance()
				.getFont(KeyboardHeroFontModel.FONT_NIGHTMARE).deriveFont(82f));
		setHorizontalAlignment(SwingConstants.CENTER);
		setForeground(new Color(KeyboardHeroConstants.COLOR_PRIMARY));
	}

	/**
	 * Instantiates a new title label.
	 * 
	 * @param text the text
	 */
	public TitleLabel(String text) {
		this();
		setText(text);
	}
}
