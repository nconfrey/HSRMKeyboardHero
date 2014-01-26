package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


import view.GuitarBackgroundPane;
import controller.player.AlbumLoader;
import controller.player.Playlist;
import model.Track;

public class GamePanel extends GHPanel {
	
	private JPanel leftContent;		// sidepanel for scores, songtitle ...
	private BufferedImage coverImage;
	
	public GamePanel(){
		setFocusable(true);
		
		 // ContentPanel
	    this.setLayout(new BorderLayout());
	    this.add(this.buildLeftContent(), BorderLayout.WEST);
	    this.add(new GuitarBackgroundPane(), BorderLayout.CENTER);
	   
	    loadBackgroundCover();
		
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
		leftContent.setOpaque(false);
		JPanel scorePanel = new JPanel();
		leftContent.add(scorePanel, BorderLayout.SOUTH);
		scorePanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 30, 0));
		ScorePanel secondScorePanel = new ScorePanel();
		scorePanel.add(secondScorePanel);
		secondScorePanel.setBackground(Color.BLACK);
		scorePanel.setOpaque(false);
		
		
		
	    return leftContent;
	}
	
	private void loadBackgroundCover() {
		
		try {
			coverImage = ImageIO.read(getClass().getResourceAsStream("/background.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		final Track currentTrack = PlayerController.getInstance().getTrack();
		new Thread() {
		    public void run() {
		    	final BufferedImage bandImage = AlbumLoader.loadCover(currentTrack);
		    	if(bandImage != null) {
		    		coverImage = bandImage;
		    		SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							repaint();
						}
					});
			    }
		    }
		}.start();
	}
	
	@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			if(this.coverImage == null) return;

		    double scaleFactor = Math.max(1d, getScaleFactorToFill(new Dimension(coverImage.getWidth(), coverImage.getHeight()), getSize()));

		    int scaleWidth = (int) Math.round(coverImage.getWidth() * scaleFactor);
		    int scaleHeight = (int) Math.round(coverImage.getHeight() * scaleFactor);

		    Image scaled = coverImage.getScaledInstance(scaleWidth, scaleHeight, Image.SCALE_FAST);

		    int width = getWidth() - 1;
		    int height = getHeight() - 1;

		    int x = (width - scaled.getWidth(this)) / 2;
		    int y = (height - scaled.getHeight(this)) / 2;

		    g.drawImage(scaled, x, y, this);
		}
	
	private double getScaleFactor(int iMasterSize, int iTargetSize) {

	    double dScale = 1;
	    if (iMasterSize > iTargetSize) {
	        dScale = (double) iTargetSize / (double) iMasterSize;
	    } else {
	        dScale = (double) iTargetSize / (double) iMasterSize;
	    }

	    return dScale;
	}

	private double getScaleFactorToFill(Dimension masterSize, Dimension targetSize) {

	    double dScaleWidth = getScaleFactor(masterSize.width, targetSize.width);
	    double dScaleHeight = getScaleFactor(masterSize.height, targetSize.height);

	    double dScale = Math.max(dScaleHeight, dScaleWidth);

	    return dScale;

	}
}


