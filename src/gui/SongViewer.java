package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.PersistenceHandler;
import model.Track;
import controller.player.Playlist;

public class SongViewer extends GHPanel implements MouseListener{
	
	private Playlist playlist;
	private JList songlist;
	private JScrollPane sp;	
	private JButton backToMenu;
	
	public SongViewer(String fillmode){
		
		this.setLayout(new BorderLayout());
		
		playlist = PersistenceHandler.loadPlaylist();
		if(playlist == null){
			playlist = new Playlist("KeyboardHero Playlist");
			Track sampleTrack = new Track("smoke_on_the_water_short.mp3");
			playlist.addTrack(sampleTrack);
		}
		PlayerController.getInstance().setTrack(playlist.getTrack(0));
		
		songlist = new JList(playlist);
		sp = new JScrollPane(songlist);
		
		songlist.addMouseListener(this);
		backToMenu = new JButton("Back to menu");
		backToMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getNavigationController().popPanel();
				
			}
		});
		
		this.add(songlist, BorderLayout.NORTH);
		this.add(backToMenu, BorderLayout.SOUTH);

	}
	
	public JList getSonglist(){
		return songlist;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2){
	    	int index = songlist.getSelectedIndex();
	    	GamePanel gameFrame = new GamePanel(new Dimension(800,600/*, playlist.getTrack(index)*/));
			this.getNavigationController().pushPanel(gameFrame);
	    }
	}

	@Override
	public void mousePressed(MouseEvent e) {	
	}

	@Override
	public void mouseReleased(MouseEvent e) {	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
}
