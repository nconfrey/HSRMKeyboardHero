/**
 * Handles Scores of a game
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.Score;
import model.Stroke;
import model.StrokeKey;
import model.StrokeSet;
import model.Track;
import controller.player.MP3Player;
import controller.player.MP3PlayerListener;
import controller.recorder.StrokeRecorder;
import controller.recorder.StrokeRecorderListener;

// TODO: Auto-generated Javadoc
/**
 * The Class ScoreController.
 * 
 * @author privat
 */
public class ScoreController implements MP3PlayerListener,
		StrokeRecorderListener {

	private boolean isRecording;
	private Map<StrokeKey, Stroke> currentPlayedStrokes;
	private Score score;
	private List<ScoreListener> listeners;
	private Track track;
	private MP3Player player;

	/**
	 * Instantiates a new score controller.
	 * 
	 * @param player the player
	 */
	public ScoreController(MP3Player player) {
		this.player = player;
		isRecording = false;
		currentPlayedStrokes = new HashMap<StrokeKey, Stroke>();
		listeners = new LinkedList<>();
		score = new Score();
	}

	/**
	 * Reset score.
	 */
	public void resetScore() {
		score.reset();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.player.MP3PlayerListener#playbackDidStart(controller.player
	 * .MP3Player)
	 */
	@Override
	public void playbackDidStart(MP3Player player) {
	}

	/**
	 * Sets the recording.
	 * 
	 * @param isRecording the new recording
	 */
	public void setRecording(boolean isRecording) {
		this.isRecording = isRecording;
	}

	/**
	 * Sets the track.
	 * 
	 * @param track the new track
	 */
	public void setTrack(Track track) {
		this.track = track;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.player.MP3PlayerListener#playbackDidStop(controller.player
	 * .MP3Player)
	 */
	@Override
	public void playbackDidStop(MP3Player player) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.player.MP3PlayerListener#playbackDidFail()
	 */
	@Override
	public void playbackDidFail(final MP3Player player) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.player.MP3PlayerListener#playbackPlaying(controller.player
	 * .MP3Player, int)
	 */
	@Override
	public void playbackPlaying(MP3Player player, int frame) {
		// TODO Auto-generated method stub
		StrokeSet strokeSet = track.getStrokeSet();

		List<StrokeKey> toRemove = new ArrayList<>();
		for (StrokeKey key : currentPlayedStrokes.keySet()) {
			Stroke recordedStroke = currentPlayedStrokes.get(key);
			Stroke playedStroke = strokeSet.getStrokeForStroke(recordedStroke);
			if (playedStroke != null && playedStroke.getEndFrame() >= frame) {
				score.raise();
			} else {
				if (playedStroke != null) {
					toRemove.add(key);
				}
			}
		}

		for (StrokeKey key : toRemove) {
			currentPlayedStrokes.remove(key);
			fireScoringDidEnd(key);
		}
	}

	/**
	 * Gets the score.
	 * 
	 * @return the score
	 */
	public Score getScore() {
		return score;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.recorder.StrokeRecorderListener#redcorderDidOpenStroke(controller
	 * .recorder.StrokeRecorder, model.Stroke)
	 */
	@Override
	public void redcorderDidOpenStroke(StrokeRecorder recorder, Stroke stroke) {
		// just score in playing mode
		if (isRecording)
			return;

		// already added
		if (currentPlayedStrokes.containsKey(stroke.getKey()))
			return;

		StrokeSet strokeSet = track.getStrokeSet();

		if (strokeSet != null && strokeSet.containsStroke(stroke)) {
			currentPlayedStrokes.put(stroke.getKey(), stroke);
			score.increaseCombo();
			fireScoringDidStart(stroke.getKey());
		} else if (strokeSet != null) {
			score.comboReset();
			player.volumeControl();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.recorder.StrokeRecorderListener#redcorderDidCloseStroke(controller
	 * .recorder.StrokeRecorder, model.Stroke)
	 */
	@Override
	public void redcorderDidCloseStroke(StrokeRecorder recorder, Stroke stroke) {
		// just score in playing mode
		if (isRecording)
			return;

		if (currentPlayedStrokes.containsKey(stroke.getKey())) {
			currentPlayedStrokes.remove(stroke.getKey());
			fireScoringDidEnd(stroke.getKey());
		}

	}

	/**
	 * Adds the listener.
	 * 
	 * @param listener the listener
	 */
	public void addListener(ScoreListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Removes the listener.
	 * 
	 * @param listener the listener
	 */
	public void removeListener(ScoreListener listener) {
		this.listeners.remove(listener);
	}

	/**
	 * Fire scoring did start.
	 * 
	 * @param key the key
	 */
	private void fireScoringDidStart(StrokeKey key) {
		for (ScoreListener listener : listeners) {
			listener.scoringDidStart(key);
		}
	}

	/**
	 * Fire scoring did end.
	 * 
	 * @param key the key
	 */
	private void fireScoringDidEnd(StrokeKey key) {
		for (ScoreListener listener : listeners) {
			listener.scoringDidEnd(key);
		}
	}
}
