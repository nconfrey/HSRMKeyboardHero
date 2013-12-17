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

	public void setLength(int length) {
		this.length = length;
	}

	public void setStartFrame(int startFrame) {
		this.startFrame = startFrame;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + startFrame;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stroke other = (Stroke) obj;
		if (key != other.key)
			return false;
		if (startFrame != other.startFrame)
			return false;
		return true;
	}

	
}
