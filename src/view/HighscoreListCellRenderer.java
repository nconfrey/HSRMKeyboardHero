/**
 * Cell Renderer for Highscore List display
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
import java.awt.Component;
import java.awt.Font;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;

public class HighscoreListCellRenderer extends PlayListCellRenderer {
	private ImageIcon goldStar;
	private ImageIcon silverStar;
	private ImageIcon bronzeStar;

	/**
	 * Instantiates a new highscore list cell renderer.
	 */
	public HighscoreListCellRenderer() {
		init();
	}

	/**
	 * Inits the resources
	 */
	public void init() {
		try {
			goldStar = new ImageIcon(ImageIO.read(getClass()
					.getResourceAsStream("/gold.png")));
		} catch (IOException e) {
		}
		try {
			silverStar = new ImageIcon(ImageIO.read(getClass()
					.getResourceAsStream("/silver.png")));
		} catch (IOException e) {
		}
		try {
			bronzeStar = new ImageIcon(ImageIO.read(getClass()
					.getResourceAsStream("/bronze.png")));
		} catch (IOException e) {
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax
	 * .swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		Component c = super.getListCellRendererComponent(list, value, index,
				isSelected, cellHasFocus);

		if (c instanceof JLabel) {
			JLabel label = (JLabel) c;
			label.setHorizontalTextPosition(SwingConstants.RIGHT);
			label.setIconTextGap(15);
			if (index == 0) {
				label.setIcon(goldStar);
			} else if (index == 1) {
				label.setIcon(silverStar);
			} else if (index == 2) {
				label.setIcon(bronzeStar);
			}
		}
		c.setBackground(new Color(KeyboardHeroConstants.COLOR_PRIMARY));
		c.setForeground(Color.WHITE);
		c.setFont(new Font("SansSerif", Font.BOLD, 14));

		setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

		return c;
	}
}
