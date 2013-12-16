package model;

import java.io.File;

import controller.player.MP3PlayerTrack;

public class Track {

	private StrokeSet strokeSet;
	private MP3PlayerTrack mp3;
	
	public Track(MP3PlayerTrack mp3) {
		this.mp3 = mp3;
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
