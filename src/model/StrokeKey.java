/**
 * 
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package model;

import java.awt.Color;
import java.awt.event.KeyEvent;

public enum StrokeKey {
	F1(0, "F1", new Color(0x66FF33)), F2(1, "F2", new Color(0xF5B800)), F3(2,
			"F3", new Color(0x3366FF)), F4(3, "F4", new Color(0xFF6633)), F5(4,
			"F5", new Color(0x33CCFF)), ENTER(-1, "ENTER", null), INVALID(-1,
			"INVALID", null);

	public static final int STROKE_COUNT = 5;
	private int value;
	private String text;
	private Color color;

	/**
	 * Instantiates a new stroke key.
	 * 
	 * @param value the value
	 * @param text the text
	 * @param color the color
	 */
	private StrokeKey(int value, String text, Color color) {
		this.value = value;
		this.text = text;
		this.color = color;
	}

	/**
	 * Key for code.
	 * 
	 * @param keyCode the key code
	 * @return the stroke key
	 */
	public static StrokeKey keyForCode(int keyCode) {
		if (keyCode == KeyEvent.VK_F1) {
			return StrokeKey.F1;
		} else if (keyCode == KeyEvent.VK_F2) {
			return StrokeKey.F2;
		} else if (keyCode == KeyEvent.VK_F3) {
			return StrokeKey.F3;
		} else if (keyCode == KeyEvent.VK_F4) {
			return StrokeKey.F4;
		} else if (keyCode == KeyEvent.VK_F5) {
			return StrokeKey.F5;
		} else if (keyCode == KeyEvent.VK_ENTER) {
			return StrokeKey.ENTER;
		} else {
			return StrokeKey.INVALID;
		}
	}

	/**
	 * Key for position.
	 * 
	 * @param position the position
	 * @return the stroke key
	 */
	public static StrokeKey keyForPosition(int position) {
		return values()[position];
	}

	/**
	 * Gets the position.
	 * 
	 * @return the position
	 */
	public int getPosition() {
		return value;
	}

	/**
	 * Gets the color.
	 * 
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return text;
	}

	/**
	 * Checks if is guitar string.
	 * 
	 * @return true, if is guitar string
	 */
	public boolean isGuitarString() {
		return (this != StrokeKey.ENTER && this != StrokeKey.INVALID);
	}
}
