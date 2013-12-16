package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Stroke;

public class StrokeBlock{
	
	private Stroke stroke;

	public StrokeBlock(Stroke stroke) {
		this.stroke = stroke;
	}
	
	public void paintComponent(Graphics g) {
		
		g.setColor(Color.red);
		
		int x = (int)Layouter.getPixelForStroke(stroke.getKey().getValue());//TODO!!
		int y = (int)Layouter.getPixelForFrame(stroke.getStartFrame());
		int width = Layouter.STROKE_WIDTH;
		int height = (int)Layouter.getPixelForFrame(stroke.getLength());
		
		g.fillRoundRect(x, y, width, height, 5, 5);
	}
}
