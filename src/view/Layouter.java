package view;

import model.StrokeKey;

public class Layouter {

	public static double getPixelForFrame(int frame) {
		return frame * 0.5;
	}
	
	public static int getFrameForPixel(double pixel) {
		return (int)(pixel / 0.5);
	}
	
	public static double getPixelForStroke(StrokeKey stroke) {
		return (stroke.getPosition() + 1) * 40.0;
	}
	
	public static final int STROKE_WIDTH = 30;
	public static final int BLOCK_FRAME_COUNT = 200;
}
