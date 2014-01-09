package controller.recorder;

import gui.GuitarStringListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;

import model.Stroke;
import model.StrokeKey;
import model.StrokeSet;
import model.Track;
import controller.player.MP3Player;
import controller.player.MP3PlayerListener;
import controller.player.Playlist;

public class StrokeRecorder implements GuitarStringListener, MP3PlayerListener {
	
	private Track track;
	private Map<StrokeKey, Stroke> strokes;
	private List<StrokeKey> pressedKeys;
	
	private int frame = 0;
	private boolean isRecording = false;
	
	private ArrayList<StrokeRecorderListener> strokeRecorderListener;
	
	public StrokeRecorder(MP3Player player) {
		strokeRecorderListener = new ArrayList<>();
		
		player.addListener(this);
	}
	
	// Getters, Setters

	public void setTrack(Track track) {
		this.track = track;
	}
	
	public void addStrokeRecorderListener(StrokeRecorderListener listener) {
        this.strokeRecorderListener.add(listener);
    }

    public void removeStrokeRecorderListener(StrokeRecorderListener listener) {
    	this.strokeRecorderListener.remove(listener);
    }
	
	// GuitarStringListener
	@Override
	public void guitarStringPressed(StrokeKey strokeKey) {
		if(!isRecording) return;
		
		// add key to currently pressed keys
		if(strokeKey.isGuitarString() && !pressedKeys.contains(strokeKey)) {
			pressedKeys.add(strokeKey);
		}

	}
	
	@Override
    public void guitarStringReleased(StrokeKey strokeKey) {
		if(!isRecording) return;
		
		// remove key from pressed keys
		if(strokeKey.isGuitarString() && pressedKeys.contains(strokeKey)) {
			pressedKeys.remove(strokeKey);
		}
		
		finishKey(strokeKey);
    }
	
	@Override
	public void guitarStrokePressed(StrokeKey strokeKey) {
		if(!isRecording) return;
		for(StrokeKey aKey : pressedKeys) {
			if(strokes.containsKey(aKey)) {
				finishKey(aKey);
			}
			
			Stroke newStroke = new Stroke(aKey, frame, 0); 
			strokes.put(aKey, newStroke);
			
			// Notify listener
			for (StrokeRecorderListener listener : this.strokeRecorderListener) {
				listener.redcorderDidOpenStroke(this, newStroke);
	        }
		}
	}
	
	@Override
    public void guitarStrokeReleased(StrokeKey strokeKey) {
    	
    }
	
	private void finishKey(StrokeKey strokeKey) {
		if(strokes.containsKey(strokeKey)) {
			Stroke aStroke = strokes.get(strokeKey);
			int length = frame - aStroke.getStartFrame();
			
			if(length > 0) {
				aStroke.setLength(length);
				
				if (track != null) {
					track.getStrokeSet().set(aStroke);
				}
				strokes.remove(strokeKey);
				
				System.out.println("played " + aStroke.getKey() + " for " + length + " frames");
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
	@Override
	public void playbackDidStart(MP3Player player) {
		this.strokes = new HashMap<StrokeKey, Stroke>();
		this.pressedKeys = new ArrayList<>();
		
		if(track != null && track.getStrokeSet() == null) {
			track.setStrokeSet(new StrokeSet());
		}
		
		frame = 0;
		isRecording = true;
	}

	@Override
	public void playbackDidStop(MP3Player player) {
		frame = 0;
		isRecording = false;
		
		if (this.track.getStrokeSet() != null) {
			// recorded Keyset debug output
			for (Map.Entry<Integer, List<Stroke>> entry : this.track.getStrokeSet().getStrokes().entrySet()) {
				String strokes = "";
				for(Stroke aStroke : entry.getValue()) {
					strokes += " " + aStroke.getKey() + " (l: " + aStroke.getLength() + "),";
				}
			     System.out.println("on Frame: " + entry.getKey() + ". Keys: " + strokes);
			}
		}
	}

	@Override
	public void playbackPlaying(MP3Player player, int frame) {
		this.frame = frame;
	}
	
}
