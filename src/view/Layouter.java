package view;

public class Layouter {

	public static double getPixelForFrame(int frame) {
		return frame * 1.0;
	}
	
	public static int getFrameForPixel(double pixel) {
		return (int)(pixel / 1.0);
	}
	
	public static double getPixelForStroke(int stroke) {
		return stroke * 15.0;
	}
	
	public static final int STROKE_WIDTH = 15;
	public static final int BLOCK_FRAME_COUNT = 50;
}
