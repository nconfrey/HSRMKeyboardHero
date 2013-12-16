package model;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import model.StrokeKey;

public class StrokeSet {
	
	private NavigableMap<Integer, List<Stroke>> strokes;
	
	public StrokeSet() {
		this.strokes = new TreeMap<>();
	}

	
	public NavigableMap<Integer, List<Stroke>> getStrokes() {
		return strokes;
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
}
