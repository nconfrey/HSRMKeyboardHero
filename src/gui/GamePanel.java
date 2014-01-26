package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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

	private JPanel leftContent; // sidepanel for scores, songtitle ...
	private BufferedImage coverImage;
	private Image coverImageBuffer;

	public GamePanel() {
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
					KeyboardFocusManager manager = KeyboardFocusManager
							.getCurrentKeyboardFocusManager();
					manager.removeKeyEventDispatcher(this);
					PlayerController.getInstance().stop();
					getNavigationController().popToRootPanel();
					return true;
				}
				return false;
			}
		});
		
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
			setCoverImage(ImageIO.read(getClass().getResourceAsStream(
					"/background.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		final Track currentTrack = PlayerController.getInstance().getTrack();
		new Thread() {
			public void run() {
				final BufferedImage bandImage = AlbumLoader
						.loadCover(currentTrack);
				if (bandImage != null) {
					setCoverImage(bandImage);
				}
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
}
