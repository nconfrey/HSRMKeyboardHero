package model;

import java.awt.Color;
import java.awt.event.KeyEvent;

public enum StrokeKey {
	F1(0, new Color(0x66FF33)),
	F2(1, new Color(0xF5B800)),
	F3(2, new Color(0x3366FF)),
	F4(3,new Color(0xFF6633)),
	F5(4, new Color(0x33CCFF)),
	ENTER(-1, null), 
	INVALID(-1, null);
	
	public static final int STROKE_COUNT = 5;
	private int value;
	private Color color;
	
	private StrokeKey(int value, Color color) {
		this.value = value;
		this.color = color;
	}
	
	public static StrokeKey keyForCode(int keyCode) {
		if(keyCode == KeyEvent.VK_F1) {
			return StrokeKey.F1;
		} else if(keyCode == KeyEvent.VK_F2) {
			return StrokeKey.F2;
		} else if(keyCode == KeyEvent.VK_F3) {
			return StrokeKey.F3;
		} else if(keyCode == KeyEvent.VK_F4) {
			return StrokeKey.F4;
		} else if(keyCode == KeyEvent.VK_F5) {
			return StrokeKey.F5;
		} else if(keyCode == KeyEvent.VK_ENTER) {
			return StrokeKey.ENTER;
		} else {
			return StrokeKey.INVALID;
		}
	}
	
	public static StrokeKey keyForPosition(int position) {
		return values()[position];
	}
	
	public int getPosition() {
		return value;
	}
	
	public Color getColor() {
		return color;
	}
	
	public boolean isGuitarString() {
		return (this != StrokeKey.ENTER && this != StrokeKey.INVALID);
	}
}
