package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;

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
	private int frame = 0;
	private boolean isRecording = false;
	
	public StrokeRecorder(Track track) {
		this.player = new MP3Player();
		player.addListener(this);
		
		this.strokes = new HashMap<StrokeKey, Stroke>();
		setTrack(track);
	}

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

	public void record() {
		player.play();
	}
	
	// GuitarStringListener
	@Override
	public void guitarStringPressed(StrokeKey strokeKey) {
		if(!isRecording) return;
		
		if(!strokes.containsKey(strokeKey)) {
			strokes.put(strokeKey, new Stroke(strokeKey, frame, 0));
		}
	}
	
	@Override
    public void GuitarStringReleased(StrokeKey strokeKey) {
		if(!isRecording) return;
		
		if(strokes.containsKey(strokeKey)) {
			Stroke aStroke = strokes.remove(strokeKey);
			int length = frame - aStroke.getStartFrame();
			if(length > 0) {
				aStroke.setLength(length);
				StrokeSet set = track.getStrokeSet();
				set.set(aStroke);
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
