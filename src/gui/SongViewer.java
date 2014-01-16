package gui;

import java.util.Vector;

import javax.swing.JList;

import model.PersistenceHandler;
import model.Track;
import controller.player.Playlist;

public class SongViewer{
	
	Playlist playlist;
	JList songlist;
	
	public SongViewer(String fillmode){
		playlist = PersistenceHandler.loadPlaylist();
		if(playlist == null){
			playlist = new Playlist("KeyboardHero Playlist");
			Track sampleTrack = new Track("smoke_on_the_water_short.mp3");
			playlist.addTrack(sampleTrack);
		}
		PlayerController.getInstance().setTrack(playlist.getTrack(0));
		
		songlist = new JList(playlist);
	}
	
	public JList getSonglist(){
		return songlist;
	}
	
}
