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
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
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

	private JPanel leftContent; // sidepanel for scores, songtitle ...
	private BufferedImage coverImage;
	private BufferedImage backgroundImage;
	private Image scaledBackgroundImage;
	private boolean paused;
	private ComponentListener componentListener;
	private GameResultsPanel resultsPanel;
	private ImagePanel miniCoverPanel;
	private GuitarPane guitarPane;

	public GamePanel() {
		setFocusable(true);

		// ContentPanel
		this.setLayout(new MigLayout("fill"));

		resultsPanel = new GameResultsPanel(this);
		resultsPanel.setVisible(false);
		resultsPanel.setOpaque(false);
		this.add(resultsPanel, "pos 0 0 container.w container.h");

		this.add(this.buildLeftContent(),
				"gapleft 30, gaptop  30, west, width 250:350:350");
		GuitarBackgroundPane backgroundPane = new GuitarBackgroundPane();
		this.add(backgroundPane, "center, growy");
		guitarPane = backgroundPane.getGuitarPane();
		guitarPane.start();

		loadBackgroundCover();

		componentListener = new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				bufferImage();
			}
		};
	}

	public JPanel buildLeftContent() {
		Color backgroundColor = new Color(0xEEDDDDDD, true);

		// Panel
		leftContent = new JPanel(new MigLayout("fillx", "", "[]30[]"));
		leftContent.setOpaque(false);
		ScorePanel scorePanel = new ScorePanel();

		TitlePanel titlePanel = new TitlePanel();
		titlePanel.setBackground(backgroundColor);
		leftContent.add(titlePanel, "wrap, growx");

		JPanel coverWrapperPanel = new JPanel(new MigLayout("fill, insets 3"));
		coverWrapperPanel.setBackground(backgroundColor);
		miniCoverPanel = new ImagePanel("cover.png", ImagePanel.SIZE_FILL);
		miniCoverPanel.setOpaque(false);
		coverWrapperPanel.add(miniCoverPanel, "grow");
		leftContent.add(coverWrapperPanel, "h 300!,wrap, growx");

		if (!PlayerController.getInstance().isRecording()) {
			leftContent.add(scorePanel, "wrap, growx");
			scorePanel.setBackground(backgroundColor);
		}

		return leftContent;
	}

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
				final Track currentTrack = PlayerController.getInstance()
						.getTrack();

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

	private void setCoverImage(BufferedImage image) {
		this.coverImage = image;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				miniCoverPanel.setCoverImage(coverImage);
			}
		});
	}

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
		// Game Results
		PlayerController.getInstance().getPlayer().addPlayerListener(this);
		addComponentListener(componentListener);
	}

	@Override
	public void panelWillDisappear() {
		removeComponentListener(componentListener);
	}

	@Override
	public void playbackDidStart(MP3Player player) {

	}

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

	@Override
	public void playbackPlaying(MP3Player player, int frame) {

	}

	@Override
	public void didPressBack(KeyEvent e) {
		if (!paused) {
			paused = true;
			PlayerController.getInstance().pauseResume();
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
				PlayerController.getInstance().stop();
				getNavigationController().popToRootPanel();
			}
			if (d == JOptionPane.NO_OPTION || d == JOptionPane.CLOSED_OPTION) {
				paused = false;
				PlayerController.getInstance().pauseResume();
				guitarPane.pauseOrResume();
			}
		}
	}

	public void resultPanelShouldClose() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				PlayerController.getInstance().stop();
				getNavigationController().popToRootPanel();
			}
		});
	}

	@Override
	public void resultPanelDidSelectReplay() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				PlayerController.getInstance().stop();
				PlayerController.getInstance().reset();
				GamePanel gameFrame = new GamePanel();
				getNavigationController().replacePanel(gameFrame);
			}
		});
	}
}
