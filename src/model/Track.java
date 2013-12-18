package model;

import java.io.File;

import controller.KeyboardHero;
import controller.player.MP3PlayerTrack;

public class Track {

	private StrokeSet strokeSet;
	private MP3PlayerTrack mp3;
	
	public Track(MP3PlayerTrack mp3) {
		this.mp3 = mp3;
	}
	
	public Track (String mp3Name) {
		File mp3File = null;
		try {
			mp3File = new File(KeyboardHero.class.getResource("/"+mp3Name).toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.mp3 = new MP3PlayerTrack(mp3File);
	}

	public StrokeSet getStrokeSet() {
		return strokeSet;
	}

	public void setStrokeSet(StrokeSet strokeSet) {
		this.strokeSet = strokeSet;
	}

	public MP3PlayerTrack getMp3() {
		return mp3;
	}

	public void setMp3(MP3PlayerTrack mp3) {
		this.mp3 = mp3;
	}
	
}
