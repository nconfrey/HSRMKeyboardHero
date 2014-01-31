/**
 * Main Game Panel where all panels get added that are needed for a game
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package view;

import helper.KeyboardHeroConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Track;
import net.miginfocom.swing.MigLayout;
import controller.PlayerController;
import controller.player.AlbumLoader;
import controller.player.MP3Player;
import controller.player.MP3PlayerListener;

public class GamePanel extends GHPanel implements MP3PlayerListener,
		GameResultsPanel.ResultListener {

	private PlayerController playerController;
	private JPanel leftContent; // sidepanel for scores, songtitle ...
	private BufferedImage coverImage;
	private BufferedImage backgroundImage;
	private Image scaledBackgroundImage;
	private boolean paused;
	private ComponentListener componentListener;
	private GameResultsPanel resultsPanel;
	private ImagePanel miniCoverPanel;
	private GuitarPane guitarPane;
	private ComboPanel comboPanel;
	private ScorePanel scorePanel;

	/**
	 * Instantiates a new game panel.
	 * 
	 * @param playerController the player controller
	 */
	public GamePanel(PlayerController playerController) {
		this.playerController = playerController;
		setFocusable(true);

		playerController.stop();
		playerController.getPlayer().addPlayerListener(this);

		// ContentPanel
		this.setLayout(new MigLayout("fill"));

		resultsPanel = new GameResultsPanel(playerController, this);
		resultsPanel.setVisible(false);
		resultsPanel.setOpaque(false);
		this.add(resultsPanel, "pos 0 0 container.w container.h");

		this.add(this.buildLeftContent(),
				"gapleft 30, gaptop  30, west, width 250:350:350");
		GuitarBackgroundPane backgroundPane = new GuitarBackgroundPane(
				playerController);
		this.add(backgroundPane, "center, growy");
		guitarPane = backgroundPane.getGuitarPane();

		loadBackgroundCover();

		componentListener = new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				bufferImage();
			}
		};
	}

	/**
	 * Builds the left content.
	 * 
	 * @return the left content jpanel
	 */
	public JPanel buildLeftContent() {
		Color backgroundColor = new Color(0xEEDDDDDD, true);

		// Panel
		leftContent = new JPanel(new MigLayout("fillx", "", "[]30[]"));
		leftContent.setOpaque(false);
		scorePanel = new ScorePanel(playerController);

		TitlePanel titlePanel = new TitlePanel(playerController);
		titlePanel.setBackground(backgroundColor);
		leftContent.add(titlePanel, "wrap, growx");

		JPanel coverWrapperPanel = new JPanel(new MigLayout("fill, insets 3"));
		coverWrapperPanel.setBackground(backgroundColor);
		miniCoverPanel = new ImagePanel("cover.png", ImagePanel.SIZE_FILL);
		miniCoverPanel.setOpaque(false);
		coverWrapperPanel.add(miniCoverPanel, "grow");
		leftContent.add(coverWrapperPanel, "h 300!,wrap, growx");

		if (!playerController.isRecording()) {

			comboPanel = new ComboPanel(playerController);
			leftContent.add(comboPanel, "h 60!, wrap, growx");

			leftContent.add(scorePanel, "wrap, growx");
			scorePanel.setBackground(backgroundColor);
		}

		return leftContent;
	}

	/**
	 * Load background cover.
	 */
	private void loadBackgroundCover() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					BufferedImage image = ImageIO.read(getClass()
							.getResourceAsStream("/background.jpg"));
					if (backgroundImage == null) {
						setBackgroundCoverImage(image);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		new Thread() {
			@Override
			public void run() {
				final Track currentTrack = playerController.getTrack();

				final BufferedImage bandImage = AlbumLoader
						.loadCover(currentTrack);

				if (bandImage != null) {
					setCoverImage(bandImage);
					BufferedImage blurred = new BufferedImage(
							bandImage.getWidth(), bandImage.getHeight(),
							bandImage.getType());
					BoxBlurFilter blurFilter = new BoxBlurFilter();
					blurFilter.filter(bandImage, blurred);
					setBackgroundCoverImage(blurred);
				}
			}
		}.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (this.scaledBackgroundImage == null)
			return;

		int width = getWidth() - 1;
		int height = getHeight() - 1;

		int x = (width - scaledBackgroundImage.getWidth(this)) / 2;
		int y = (height - scaledBackgroundImage.getHeight(this)) / 2;

		g.drawImage(scaledBackgroundImage, x, y, this);
	}

	/**
	 * Sets the cover image.
	 * 
	 * @param image the new cover image
	 */
	private void setCoverImage(BufferedImage image) {
		this.coverImage = image;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				miniCoverPanel.setCoverImage(coverImage);
			}
		});
	}

	/**
	 * Sets the background cover image.
	 * 
	 * @param coverImage the new background cover image
	 */
	public void setBackgroundCoverImage(BufferedImage coverImage) {
		this.backgroundImage = coverImage;
		bufferImage();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				repaint();
			}
		});
	}

	/**
	 * Buffer image. Scales the background image
	 */
	private void bufferImage() {
		if (backgroundImage != null) {
			double scaleFactor = Math.max(
					1d,
					getScaleFactorToFill(
							new Dimension(backgroundImage.getWidth(),
									backgroundImage.getHeight()), getSize()));

			int scaleWidth = (int) Math.round(backgroundImage.getWidth()
					* scaleFactor);
			int scaleHeight = (int) Math.round(backgroundImage.getHeight()
					* scaleFactor);

			scaledBackgroundImage = backgroundImage.getScaledInstance(
					scaleWidth, scaleHeight, Image.SCALE_FAST);
		}
	}

	/**
	 * Gets the scale factor.
	 * 
	 * @param iMasterSize the i master size
	 * @param iTargetSize the i target size
	 * @return the scale factor
	 */
	private double getScaleFactor(int iMasterSize, int iTargetSize) {

		double dScale = 1;
		if (iMasterSize > iTargetSize) {
			dScale = (double) iTargetSize / (double) iMasterSize;
		} else {
			dScale = (double) iTargetSize / (double) iMasterSize;
		}

		return dScale;
	}

	/**
	 * Gets the scale factor to fill.
	 * 
	 * @param masterSize the master size
	 * @param targetSize the target size
	 * @return the scale factor to fill
	 */
	private double getScaleFactorToFill(Dimension masterSize,
			Dimension targetSize) {

		double dScaleWidth = getScaleFactor(masterSize.width, targetSize.width);
		double dScaleHeight = getScaleFactor(masterSize.height,
				targetSize.height);

		double dScale = Math.max(dScaleHeight, dScaleWidth);

		return dScale;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.GHPanel#panelWillAppear()
	 */
	@Override
	public void panelWillAppear() {
		// Game Results
		addComponentListener(componentListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.GHPanel#panelWillDisappear()
	 */
	@Override
	public void panelWillDisappear() {
		removeComponentListener(componentListener);
		playerController.getPlayer().removePlayerListener(this);
		guitarPane.cleanUp();

		if (comboPanel != null) {
			comboPanel.cleanUp();
		}

		if (scorePanel != null) {
			scorePanel.cleanUp();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.player.MP3PlayerListener#playbackDidStart(controller.player
	 * .MP3Player)
	 */
	@Override
	public void playbackDidStart(MP3Player player) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.player.MP3PlayerListener#playbackDidStop(controller.player
	 * .MP3Player)
	 */
	@Override
	public void playbackDidStop(MP3Player player) {
		if (!paused) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					resultsPanel.setVisible(true);
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.player.MP3PlayerListener#playbackPlaying(controller.player
	 * .MP3Player, int)
	 */
	@Override
	public void playbackPlaying(MP3Player player, int frame) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.player.MP3PlayerListener#playbackDidFail(controller.player
	 * .MP3Player)
	 */
	@Override
	public void playbackDidFail(final MP3Player player) {
		int d = JOptionPane
				.showOptionDialog(getParent(), KeyboardHeroConstants
						.getString("playback_failed_text"),
						KeyboardHeroConstants
								.getString("playback_failed_title"),
						JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE,
						null, new String[] { KeyboardHeroConstants
								.getString("back_to_menu"), },
						KeyboardHeroConstants.getString("back_to_menu"));

		if (d == JOptionPane.YES_OPTION) {
			playerController.stop();
			getNavigationController().popToRootPanel();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.GHPanel#didPressBack(java.awt.event.KeyEvent)
	 */
	@Override
	public void didPressBack(KeyEvent e) {
		if (!paused) {
			paused = true;
			guitarPane.pauseOrResume();

			int d = JOptionPane.showOptionDialog(
					getParent(),
					KeyboardHeroConstants.getString("game_pause_dialog_title"),
					KeyboardHeroConstants.getString("game_title"),
					JOptionPane.YES_NO_OPTION,
					JOptionPane.PLAIN_MESSAGE,
					null,
					new String[] {
							KeyboardHeroConstants.getString("back_to_menu"),
							KeyboardHeroConstants.getString("resume") },
					KeyboardHeroConstants.getString("resume"));

			if (d == JOptionPane.YES_OPTION) {
				playerController.stop();
				getNavigationController().popToRootPanel();
			}
			if (d == JOptionPane.NO_OPTION || d == JOptionPane.CLOSED_OPTION) {
				paused = false;
				guitarPane.pauseOrResume();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.GameResultsPanel.ResultListener#resultPanelShouldClose()
	 */
	@Override
	public void resultPanelShouldClose() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				playerController.stop();
				getNavigationController().popToRootPanel();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.GameResultsPanel.ResultListener#resultPanelDidSelectReplay()
	 */
	@Override
	public void resultPanelDidSelectReplay() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				playerController.stop();
				playerController.reset();
				GamePanel gameFrame = new GamePanel(playerController);
				getNavigationController().replacePanel(gameFrame);
			}
		});
	}
}
