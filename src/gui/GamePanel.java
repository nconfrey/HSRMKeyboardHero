package gui;

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
import view.GuitarBackgroundPane;
import view.GuitarPane;
import controller.player.AlbumLoader;
import controller.player.MP3Player;
import controller.player.MP3PlayerListener;

public class GamePanel extends GHPanel implements MP3PlayerListener {

	private JPanel leftContent; // sidepanel for scores, songtitle ...
	private BufferedImage coverImage;
	private Image coverImageBuffer;
	private boolean paused;
	private ComponentListener componentListener;
	private GameResultsPanel resultsPanel;
	private GuitarPane guitarPane;

	public GamePanel() {
		setFocusable(true);

		// ContentPanel
		this.setLayout(new MigLayout("fill"));

		resultsPanel = new GameResultsPanel();
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

		// Panel
		leftContent = new JPanel(new MigLayout("fillx", "", "[]30[]"));
		leftContent.setOpaque(false);
		ScorePanel scorePanel = new ScorePanel();
		TitlePanel titlePanel = new TitlePanel();

		leftContent.add(titlePanel, "wrap, growx");
		if (!PlayerController.getInstance().isRecording()) {
			leftContent.add(scorePanel, "wrap, growx");
			scorePanel.setBackground(Color.WHITE);
		}

		titlePanel.setBackground(Color.WHITE);

		return leftContent;
	}

	private void loadBackgroundCover() {

		new Thread() {
			@Override
			public void run() {

				try {
					setCoverImage(ImageIO.read(getClass().getResourceAsStream(
							"/background.jpg")));
				} catch (IOException e) {
					e.printStackTrace();
				}

				final Track currentTrack = PlayerController.getInstance()
						.getTrack();

				final BufferedImage bandImage = AlbumLoader
						.loadCover(currentTrack);

				float[] data = new float[25];
				for (int i = 0; i < 25; i++) {
					data[i] = 1.0f / 25.0f;
				}
				ConvolveOp bio = new ConvolveOp(new Kernel(5, 5, data),
						ConvolveOp.EDGE_ZERO_FILL, null);
				BufferedImage blurred = bio.filter(bandImage, null);
				for (int i = 0; i < 49; i++) {
					blurred = bio.filter(blurred, null);
				}
				final BufferedImage blurredFinal = blurred;
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if (blurredFinal != null) {
							setCoverImage(blurredFinal);
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

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				repaint();
			}
		});
	}

	private void bufferImage() {
		if (coverImage != null) {
			double scaleFactor = Math.max(
					1d,
					getScaleFactorToFill(new Dimension(coverImage.getWidth(),
							coverImage.getHeight()), getSize()));

			int scaleWidth = (int) Math.round(coverImage.getWidth()
					* scaleFactor);
			int scaleHeight = (int) Math.round(coverImage.getHeight()
					* scaleFactor);

			coverImageBuffer = coverImage.getScaledInstance(scaleWidth,
					scaleHeight, Image.SCALE_FAST);
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

			int d = JOptionPane.showOptionDialog(getParent(), "Game Paused",
					"Keyboard Hero", JOptionPane.YES_NO_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, new String[] {
							"Back to menu", "Resume" }, "Resume");

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
}
