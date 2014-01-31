/**
 * a StrokeSet holds multiple Strokes and a Highscore
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class StrokeSet implements Serializable {

	private static final long serialVersionUID = 7800898096075391569L;
	private NavigableMap<Integer, List<Stroke>> strokes;
	private HighscoreSet highscores;

	/**
	 * Instantiates a new stroke set.
	 */
	public StrokeSet() {
		this.strokes = new TreeMap<>();
		this.highscores = new HighscoreSet();
	}

	/**
	 * Gets the highscores.
	 * 
	 * @return the highscores
	 */
	public HighscoreSet getHighscores() {
		return highscores;
	}

	/**
	 * Adds the highscore.
	 * 
	 * @param score the score
	 * @param name the name
	 */
	public void addHighscore(int score, String name) {
		Highscore highscore = new Highscore(score, name);
		this.highscores.addHighScore(highscore);
	}

	/**
	 * Gets the strokes.
	 * 
	 * @return the strokes
	 */
	public NavigableMap<Integer, List<Stroke>> getStrokes() {
		return strokes;
	}

	/**
	 * Gets the stroke for stroke.
	 * 
	 * @param recordedStroke the recorded stroke
	 * @return the stroke for stroke
	 */
	public Stroke getStrokeForStroke(Stroke recordedStroke) {
		ArrayList<Stroke> frameList = getListForFrame(recordedStroke
				.getStartFrame());
		for (Stroke stroke : frameList) {
			if (stroke.equals(recordedStroke)) {
				return stroke;
			}
		}
		return null;
	}

	/**
	 * Checks if aStroke is part of the StrokeSet
	 * 
	 * @param aStroke the a stroke
	 * @return true, if successful
	 */
	public boolean containsStroke(Stroke aStroke) {
		ArrayList<Stroke> frameList = getListForFrame(aStroke.getStartFrame());
		return frameList.contains(aStroke);
	}

	/**
	 * Sets a Stroke to the given params
	 * 
	 * @param frame the frame
	 * @param key the key
	 * @param length the length
	 * @return the stroke
	 */
	public Stroke set(Integer frame, StrokeKey key, Integer length) {
		Stroke stroke = new Stroke(key, frame, 0);
		return set(stroke);
	}

	/**
	 * Adds a Stroke to the StrokeSet
	 * 
	 * @param stroke the stroke
	 * @return the stroke
	 */
	public Stroke set(Stroke stroke) {
		ArrayList<Stroke> frameList = getListForFrame(stroke.getStartFrame());

		// remove old stroke with possibly other length
		if (frameList.contains(stroke)) {
			frameList.remove(stroke);
		}

		frameList.add(stroke);
		return stroke;
	}

	/**
	 * Gets the list for frame.
	 * 
	 * @param frame the frame
	 * @return the list for frame
	 */
	private ArrayList<Stroke> getListForFrame(Integer frame) {
		if (strokes.containsKey(frame)) {
			return (ArrayList<Stroke>) strokes.get(frame);
		}

		ArrayList<Stroke> newList = new ArrayList<Stroke>();
		this.strokes.put(frame, newList);
		return newList;
	}

	/**
	 * Gets the list for frame in range.
	 * 
	 * @param fromFrame the from frame
	 * @param toFrame the to frame
	 * @return the list for frame in range
	 */
	public List<Stroke> getListForFrameInRange(Integer fromFrame,
			Integer toFrame) {
		Map<Integer, List<Stroke>> subMap = strokes.subMap(fromFrame, toFrame);
		List<Stroke> strokeRangeList = new ArrayList<>();

		for (List<Stroke> strokeList : subMap.values()) {
			strokeRangeList.addAll(strokeList);
		}

		return strokeRangeList;
	}

	/**
	 * Gets the list for frame in range.
	 * 
	 * @param fromFrame the from frame
	 * @param toFrame the to frame
	 * @param key the key
	 * @return the list for frame in range
	 */
	public List<Stroke> getListForFrameInRange(Integer fromFrame,
			Integer toFrame, StrokeKey key) {
		Map<Integer, List<Stroke>> subMap = strokes.subMap(fromFrame, toFrame);
		List<Stroke> strokeRangeList = new ArrayList<>();

		for (List<Stroke> strokeList : subMap.values()) {
			for (Stroke stroke : strokeList) {
				if (stroke.getKey() == key) {
					strokeRangeList.add(stroke);
				}
			}
		}
		return strokeRangeList;
	}
}
