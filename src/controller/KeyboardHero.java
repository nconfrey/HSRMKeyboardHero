package controller;


import gui.MainFrame;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import controller.player.MP3Player;
import controller.player.Playlist;
import controller.player.Track;



public class KeyboardHero {

	public static void main(String[] args) {
		new MainFrame();
		
		MP3Player player = new MP3Player();
		Playlist defaultPlaylist = player.createPlayList("defaultPlaylist");
		
		Track track1;
		try {
			track1 = new Track(new File(KeyboardHero.class.getResource("/smoke_on_the_water.mp3").toURI()));
			defaultPlaylist.addTrack(track1);
			player.selectPlaylist(0);
			player.selectTrack(0);
			player.play();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

}
