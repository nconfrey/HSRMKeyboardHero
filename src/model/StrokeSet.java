package model;

import java.util.HashMap;
import java.util.Map;
import model.StrokeKey;

public class StrokeSet {
	
	private Map<Integer, Stroke> strokes;
	
	public StrokeSet() {
		this.strokes = new HashMap<>();
	}
	
	public Stroke set(int frame, StrokeKey strokeKey) {
		if(strokes.containsKey(frame)) {
			return strokes.get(frame);
		}
		
		Stroke newStroke = new Stroke(strokeKey, 0);
		strokes.put(frame, newStroke);
		return newStroke;
	}
	
}
