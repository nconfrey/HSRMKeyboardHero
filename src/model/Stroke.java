package model;

public class Stroke {
	
	private StrokeKey key;
	private int length;
	private int startFrame;
	
	public Stroke(StrokeKey strokeKey, int startFrame, int length) {
		this.key = strokeKey;
		this.startFrame = startFrame;
		this.length = length;
	}

	public StrokeKey getKey() {
		return key;
	}

	public int getLength() {
		return length;
	}

	public int getStartFrame() {
		return startFrame;
	}

	
}
