package controller;


import gui.BaseFrame;
import gui.GamePanel;
import gui.MainFrame;
import gui.MenuPanel;
import gui.NavigationController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import model.PersistenceHandler;
import model.Score;
import model.Track;
import controller.player.MP3Player;
import controller.player.Playlist;
import controller.player.MP3PlayerTrack;
import controller.recorder.StrokeRecorder;
import controller.recorder.StrokeRecorderListener;



public class KeyboardHero {
		
	public static void main(String[] args) {
		BaseFrame baseFrame = new BaseFrame();
		NavigationController navCon = new NavigationController(baseFrame);
		MenuPanel menu = new MenuPanel();
		navCon.pushPanel(menu);
	}
}
