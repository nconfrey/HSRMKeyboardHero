package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class ComboPanel extends JPanel {
	
	private static final int COMBO_BLOCK_COUNT = 5;
	
	private static final int SPACING_X = 5;

	
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int alphaWert = 100;
		
		
		float width = (getWidth() - (6 * SPACING_X)) / COMBO_BLOCK_COUNT;
		float height = getHeight() - 2 * SPACING_X;
		Color color = new Color(255,200,200);
		
		for(int count = 0; count < COMBO_BLOCK_COUNT; count++) {
			
			color = new Color(color.getRed(), color.getGreen() - 35, color.getBlue() - 35);
			g2.setColor(color);
			float x = count * width + ((count + 1) * SPACING_X);
			g2.fill(new Rectangle2D.Float(x, SPACING_X, width, height));
			
			g2.setFont(new Font("sanserif", Font.PLAIN, 20));
			g2.setPaint(Color.WHITE);
			g2.drawString("XXX", x, height - 12);
			
		}
		
		/*
		g2.draw(new Rectangle2D.Float(0, 0, getWidth()/COMBO_BLOCK_COUNT, getHeight()));
		g2.setColor(Color.PINK);
		g.setColor(Color.GREEN);
		g.fillRect(getWidth()-(COMBO_BLOCK_COUNT-1)*(getWidth()/COMBO_BLOCK_COUNT) , 0 , getWidth()/COMBO_BLOCK_COUNT, getHeight());
		g.setColor(Color.BLUE);
		g.fillRect(getWidth()- (COMBO_BLOCK_COUNT-2)*(getWidth()/COMBO_BLOCK_COUNT), 0 , getWidth()/COMBO_BLOCK_COUNT, getHeight());
		g.setColor(Color.CYAN);
		g.fillRect(getWidth()- (COMBO_BLOCK_COUNT-3)*(getWidth()/COMBO_BLOCK_COUNT), 0 , getWidth()/COMBO_BLOCK_COUNT, getHeight());
		g.setColor(Color.YELLOW);
		g.fillRect(getWidth()- (COMBO_BLOCK_COUNT-4)*(getWidth()/COMBO_BLOCK_COUNT), 0 , getWidth()/COMBO_BLOCK_COUNT, getHeight());
		*/
	}
}
