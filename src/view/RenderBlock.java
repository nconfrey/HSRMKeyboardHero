package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import model.Stroke;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RenderBlock {

	private List<Stroke> strokes;
	private BufferedImage buffer;
	private int width;
	private int startFrame;
	private int defaultFrameCount;
	
	public RenderBlock(int width, int startFrame, int defaultFrameCount, List<Stroke> strokeBlocks) {
		this.width = width;
		this.startFrame = startFrame;
		this.defaultFrameCount = defaultFrameCount;
		this.strokes = strokeBlocks;
	}
	
	public void render() {
		int height = (int)Layouter.getPixelForFrame(getMaxFrame());
		if (height > 0) {
			this.buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D)buffer.getGraphics().create();
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
		             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			//g.setColor(randomColor());
			g.fillRect(0, 0, this.buffer.getWidth(), this.buffer.getHeight());
			g.translate(0, - Layouter.getPixelForFrame(startFrame));
			for (Stroke stroke : strokes) {
				StrokeBlock p = new StrokeBlock(stroke);
				p.paintComponent(g);
			}
		}
	}
	
	private Color randomColor() {
		Random rand = new Random();
		
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		
		return new Color(r, g, b, 0.5f);
	}
	
	public int getStartFrame() {
		return startFrame - (getMaxFrame() - defaultFrameCount);
	}

	public void drawBuffer(Graphics2D g) {
		if (this.buffer != null) {
			g.drawImage(this.buffer, null, 0, 0);
		}
	}
	
	public int getMaxFrame(){
		int maxFrame = 0;
		for (Stroke stroke : strokes) {
			maxFrame = Math.max(maxFrame, getFrameOffset(stroke) + stroke.getLength());
		}
		return maxFrame;
	}
	
	private int getFrameOffset(Stroke stroke) {
		return stroke.getStartFrame() - startFrame;
	}
}
