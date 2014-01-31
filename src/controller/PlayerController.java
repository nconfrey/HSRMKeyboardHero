/**
 * Handles a Game
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package controller;

import model.Track;
import controller.player.MP3Player;
import controller.recorder.StrokeRecorder;

public class PlayerController {

	private MP3Player player;
	private PlaylistController playlistController;
	private StrokeRecorder recorder;
	private KeyController keyController;
	private Track track;
	private ScoreController scoreController;
	private boolean isRecording;

	/**
	 * Instantiates a new player controller.
	 */
	public PlayerController() {
		player = new MP3Player();
		playlistController = new PlaylistController();
		recorder = new StrokeRecorder(player);
		keyController = new KeyController();
		scoreController = new ScoreController(player);

		keyController.addGuitarStringListener(recorder);
		player.addPlayerListener(scoreController);
		recorder.addStrokeRecorderListener(scoreController);

		setRecording(false);
	}

	/**
	 * Play.
	 */
	public void play() {
		recorder.setTrack(track);
		scoreController.resetScore();
		player.play();
	}

	/**
	 * Loop.
	 */
	public void loop() {
		player.loop();
	}

	/**
	 * Stop.
	 */
	public void stop() {
		player.stop();
	}

	/**
	 * Pause resume.
	 */
	public void pauseResume() {
		player.pauseResume();
	}

	/**
	 * Checks if is recording.
	 * 
	 * @return true, if is recording
	 */
	public boolean isRecording() {
		return isRecording;
	}

	/**
	 * Reset.
	 */
	public void reset() {
		scoreController.resetScore();

	}

	/**
	 * Sets the recording.
	 * 
	 * @param isRecording the new recording
	 */
	public void setRecording(boolean isRecording) {
		this.isRecording = isRecording;

		if (scoreController != null) {
			scoreController.setRecording(isRecording);
		}

		if (recorder != null) {
			recorder.setRecordingMode(isRecording);
		}

	}

	/**
	 * Gets the player.
	 * 
	 * @return the player
	 */
	public MP3Player getPlayer() {
		return player;
	}

	/**
	 * Gets the playlist controller.
	 * 
	 * @return the playlist controller
	 */
	public PlaylistController getPlaylistController() {
		return playlistController;
	}

	/**
	 * Gets the recorder.
	 * 
	 * @return the recorder
	 */
	public StrokeRecorder getRecorder() {
		return recorder;
	}

	/**
	 * Gets the track.
	 * 
	 * @return the track
	 */
	public Track getTrack() {
		return track;
	}

	/**
	 * Sets the track.
	 * 
	 * @param track the new track
	 */
	public void setTrack(Track track) {
		this.track = track;
		if (isRecording) {
			track.setStrokeSet(null);
		}

		recorder.setTrack(track);
		player.setTrack(track.getMp3());
		scoreController.setTrack(track);
	}

	/**
	 * Gets the score controller.
	 * 
	 * @return the score controller
	 */
	public ScoreController getScoreController() {
		return scoreController;
	}
}
