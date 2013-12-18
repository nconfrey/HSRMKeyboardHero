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
	private MP3Player player;
	
	private Map<StrokeKey, Stroke> strokes;
	private List<StrokeKey> pressedKeys;
	
	private int frame = 0;
	private boolean isRecording = false;
	
	private ArrayList<StrokeRecorderListener> strokeRecorderListener;
	
	public StrokeRecorder(Track track) {
		this.player = new MP3Player();
		player.addListener(this);
		
		this.strokes = new HashMap<StrokeKey, Stroke>();
		this.pressedKeys = new ArrayList<>();
		this.strokeRecorderListener = new ArrayList<>();
		
		setTrack(track);
	}
	
	// Getters, Setters

	public void setTrack(Track track) {
		this.track = track;
		
		// set track to player
		Playlist currentPlaylist = player.getCurrentPlaylist();
		if(currentPlaylist == null) {
			currentPlaylist = player.createPlayList("defaultPlaylist");
		}
		currentPlaylist.addTrack(track.getMp3());
		player.selectPlaylist(0);
		player.selectTrack(0);
		
	}
	
	public void addStrokeRecorderListener(StrokeRecorderListener listener) {
        this.strokeRecorderListener.add(listener);
    }

    public void removeStrokeRecorderListener(StrokeRecorderListener listener) {
    	this.strokeRecorderListener.remove(listener);
    }
    
    // public methods

	public void record() {
		if(track.getStrokeSet() == null) {
			track.setStrokeSet(new StrokeSet());
		}
		
		player.play();
		
		for (StrokeRecorderListener listener : this.strokeRecorderListener) {
			listener.recorderDidStartRecording(this, track);
        }
		
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
		
		for(StrokeKey aKey : pressedKeys) {
			if(strokes.containsKey(aKey)) {
				finishKey(aKey);
			}
			strokes.put(aKey, new Stroke(aKey, frame, 0));
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
				
				this.track.getStrokeSet().set(aStroke);
				strokes.remove(strokeKey);
				
				System.out.println("played " + aStroke.getKey() + " for " + length + " frames");
			} else {
				strokes.remove(strokeKey);
			}
		}
	}

	
	// MP3PlayerListener
	@Override
	public void playbackDidStart(MP3Player player) {
		frame = 0;
		isRecording = true;
	}

	@Override
	public void playbackDidStop(MP3Player player) {
		frame = 0;
		isRecording = false;
		
		for (StrokeRecorderListener listener : this.strokeRecorderListener) {
			listener.recorderDidStopRecording(this, this.track);
        }
		
		// recorded Keyset debug output
		for (Map.Entry<Integer, List<Stroke>> entry : this.track.getStrokeSet().getStrokes().entrySet()) {
			String strokes = "";
			for(Stroke aStroke : entry.getValue()) {
				strokes += " " + aStroke.getKey() + " (l: " + aStroke.getLength() + "),";
			}
		     System.out.println("on Frame: " + entry.getKey() + ". Keys: " + strokes);
		}
		
	}

	@Override
	public void playbackPlaying(MP3Player player, int frame) {
		this.frame = frame;
	}
	
}
