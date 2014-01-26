package gui;

import model.Score;
import model.Track;
import controller.KeyController;
import controller.ScoreController;
import controller.player.MP3Player;
import controller.recorder.StrokeRecorder;

public class PlayerController {

	private MP3Player player;
	private StrokeRecorder recorder;
	private KeyController keyController;
	private Track track;
	private ScoreController scoreController;
	private boolean isRecording;

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
		scoreController = new ScoreController();
		
		keyController.addGuitarStringListener(recorder);
		player.addPlayerListener(scoreController);
		recorder.addStrokeRecorderListener(scoreController);
		
		setRecording(false);
	}
	
	public void play() {
		recorder.setTrack(track);
		player.play();
	}
	
	public void stop() {
		player.stop();
	}
	
	public void pauseResume() {
		player.pauseResume();
	}

	public boolean isRecording() {
		return isRecording;
	}
	
	public void setRecording(boolean isRecording) {
		this.isRecording = isRecording;
		
		if(scoreController != null) {
			scoreController.setRecording(isRecording);
		}
		
		if(recorder != null) {
			recorder.setRecordingMode(isRecording);
		}
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
	public ScoreController getScoreController() {
		return scoreController;
	}
}
