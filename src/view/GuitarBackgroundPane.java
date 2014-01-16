package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GuitarBackgroundPane extends JPanel {

	private GuitarPaneLayout layout;
	
	public GuitarBackgroundPane() {
		layout = new GuitarPaneLayout(getWidth());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		layout.setWidth(getWidth());
	
		g.setColor(new Color(0xDDDDDD));
		g.fillRect(layout.getGuitarOffset(), 0, layout.getGuitarWidth(), getHeight());
		
		g.setColor(new Color(0xC0C0C0));
		for (int i = 0; i < 5; i++) {
			int x = layout.getPositionForLine(i);
			g.drawLine(x, 0, x, getHeight());
		}
	}
	
}
