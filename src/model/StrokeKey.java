/**
 * A Single Key
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
	F1(0, "F1", new Color(0x5cb81b), new Color(0x75dd2c)), F2(1, "F2",
			new Color(0xb72b19), new Color(0xff1c00)), F3(2, "F3", new Color(
			0xecc630), new Color(0xf8f263)), F4(3, "F4", new Color(0x508dc3),
			new Color(0x57abf6)), F5(4, "F5", new Color(0x9c591c), new Color(
			0xd98538)), ENTER(-1, "ENTER", null, null), INVALID(-1, "INVALID",
			null, null);

	public static final int STROKE_COUNT = 5;
	private int value;
	private String text;
	private Color primaryColor;
	private Color secondaryColor;

	/**
	 * Instantiates a new stroke key.
	 * 
	 * @param value the value
	 * @param text the text
	 * @param color the color
	 */
	private StrokeKey(int value, String text, Color primaryColor,
			Color secondaryColor) {
		this.value = value;
		this.text = text;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
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
	 * Key for position on the guitar pane.
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
	 * Gets the primary color.
	 * 
	 * @return the primary color
	 */
	public Color getPrimaryColor() {
		return primaryColor;
	}

	/**
	 * Gets the secondary color.
	 * 
	 * @return the secondary color
	 */
	public Color getSecondaryColor() {
		return secondaryColor;
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
