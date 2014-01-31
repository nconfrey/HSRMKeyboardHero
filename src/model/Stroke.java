/**
 * Single Stroke referencing a key and its position and length
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package model;

import java.io.Serializable;

public class Stroke implements Serializable {

	private static final long serialVersionUID = 3344807030757232975L;
	private StrokeKey key;
	private int length;
	private int startFrame;

	/**
	 * Instantiates a new stroke.
	 * 
	 * @param strokeKey the stroke key
	 * @param startFrame the start frame
	 * @param length the length
	 */
	public Stroke(StrokeKey strokeKey, int startFrame, int length) {
		this.key = strokeKey;
		this.startFrame = startFrame;
		this.length = length;
	}

	/**
	 * Gets the key.
	 * 
	 * @return the key
	 */
	public StrokeKey getKey() {
		return key;
	}

	/**
	 * Gets the length.
	 * 
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Gets the start frame.
	 * 
	 * @return the start frame
	 */
	public int getStartFrame() {
		return startFrame;
	}

	/**
	 * Gets the end frame.
	 * 
	 * @return the end frame
	 */
	public int getEndFrame() {
		return startFrame + length;
	}

	/**
	 * Sets the length.
	 * 
	 * @param length the new length
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Sets the start frame.
	 * 
	 * @param startFrame the new start frame
	 */
	public void setStartFrame(int startFrame) {
		this.startFrame = startFrame;
	}

	/**
	 * Checks if is open.
	 * 
	 * @return true, if is open
	 */
	public boolean isOpen() {
		return isEmpty();
	}

	/**
	 * Checks if is empty.
	 * 
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return this.length == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + startFrame;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Stroke " + key + " <" + startFrame + "|" + length + ">";
	}

}
