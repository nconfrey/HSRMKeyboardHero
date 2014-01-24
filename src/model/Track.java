package model;

import java.io.File;
import java.io.Serializable;

import controller.KeyboardHero;
import controller.player.MP3PlayerTrack;

public class Track implements Serializable{

	private StrokeSet strokeSet;
	private MP3PlayerTrack mp3;
	
	public Track(MP3PlayerTrack mp3) {
		this.mp3 = mp3;
	}
	
	public Track(String mp3Name) {
		File mp3File = null;
		try {
			mp3File = new File(mp3Name);
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
	
	public String toString(){
		return mp3.toString();
	}
}
