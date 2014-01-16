package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import gui.PlayerController;
import model.Score;
import model.Stroke;
import model.StrokeKey;
import model.StrokeSet;
import controller.player.MP3Player;
import controller.player.MP3PlayerListener;
import controller.recorder.StrokeRecorder;
import controller.recorder.StrokeRecorderListener;

public class ScoreController implements MP3PlayerListener, StrokeRecorderListener {
	
	private MP3Player player;
	private boolean isRecording;
	private Map<StrokeKey, Stroke> currentPlayedStrokes;
	private Score score;
	
	public ScoreController() {
		player = null;
		isRecording = false;
		currentPlayedStrokes = new HashMap<StrokeKey, Stroke>();
		score = new Score();
	}
	
	@Override
	public void playbackDidStart(MP3Player player) {
		if (!PlayerController.getInstance().isRecording()) {
			this.player = player;
		}
	}

	public void setRecording(boolean isRecording) {
		this.isRecording = isRecording;
	}

	@Override
	public void playbackDidStop(MP3Player player) {
		this.player = null;
	}

	@Override
	public void playbackPlaying(MP3Player player, int frame) {
		// TODO Auto-generated method stub
		for (StrokeKey key : currentPlayedStrokes.keySet()) {
			
			StrokeSet strokeSet = PlayerController.getInstance().getTrack().getStrokeSet();
			Stroke recordedStroke = currentPlayedStrokes.get(key);
			Stroke playedStroke = strokeSet.getStrokeForStroke(recordedStroke);
			if(playedStroke != null && playedStroke.getEndFrame() >= frame) {
				score.raise();
				System.out.println(score);
			}
			
		}
	}

	public Score getScore() {
		return score;
	}

	@Override
	public void redcorderDidOpenStroke(StrokeRecorder recorder, Stroke stroke) {
		// just score in playing mode
		if(isRecording) return;
		
		// already added
		if(currentPlayedStrokes.containsKey(stroke.getKey())) return;
		
		StrokeSet strokeSet = PlayerController.getInstance().getTrack().getStrokeSet();
		if(strokeSet != null && strokeSet.containsStroke(stroke)) {
			currentPlayedStrokes.put(stroke.getKey(), stroke);
			System.out.println("added stroke");
		}
		
	}

	@Override
	public void redcorderDidCloseStroke(StrokeRecorder recorder, Stroke stroke) {
		// just score in playing mode
		if(isRecording) return;
		
		if(currentPlayedStrokes.containsKey(stroke.getKey())) {
			currentPlayedStrokes.remove(stroke.getKey());
			System.out.println("finished");
		}
		
	}

}
