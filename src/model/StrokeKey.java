package model;

import java.awt.event.KeyEvent;

public enum StrokeKey {
	F1,F2,F3,F4,F5,INVALID;
	
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
		} else {
			return StrokeKey.INVALID;
		}
		
	}
}
