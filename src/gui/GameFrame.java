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
import javax.swing.JPanel;

import model.StrokeKey;
import model.Track;
import view.GuitarPane;

public class GameFrame extends JFrame implements ActionListener, KeyListener {
		
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private JPanel wrapper;			// background Panel
	private JPanel contentPanel;	
	private JPanel leftContent;		// sidepanel for scores, songtitle ...
	private JPanel rightContent;	// sidepanel for scores, songtitle ...
	private JPanel gameContent;		// main game content
	private GuitarPane guitarPane;
	private JButton recordButton;
	private JButton playButton;
	private JButton backToMenu;
	
	private final Dimension screenSize;
	private final Dimension frameSize;
	
	private ArrayList<GuitarStringListener> guitarStringListener;
	
	public GameFrame(){
		
		// TODO: Move to a better position
		PlayerController.getInstance().setTrack(new Track("smoke_on_the_water_short.mp3"));
			
		// Window
		frameSize = new Dimension(800,600);
		screenSize = this.getToolkit().getScreenSize(); 
		this.setSize(frameSize);
		this.setTitle("Keyboard Hero");
		
		// center mainframe
	    this.setLocation((int) ((screenSize.getWidth() - this.getWidth()) / 2), (int) ((screenSize.getHeight() - this.getHeight()) / 2));
		
		wrapper = new JPanel();
	    
	    // ContentPanel
	    contentPanel = new JPanel();
	    contentPanel.setLayout(new BorderLayout());
	    contentPanel.add(this.buildLeftContent(), BorderLayout.WEST);
	    contentPanel.add(this.buildGameContent(), BorderLayout.CENTER);
	    contentPanel.add(this.buildRightContent(), BorderLayout.EAST);
	    wrapper.add(contentPanel);
	  
	    
	    // key related
	    this.addKeyListener(this);
	    this.setFocusable(true);
	    guitarStringListener = new ArrayList<>();
	    
		this.addGuitarStringListener(PlayerController.getInstance().getRecorder());
		this.addActionListener(this);
		
		// start displaying View
	    this.add(wrapper);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    this.setVisible(true);
	    
	}

	public void play() {
		PlayerController.getInstance().play();
	}

	public JPanel buildLeftContent(){
		
		// Panel
		leftContent = new JPanel();
	    leftContent.setBackground(Color.LIGHT_GRAY);
	    leftContent.setPreferredSize(new Dimension(frameSize.width/6,frameSize.height)); // 1/6 der Frame Size
	    
	    // Record Button
	    recordButton = new JButton("record");
	    recordButton.setActionCommand("ButtonRecordClicked");
	    leftContent.add(recordButton);
	    
	    // PlayButton
	    playButton = new JButton("play");
	    playButton.setActionCommand("ButtonPlayClicked");
	    leftContent.add(playButton);
	    
	    
	    return leftContent;
	}

	public JPanel buildRightContent(){
		backToMenu = new JButton("Menu");
		backToMenu.addActionListener(this);
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
	    guitarPane = new GuitarPane();
	    guitarPane.setPreferredSize(gameContent.getPreferredSize());
	    gameContent.add(guitarPane);
	    return gameContent;
	}
	
	// Button Actions
	public void addActionListener(ActionListener controller){
		recordButton.addActionListener(controller);	
		playButton.addActionListener(controller);
	}
	
	
	// Key Handling
	
	public void addGuitarStringListener(GuitarStringListener listener) {
        this.guitarStringListener.add(listener);
    }

    public void removeGuitarStringListener(GuitarStringListener listener) {
    	this.guitarStringListener.remove(listener);
    }
	
	public void keyPressed(KeyEvent e) {
		StrokeKey strokeKey = StrokeKey.keyForCode(e.getKeyCode());
		if(strokeKey != StrokeKey.INVALID && strokeKey != StrokeKey.ENTER) {
			for (GuitarStringListener guitarStringListener : this.guitarStringListener) {
				guitarStringListener.guitarStringPressed(strokeKey);
	        }
		} else if(strokeKey == StrokeKey.ENTER) {
			for (GuitarStringListener guitarStringListener : this.guitarStringListener) {
				guitarStringListener.guitarStrokePressed(strokeKey);
	        }
		}
    }
	
    public void keyReleased(KeyEvent e) {
    	StrokeKey strokeKey = StrokeKey.keyForCode(e.getKeyCode());
		if(strokeKey != StrokeKey.INVALID && strokeKey != StrokeKey.ENTER) {
			
			for (GuitarStringListener guitarStringListener : this.guitarStringListener) {
				guitarStringListener.guitarStringReleased(strokeKey);
	        }
		} else if(strokeKey == StrokeKey.ENTER) {
			for (GuitarStringListener guitarStringListener : this.guitarStringListener) {
				guitarStringListener.guitarStrokeReleased(strokeKey);
	        }
		}
    }
    
    public void keyTyped(KeyEvent e) {
        
    }
	
	public void actionPerformed(ActionEvent e) {
	    if ("ButtonRecordClicked".equals(e.getActionCommand())) {
	    	recordButtonClicked();
	    } else if ("ButtonPlayClicked".equals(e.getActionCommand())) {
	    	playButtonClicked();
	    } else if(e.getSource() == backToMenu){
	    	new MenuFrame();
	    	setVisible(false);
	    }
	    
	}
	
	private void recordButtonClicked() {
		PlayerController.getInstance().record();
	}
	
	private void playButtonClicked() {
		this.play();
	}
}
