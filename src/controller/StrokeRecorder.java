package controller;

import model.StrokeKey;
import model.StrokeSet;
import model.Track;
import controller.player.MP3Player;
import controller.player.Playlist;

public class StrokeRecorder implements GuitarStringListener {
	
	private Track track;
	MP3Player player;
	
	public StrokeRecorder(Track track) {
		this.player = new MP3Player();
		setTrack(track);
	}

	public void setTrack(Track track) {
		this.track = track;
		
		// set track to player
		Playlist currentPlaylist = player.getCurrentPlaylist();
		if(currentPlaylist == null) {
			currentPlaylist = player.createPlayList("defaultPlaylist");
		}
		currentPlaylist.addTrack(track.getMp3());
		player.selectPlaylist(0);
		player.selectTrack(0);
	}

	public void record() {
		player.play();
	}
	
	public void guitarStringPressed(StrokeKey strokeKey) {
		System.out.println("pressed");
	}
	
    public void GuitarStringReleased(StrokeKey strokeKey) {
    	System.out.println("released");
    }
	
}
