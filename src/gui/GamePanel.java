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
import view.GuitarBackgroundPane;
import view.GuitarPane;

public class GamePanel extends JPanel implements ActionListener {
		
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private GuitarPane guitarPane;
	private JPanel contentPanel;	
	private JPanel leftContent;		// sidepanel for scores, songtitle ...
	private JPanel rightContent;	// sidepanel for scores, songtitle ...
	private JPanel gameContent;		// main game content
	private JButton recordButton;
	private JButton playButton;
	private JButton savePlButton;
	private JButton backToMenu;
	private JPanel scoreContent;
	private JLabel scoreLabel;
	
	Playlist samplePlaylist;
	
	private final Dimension frameSize;
	
	public GamePanel(Dimension frameSize){
			
		this.frameSize = frameSize;
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
	    contentPanel = new JPanel();
	    contentPanel.setLayout(new BorderLayout());
	    contentPanel.add(this.buildLeftContent(), BorderLayout.WEST);
	    contentPanel.add(this.buildGameContent(), BorderLayout.CENTER);
	    contentPanel.add(this.buildRightContent(), BorderLayout.EAST);
	    this.add(contentPanel);
	    
		this.addActionListener(this);
	}
    
	public void play() {
		PlayerController.getInstance().play();
	}

	public JPanel buildLeftContent(){
		
		// Panel
		leftContent = new JPanel(new BorderLayout());
	    leftContent.setBackground(Color.LIGHT_GRAY);
	    leftContent.setPreferredSize(new Dimension(frameSize.width/6,frameSize.height)); // 1/6 der Frame Size
	    
	    // Record Button
	    recordButton = new JButton("record");
	    recordButton.setActionCommand("ButtonRecordClicked");
	    leftContent.add(recordButton, BorderLayout.NORTH);
	    
	    // Play Button
	    playButton = new JButton("play");
	    playButton.setActionCommand("ButtonPlayClicked");
	    leftContent.add(playButton, BorderLayout.WEST);
	    
	    // Score
	    scoreContent = new JPanel();
	    leftContent.add(scoreContent, BorderLayout.SOUTH);
	    scoreContent.setPreferredSize(new Dimension(100, 20));
	    scoreLabel = new JLabel("Score");
	    scoreContent.add(scoreLabel);
	    
	    scoreContent.setBackground(Color.cyan);
	    
	    
	    // Save Playlist Button
	    savePlButton = new JButton("save");
	    savePlButton.setActionCommand("ButtonSaveClicked");
	    leftContent.add(savePlButton);
	    
	    
	    return leftContent;
	}

	public JPanel buildRightContent(){
		backToMenu = new JButton("Menu");
	    backToMenu.setActionCommand("ButtonMenuClicked");
		rightContent = new JPanel();
	    rightContent.setBackground(Color.LIGHT_GRAY);
	    rightContent.setPreferredSize(new Dimension(frameSize.width/6,frameSize.height)); // 1/6 der Frame Size
	    rightContent.add(backToMenu);
	    return rightContent;
	}
	
	public JPanel buildGameContent(){
		gameContent = new JPanel();
	    gameContent.setBackground(Color.WHITE);
	    gameContent.setPreferredSize(new Dimension(frameSize.width/6*4,frameSize.height)); // 4/6 der Frame Size
	    
	    GuitarBackgroundPane bgPane = new GuitarBackgroundPane();
	    bgPane.setPreferredSize(gameContent.getPreferredSize());
	    gameContent.add(bgPane);
	    
	    guitarPane = new GuitarPane();
	    guitarPane.setPreferredSize(gameContent.getPreferredSize());
	    bgPane.add(guitarPane);
	    return gameContent;
	}
	
	// Button Actions
	public void addActionListener(ActionListener controller){
		recordButton.addActionListener(controller);	
		playButton.addActionListener(controller);
		savePlButton.addActionListener(controller);
		backToMenu.addActionListener(controller);
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
