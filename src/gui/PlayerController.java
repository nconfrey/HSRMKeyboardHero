package gui;

import model.Track;
import controller.KeyController;
import controller.player.MP3Player;
import controller.recorder.StrokeRecorder;

public class PlayerController {

	private MP3Player player;
	private StrokeRecorder recorder;
	private KeyController keyController;
	private Track track;

	private static PlayerController instance;
	
	public static PlayerController getInstance() {
		if(instance == null) {
			instance = new PlayerController();
		}
		return instance;
	}
	
	public PlayerController() {
		player = new MP3Player();
		recorder = new StrokeRecorder(player);
		keyController = new KeyController();
		keyController.addGuitarStringListener(recorder);
	}
	
	public void play() {
		recorder.setTrack(null);
		player.play();
	}
	
	public void record() {
		recorder.setTrack(track);
		player.play();
	}

	public MP3Player getPlayer() {
		return player;
	}

	public StrokeRecorder getRecorder() {
		return recorder;
	}
	
	public Track getTrack() {
		return track;
	}
	
	public void setTrack(Track track) {
		this.track = track;
		player.setTrack(track.getMp3());
		recorder.setTrack(track);
	}
}
