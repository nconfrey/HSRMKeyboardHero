/**
 * Records Strokes to a Track
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package controller.recorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.Stroke;
import model.StrokeKey;
import model.StrokeSet;
import model.Track;
import controller.player.MP3Player;
import controller.player.MP3PlayerListener;

public class StrokeRecorder implements GuitarStringListener, MP3PlayerListener {

	private final static int RECORDING_TOLERANCE = 100;

	private Track track;
	private Map<StrokeKey, Stroke> strokes;
	private List<StrokeKey> pressedKeys;
	private List<StrokeRecorderListener> strokeRecorderListener;

	private int frame = 0;
	private boolean isRecording = false;
	private boolean isRecordingMode = false;

	/**
	 * Checks if is recording mode.
	 * 
	 * @return true, if is recording mode
	 */
	public boolean isRecordingMode() {
		return isRecordingMode;
	}

	/**
	 * Sets the recording mode.
	 * 
	 * @param isRecordingMode the new recording mode
	 */
	public void setRecordingMode(boolean isRecordingMode) {
		this.isRecordingMode = isRecordingMode;
	}

	/**
	 * Instantiates a new stroke recorder.
	 * 
	 * @param player the player
	 */
	public StrokeRecorder(MP3Player player) {
		strokeRecorderListener = new LinkedList<>();

		player.addPlayerListener(this);
	}

	// Getters, Setters

	/**
	 * Sets the track.
	 * 
	 * @param track the new track
	 */
	public void setTrack(Track track) {
		this.track = track;
	}

	/**
	 * Adds the stroke recorder listener.
	 * 
	 * @param listener the listener
	 */
	public void addStrokeRecorderListener(StrokeRecorderListener listener) {
		this.strokeRecorderListener.add(listener);
	}

	/**
	 * Removes the stroke recorder listener.
	 * 
	 * @param listener the listener
	 */
	public void removeStrokeRecorderListener(StrokeRecorderListener listener) {
		this.strokeRecorderListener.remove(listener);
	}

	// GuitarStringListener
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.recorder.GuitarStringListener#guitarStringPressed(model.StrokeKey
	 * )
	 */
	@Override
	public void guitarStringPressed(StrokeKey strokeKey) {
		if (!isRecording)
			return;

		// add key to currently pressed keys
		if (strokeKey.isGuitarString() && !pressedKeys.contains(strokeKey)) {
			pressedKeys.add(strokeKey);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.recorder.GuitarStringListener#guitarStringReleased(model.StrokeKey
	 * )
	 */
	@Override
	public void guitarStringReleased(StrokeKey strokeKey) {
		if (!isRecording)
			return;

		// remove key from pressed keys
		if (strokeKey.isGuitarString() && pressedKeys.contains(strokeKey)) {
			pressedKeys.remove(strokeKey);
		}

		finishKey(strokeKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.recorder.GuitarStringListener#guitarStrokePressed(model.StrokeKey
	 * )
	 */
	@Override
	public void guitarStrokePressed(StrokeKey strokeKey) {
		if (!isRecording)
			return;
		for (StrokeKey aKey : pressedKeys) {
			if (strokes.containsKey(aKey)) {
				finishKey(aKey);
			}

			int startFrame = frame;

			if (!isRecordingMode) {
				StrokeSet gamingStrokeSet = this.track.getStrokeSet();
				List<Stroke> strokeList = gamingStrokeSet
						.getListForFrameInRange(frame - RECORDING_TOLERANCE,
								frame + RECORDING_TOLERANCE, aKey);
				if (strokeList.size() > 0) {
					startFrame = strokeList.get(0).getStartFrame();
				}
			}

			Stroke newStroke = new Stroke(aKey, startFrame, 0);
			strokes.put(aKey, newStroke);

			// Notify listener
			for (StrokeRecorderListener listener : this.strokeRecorderListener) {
				listener.redcorderDidOpenStroke(this, newStroke);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.recorder.GuitarStringListener#guitarStrokeReleased(model.StrokeKey
	 * )
	 */
	@Override
	public void guitarStrokeReleased(StrokeKey strokeKey) {

	}

	/**
	 * Finish key.
	 * 
	 * @param strokeKey the stroke key
	 */
	private void finishKey(StrokeKey strokeKey) {
		if (strokes.containsKey(strokeKey)) {
			Stroke aStroke = strokes.get(strokeKey);
			int length = frame - aStroke.getStartFrame();

			if (length > 0) {
				aStroke.setLength(length);

				if (isRecordingMode) {
					track.getStrokeSet().set(aStroke);
				}
				strokes.remove(strokeKey);

				System.out.println("played " + aStroke.getKey() + " for "
						+ length + " frames");
			} else {
				strokes.remove(strokeKey);
			}

			// Notify listener
			for (StrokeRecorderListener listener : this.strokeRecorderListener) {
				listener.redcorderDidCloseStroke(this, aStroke);
			}
		}
	}

	// MP3PlayerListener
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.player.MP3PlayerListener#playbackDidStart(controller.player
	 * .MP3Player)
	 */
	@Override
	public void playbackDidStart(MP3Player player) {
		this.strokes = new HashMap<StrokeKey, Stroke>();
		this.pressedKeys = new ArrayList<>();

		if (isRecordingMode) {
			track.setStrokeSet(new StrokeSet());
		}

		frame = 0;
		isRecording = true;
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
		frame = 0;
		isRecording = false;

		/*
		 * debugging if (this.track.getStrokeSet() != null) { // recorded Keyset
		 * debug output for (Map.Entry<Integer, List<Stroke>> entry :
		 * this.track.getStrokeSet().getStrokes().entrySet()) { String strokes =
		 * ""; for(Stroke aStroke : entry.getValue()) { strokes += " " +
		 * aStroke.getKey() + " (l: " + aStroke.getLength() + "),"; }
		 * System.out.println("on Frame: " + entry.getKey() + ". Keys: " +
		 * strokes); } }
		 */
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
		this.frame = frame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.player.MP3PlayerListener#playbackDidFail()
	 */
	@Override
	public void playbackDidFail(final MP3Player player) {
	}
}
