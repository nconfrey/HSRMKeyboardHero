package controller;

import gui.PlayerController;
import model.Stroke;
import controller.player.MP3Player;
import controller.player.MP3PlayerListener;
import controller.recorder.StrokeRecorder;
import controller.recorder.StrokeRecorderListener;

public class ScoreController implements MP3PlayerListener, StrokeRecorderListener {
	
	private MP3Player player;
	
	public ScoreController() {
		player = null;
	}
	
	@Override
	public void playbackDidStart(MP3Player player) {
		if (!PlayerController.getInstance().isRecording()) {
			this.player = player;
		}
	}

	@Override
	public void playbackDidStop(MP3Player player) {
		this.player = null;
	}

	@Override
	public void playbackPlaying(MP3Player player, int frame) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redcorderDidOpenStroke(StrokeRecorder recorder, Stroke stroke) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redcorderDidCloseStroke(StrokeRecorder recorder, Stroke stroke) {
		// TODO Auto-generated method stub
		
	}

}
