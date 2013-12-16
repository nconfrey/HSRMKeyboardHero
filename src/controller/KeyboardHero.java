package controller;


import gui.MainFrame;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import model.Track;
import controller.player.MP3Player;
import controller.player.Playlist;
import controller.player.MP3PlayerTrack;



public class KeyboardHero {

	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		
		
		
		// Stroke Recording
		File mp3File = null;
		try {
			mp3File = new File(KeyboardHero.class.getResource("/smoke_on_the_water.mp3").toURI());
		} catch (Exception e) {
			
		}
		
		Track track = new Track(new MP3PlayerTrack(mp3File));
		StrokeRecorder recorder = new StrokeRecorder(track);
		mainFrame.addGuitarStringListener(recorder);
		recorder.record();
	}

}
