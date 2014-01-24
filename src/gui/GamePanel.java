package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


import view.GuitarBackgroundPane;
import controller.player.Playlist;

public class GamePanel extends GHPanel {
	
	private JPanel leftContent;		// sidepanel for scores, songtitle ...
	
	private BufferedImage image;
	
	Playlist samplePlaylist;
	
	public GamePanel(){
		setFocusable(true);
		
		 // ContentPanel
	    this.setLayout(new BorderLayout());
	    this.add(this.buildLeftContent(), BorderLayout.WEST);
	    this.add(new GuitarBackgroundPane(), BorderLayout.CENTER);
	   
	    try {
			image = ImageIO.read(getClass().getResourceAsStream("/background.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyEventDispatcher() {
			
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	    			PlayerController.getInstance().stop();
	    			getNavigationController().popToRootPanel();
	    			return true;
	    		}
				return false;
			}
		});
	    
	    PlayerController.getInstance().play();
	}

	public JPanel buildLeftContent(){
		
		// Panel
		leftContent = new JPanel(new BorderLayout());
		ScorePanel scorePanel = new ScorePanel();
		leftContent.add(scorePanel, BorderLayout.SOUTH);
		scorePanel.setBackground(Color.PINK);
		
		
	    return leftContent;
	}
	
	@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			if (this.image != null) {
				Graphics2D g2 = (Graphics2D)g;
				g2.drawImage(this.image, null, 0, 0);
			}
		}
}
