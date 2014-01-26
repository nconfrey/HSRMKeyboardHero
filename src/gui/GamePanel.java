package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;
import view.GuitarBackgroundPane;
import controller.player.AlbumLoader;
import model.Track;

public class GamePanel extends GHPanel {

	private JPanel leftContent; // sidepanel for scores, songtitle ...
	private BufferedImage coverImage;
	private Image coverImageBuffer;
	private boolean paused;
	private KeyEventDispatcher keyEventDispatcher;

	public GamePanel() {
		setFocusable(true);

		 // ContentPanel
	    this.setLayout(new MigLayout("fill"));
	    this.add(this.buildLeftContent(), "gapleft 30, gaptop  30, west, width 250:350:350");
	    this.add(new GuitarBackgroundPane(), "center, growy");
	   
	    loadBackgroundCover();
		
	    keyEventDispatcher = new KeyEventDispatcher() {

			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE && e.getID() == KeyEvent.KEY_PRESSED && !paused) {
					PlayerController.getInstance().pauseResume();
					paused = true;
					
					int d = JOptionPane.showOptionDialog(getParent(), "Game Paused","Keyboard Hero",
			                JOptionPane.YES_NO_OPTION,
			                JOptionPane.PLAIN_MESSAGE, null, 
			                new String[]{"Back to menu", "Resume"}, "Resume");
					
					if (d == JOptionPane.YES_OPTION){
						PlayerController.getInstance().stop();
						getNavigationController().popToRootPanel();
					}
					if (d == JOptionPane.NO_OPTION || d == JOptionPane.CLOSED_OPTION){
						paused = false;
						PlayerController.getInstance().pauseResume();
					}

					return true;
	    		}
				return false;
			}
		};
	    
	    
	    
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				bufferImage();
			}
		});

		PlayerController.getInstance().play();
	}

	public JPanel buildLeftContent() {

		// Panel
		leftContent = new JPanel(new MigLayout("fillx", "", "[]30[]"));
		leftContent.setOpaque(false);		
		ScorePanel scorePanel = new ScorePanel();
		TitlePanel titlePanel = new TitlePanel();
		
		leftContent.add(titlePanel, "wrap, growx");
		if(!PlayerController.getInstance().isRecording()){
			leftContent.add(scorePanel, "wrap, growx");
			scorePanel.setBackground(Color.WHITE);
		}
		
		titlePanel.setBackground(Color.WHITE);
		
	    return leftContent;
	}

	private void loadBackgroundCover() {

		try {
			setCoverImage(ImageIO.read(getClass().getResourceAsStream(
					"/background.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		final Track currentTrack = PlayerController.getInstance().getTrack();
		new Thread() {
			public void run() {
				final BufferedImage bandImage = AlbumLoader.loadCover(currentTrack);
		    		SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							if (bandImage != null) {
								setCoverImage(bandImage);
							}
						}
					});
			}
		}.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (this.coverImageBuffer == null)
			return;

		int width = getWidth() - 1;
		int height = getHeight() - 1;

		int x = (width - coverImageBuffer.getWidth(this)) / 2;
		int y = (height - coverImageBuffer.getHeight(this)) / 2;

		g.drawImage(coverImageBuffer, x, y, this);
	}
	
	public void setCoverImage(BufferedImage coverImage) {
		this.coverImage = coverImage;
		bufferImage();
		repaint();
	}
	
	private void bufferImage() {
		double scaleFactor = Math.max(1d, getScaleFactorToFill(new Dimension(coverImage.getWidth(), coverImage.getHeight()), getSize()));

	    int scaleWidth = (int) Math.round(coverImage.getWidth() * scaleFactor);
	    int scaleHeight = (int) Math.round(coverImage.getHeight() * scaleFactor);

	    coverImageBuffer = coverImage.getScaledInstance(scaleWidth, scaleHeight, Image.SCALE_FAST);
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

	private double getScaleFactorToFill(Dimension masterSize,
			Dimension targetSize) {

		double dScaleWidth = getScaleFactor(masterSize.width, targetSize.width);
		double dScaleHeight = getScaleFactor(masterSize.height,
				targetSize.height);

		double dScale = Math.max(dScaleHeight, dScaleWidth);

		return dScale;

	}

	@Override
	public void panelWillAppear() {
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(keyEventDispatcher);
	}

	@Override
	public void panelWillDisappear() {
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.removeKeyEventDispatcher(keyEventDispatcher);
	}
}
