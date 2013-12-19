package view;

import model.StrokeKey;

public class Layouter {

	public static int getPixelForFrame(int frame) {
		return frame * 1;
	}
	
	public static int getFrameForPixel(double pixel) {
		return (int)(pixel / 0.5);
	}
	
	public static int getPixelForStroke(StrokeKey stroke) {
		return (stroke.getPosition() + 1) * 40;
	}
	
	public static final int STROKE_WIDTH = 30;
	public static final int BLOCK_FRAME_COUNT = 200;
}
