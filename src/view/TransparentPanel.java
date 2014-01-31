/**
 * Transparent Panel, Only for make Panel transparent
 * 
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package view;

import java.awt.Graphics;

import javax.swing.JPanel;

public class TransparentPanel extends JPanel {

	/*
	 * method which helps that transparent Panel works
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}
}
