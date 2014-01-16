package view;

import model.StrokeKey;

public class GuitarPaneLayout {

	private int width;
	
	 public GuitarPaneLayout(int width) {
		this.width = width;
	}
	 
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getPositionForLine(int line)  {
		int oneWidth = getGuitarWidth() / 8;
		return getGuitarOffset()  + (line + 2) * oneWidth;
	}
	
	public int getPositionForKey(StrokeKey key) {
		return getPositionForLine(key.getPosition());
	}
	
	public int getGuitarWidth() {
		return width - 200;
	}
	
	public int getGuitarOffset() {
		return (width - getGuitarWidth()) / 2;
	}
}
