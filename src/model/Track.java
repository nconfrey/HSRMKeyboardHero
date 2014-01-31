/**
 * a KeyboardHero Track that holds the music and the StrokeSet
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package model;

import java.io.File;
import java.io.Serializable;

public class Track implements Serializable {

	private static final long serialVersionUID = 7876085004865638208L;
	private StrokeSet strokeSet;
	private MP3PlayerTrack mp3;

	/**
	 * Instantiates a new track.
	 * 
	 * @param mp3 the mp3
	 */
	public Track(MP3PlayerTrack mp3) {
		this.mp3 = mp3;
	}

	/**
	 * Instantiates a new track.
	 * 
	 * @param mp3Name the mp3 name
	 */
	public Track(String mp3Name) {
		File mp3File = null;
		try {
			mp3File = new File(mp3Name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.mp3 = new MP3PlayerLocalTrack(mp3File);
	}

	/**
	 * Gets the stroke set.
	 * 
	 * @return the stroke set
	 */
	public StrokeSet getStrokeSet() {
		return strokeSet;
	}

	/**
	 * Sets the stroke set.
	 * 
	 * @param strokeSet the new stroke set
	 */
	public void setStrokeSet(StrokeSet strokeSet) {
		this.strokeSet = strokeSet;
	}

	/**
	 * Gets the mp3.
	 * 
	 * @return the mp3
	 */
	public MP3PlayerTrack getMp3() {
		return mp3;
	}

	/**
	 * Sets the mp3.
	 * 
	 * @param mp3 the new mp3
	 */
	public void setMp3(MP3PlayerTrack mp3) {
		this.mp3 = mp3;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return mp3.toString();
	}
}
