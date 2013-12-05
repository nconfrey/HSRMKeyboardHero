package model;

import java.util.HashMap;
import java.util.Map;
import model.Stroke;

public class StrokeSet {
	
	private Map<Integer, Byte> strokes;
	
	public StrokeSet() {
		this.strokes = new HashMap<>();
	}
	
	public boolean isSet(int frame, Stroke stroke) {
		if (!strokes.containsKey(frame)){
			return false;
		}
		
		return (strokes.get(frame) & stroke.getValue()) != 0;
	}
	
	public void set(int frame, Stroke stroke) {
		byte strokeSet = 0;
		if(strokes.containsKey(frame)) {
			strokeSet = strokes.get(frame);
		}
		
		strokes.put(frame, (byte)(strokeSet | stroke.getValue()));
	}
	
}
