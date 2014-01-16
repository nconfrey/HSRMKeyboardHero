package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import controller.player.Playlist;
import model.PersistenceHandler;
import model.StrokeKey;
import model.Track;
import view.GuitarPane;

public class GamePanel extends JPanel implements ActionListener {
		
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private GuitarPane guitarPane;
	
	private JPanel leftContent;		// sidepanel for scores, songtitle ...
	private JPanel gameContent;		// main game content
	private ScorePanel scoreContent;
	
	
	Playlist samplePlaylist;
	
	public GamePanel(Dimension frameSize){
		setSize(frameSize);
		setFocusable(true);
			
		// TODO: Move to a better position
		samplePlaylist = PersistenceHandler.loadPlaylist();
		if(samplePlaylist == null){
			samplePlaylist = new Playlist("Sample Playlist");
			Track sampleTrack = new Track("smoke_on_the_water_short.mp3");
			samplePlaylist.addTrack(sampleTrack);
		}
		PlayerController.getInstance().setTrack(samplePlaylist.getTrack(0));
		
		 // ContentPanel
	    this.setLayout(new BorderLayout());
	    this.add(this.buildLeftContent(), BorderLayout.WEST);
	    this.add(this.buildGameContent(), BorderLayout.EAST);
	   
	    
		
	}
    
	public void play() {
		PlayerController.getInstance().play();
	}

	public JPanel buildLeftContent(){
		
		// Panel
		leftContent = new JPanel(new BorderLayout());
	    leftContent.setBackground(Color.CYAN);
	    leftContent.setPreferredSize(new Dimension(getSize().width/4,getSize().height)); // 1/6 der Frame Size

	    return leftContent;
	}

	
	public JPanel buildGameContent(){
		gameContent = new JPanel(null);
	    gameContent.setBackground(Color.BLUE);
	    gameContent.setPreferredSize(getSize());
	    guitarPane = new GuitarPane();
	    guitarPane.setPreferredSize(gameContent.getPreferredSize());
	    gameContent.add(guitarPane);
	    return gameContent;
	}
	
	
	public void actionPerformed(ActionEvent e) {
	    if ("ButtonRecordClicked".equals(e.getActionCommand())) {
	    	recordButtonClicked();
	    } else if ("ButtonPlayClicked".equals(e.getActionCommand())) {
	    	playButtonClicked();
	    } else if ("ButtonSaveClicked".equals(e.getActionCommand())){
	    	PersistenceHandler.savePlaylist(samplePlaylist);
	    } else if ("ButtonMenuClicked".equals(e.getActionCommand())){
	    	PlayerController.getInstance().getPlayer().stop();
	    }
	}
	
	private void recordButtonClicked() {
		PlayerController.getInstance().record();
	}
	
	private void playButtonClicked() {
		this.play();
	}
}
