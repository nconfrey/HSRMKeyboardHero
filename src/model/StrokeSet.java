package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class StrokeSet implements Serializable{
	
	private NavigableMap<Integer, List<Stroke>> strokes;
	private TreeSet<Highscore> highscores;
	
	public StrokeSet() {
		this.strokes = new TreeMap<>();
		this.highscores = new TreeSet<>();
	}
	
	public TreeSet<Highscore> getHighscores() {
		return highscores;
	}

	public void addHighscore(int score, String name) {
		Highscore highscore = new Highscore(score, name);
		this.highscores.add(highscore);
	}

	public NavigableMap<Integer, List<Stroke>> getStrokes() {
		return strokes;
	}
	
	public Stroke getStrokeForStroke(Stroke recordedStroke) {
		ArrayList<Stroke> frameList = getListForFrame(recordedStroke.getStartFrame());
		for (Stroke stroke : frameList) {
			if(stroke.equals(recordedStroke)) {
				return stroke;
			}
		}
		return null;
	}
	
	public boolean containsStroke(Stroke aStroke) {
		ArrayList<Stroke> frameList = getListForFrame(aStroke.getStartFrame());
		return frameList.contains(aStroke);
	}

	public Stroke set(Integer frame, StrokeKey key, Integer length) {
		Stroke stroke = new Stroke(key, frame, 0);
		return set(stroke);
	}
	
	public Stroke set(Stroke stroke) {
		ArrayList<Stroke> frameList = getListForFrame(stroke.getStartFrame());
		
		// remove old stroke with possibly other length
		if(frameList.contains(stroke)) {
			frameList.remove(stroke);
		}
		
		frameList.add(stroke);
		return stroke;
	}
	
	private ArrayList<Stroke> getListForFrame(Integer frame) {
		if(strokes.containsKey(frame)) {
			return (ArrayList<Stroke>)strokes.get(frame);
		}
		
		ArrayList<Stroke> newList = new ArrayList<Stroke>();
		this.strokes.put(frame, newList);
		return newList;
	}
	
	public List<Stroke> getListForFrameInRange(Integer fromFrame, Integer toFrame) {
		Map<Integer, List<Stroke>> subMap = strokes.subMap(fromFrame, toFrame);
		List<Stroke> strokeRangeList = new ArrayList<>();
		
		for (List<Stroke> strokeList : subMap.values()) {
			strokeRangeList.addAll(strokeList);
		}
		
		return strokeRangeList;
	}
	
	public List<Stroke> getListForFrameInRange(Integer fromFrame, Integer toFrame, StrokeKey key) {
		Map<Integer, List<Stroke>> subMap = strokes.subMap(fromFrame, toFrame);
		List<Stroke> strokeRangeList = new ArrayList<>();
		
		for (List<Stroke> strokeList : subMap.values()) {
			for(Stroke stroke : strokeList) {
				if(stroke.getKey() == key) {
					strokeRangeList.add(stroke);
				}
			}
		}
		return strokeRangeList;
	}
}
