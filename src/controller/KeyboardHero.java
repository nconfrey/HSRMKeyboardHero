package controller;


import gui.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import model.Score;
import model.Track;
import controller.player.MP3Player;
import controller.player.Playlist;
import controller.player.MP3PlayerTrack;
import controller.recorder.StrokeRecorder;
import controller.recorder.StrokeRecorderListener;



public class KeyboardHero implements ActionListener, StrokeRecorderListener {
	
	private MainFrame mainFrame;
	private StrokeRecorder recorder;

	public static void main(String[] args) {
		KeyboardHero keyboardHero = new KeyboardHero();
		keyboardHero.init();
	}
	
	private void init() {
		mainFrame = new MainFrame();
		Track currentTrack = new Track("smoke_on_the_water_short.mp3");
		recorder = new StrokeRecorder(currentTrack);
		recorder.addStrokeRecorderListener(this);
		
		mainFrame.setCurrentTrack(currentTrack);
		mainFrame.addGuitarStringListener(recorder);
		mainFrame.addActionListener(this);
	}
	
	
	public void actionPerformed(ActionEvent e) {
	    if ("ButtonRecordClicked".equals(e.getActionCommand())) {
	    	recordButtonClicked();
	    } else if ("ButtonPlayClicked".equals(e.getActionCommand())) {
	    	playButtonClicked();
	    }
	}
	
	private void recordButtonClicked() {
		recorder.record();
	}
	
	private void playButtonClicked() {
		mainFrame.play();
	}

	
	@Override
	public void recorderDidStartRecording(StrokeRecorder recorder, Track track) {
		mainFrame.setLayoutToRecordingMode(true);
	}

	@Override
	public void recorderDidStopRecording(StrokeRecorder recorder, Track track) {
		mainFrame.setLayoutToRecordingMode(false);
	}

}
