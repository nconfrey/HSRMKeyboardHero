package model;

import java.awt.Color;
import java.awt.event.KeyEvent;

public enum StrokeKey {
	F1,F2,F3,F4,F5,ENTER, INVALID;
	
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
	
	public int getPosition() {
		if(this == StrokeKey.F1) {
			return 0;
		} else if(this == StrokeKey.F2) {
			return 1;
		} else if(this == StrokeKey.F3) {
			return 2;
		} else if(this == StrokeKey.F4) {
			return 3;
		} else if(this == StrokeKey.F5) {
			return 4;
		}
		return -1;
	}
	
	public Color getColor() {
		if(this == StrokeKey.F1) {
			return new Color(0x66FF33);
		} else if(this == StrokeKey.F2) {
			return new Color(0xF5B800);
		} else if(this == StrokeKey.F3) {
			return new Color(0x3366FF);
		} else if(this == StrokeKey.F4) {
			return new Color(0xFF6633);
		} else if(this == StrokeKey.F5) {
			return new Color(0x33CCFF);
		}
		return Color.white;
	}
	
	public boolean isGuitarString() {
		return (this != StrokeKey.ENTER && this != StrokeKey.INVALID);
	}
}
