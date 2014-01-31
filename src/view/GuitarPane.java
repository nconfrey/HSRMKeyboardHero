/**
 * Guitar Panel that displays a Guitar Neck with strokes
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Stroke;
import model.StrokeKey;
import model.Track;
import net.miginfocom.swing.MigLayout;
import controller.PlayerController;
import controller.ScoreListener;
import controller.player.MP3Player;
import controller.player.MP3PlayerListener;
import controller.recorder.StrokeRecorder;
import controller.recorder.StrokeRecorderListener;

public class GuitarPane extends JPanel implements MP3PlayerListener,
		StrokeRecorderListener, ScoreListener, CountPanel.CountListener {

	private static final int CLEAN_INTERVAL = 50;
	private static final float RENDER_FACTOR = 2f;

	private static final float STROKE_WIDTH = 40;
	private static final float TAB_DISTANCE = 150;

	private static final int STYLE_PRESET = 0;
	private static final int STYLE_LIVE = 1;

	private PlayerController playerController;
	private int frame;
	private float strokeOffset;
	private Timer strokeOffsetTimer;
	private int maxLoadedFrame;
	private int drawCounter;
	private List<StrokeView> strokeRects;
	private List<StrokeView> openStrokeRects;
	private List<StrokeView> toRemove;
	private boolean[] scoringKeys;
	private CountPanel countPanel;
	private InfoPanel[] infoPanels;

	/**
	 * Instantiates a new guitar pane.
	 * 
	 * @param playerController the player controller
	 */
	public GuitarPane(PlayerController aPlayerController) {
		this.playerController = aPlayerController;
		strokeRects = new ArrayList<>();
		openStrokeRects = new ArrayList<>();
		toRemove = new ArrayList<>();
		scoringKeys = new boolean[StrokeKey.STROKE_COUNT];

		setOpaque(false);

		playerController.getPlayer().addPlayerListener(this);
		playerController.getRecorder().addStrokeRecorderListener(this);
		playerController.getScoreController().addListener(this);

		countPanel = new CountPanel(this);

		setLayout(new MigLayout("fill", "[center]", "[center]"));
		add(countPanel, "width 100!, height 100!");

		infoPanels = new InfoPanel[StrokeKey.STROKE_COUNT + 1];
		for (int i = 0; i < infoPanels.length - 1; i++) {
			InfoPanel infoPanel = new InfoPanel();
			infoPanel.setText(StrokeKey.keyForPosition(i).toString());
			infoPanel.setFontSize(16);
			infoPanels[i] = infoPanel;
			add(infoPanel, "pos (" + (i + 1)
					+ " * container.w / 6 - 25px) (container.h - 90), width 50, height 50");
		}
		InfoPanel infoPanel = new InfoPanel();
		infoPanel.setText(StrokeKey.ENTER.toString());
		infoPanel.setFontSize(16);
		infoPanels[infoPanels.length - 1] = infoPanel;
		add(infoPanel, "pos 30 (container.h - 175) (container.w - 30), height 50");

		countPanel.startTimer();
		new Thread(new Runnable() {

			@Override
			public void run() {
				playerController.getPlayer().buffer();
			}
		}).start();
	}

	/**
	 * Clean up. Remove all listeners.
	 */
	public void cleanUp() {
		playerController.getPlayer().removePlayerListener(this);
		playerController.getRecorder().removeStrokeRecorderListener(this);
		playerController.getScoreController().removeListener(this);
	}

	/**
	 * Pause or resume.
	 */
	public void pauseOrResume() {
		if (countPanel.isCounting()) {
			countPanel.pauseOrResume();
		} else {
			playerController.getPlayer().pauseResume();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g.create();
		draw(g2);
		g2.dispose();
	}

	/**
	 * Adds the stroke.
	 * 
	 * @param stroke the stroke
	 * @param style the style
	 */
	private void addStroke(Stroke stroke, int style) {
		StrokeView strokeView = new StrokeView(stroke, style);
		updateStrokeRect(strokeView);
		strokeRects.add(strokeView);
		if (stroke.isOpen() && !openStrokeRects.contains(stroke)) {
			openStrokeRects.add(strokeView);
		}
	}

	/**
	 * Adds the strokes.
	 * 
	 * @param strokes the strokes
	 * @param style the style
	 */
	private void addStrokes(List<Stroke> strokes, int style) {
		for (Stroke stroke : strokes) {
			addStroke(stroke, style);
		}
	}

	/**
	 * Gets the stroke length.
	 * 
	 * @param stroke the stroke
	 * @return the stroke length
	 */
	private int getStrokeLength(Stroke stroke) {
		if (stroke.isOpen()) {
			return frame - stroke.getStartFrame();
		} else {
			return stroke.getLength();
		}
	}

	/**
	 * Update stroke rect.
	 * 
	 * @param strokeView the stroke view
	 */
	private void updateStrokeRect(StrokeView strokeView) {
		float width = STROKE_WIDTH;
		float height = getPixelForFrame(getStrokeLength(strokeView.getStroke()));
		float x = getPositionForKey(strokeView.getStroke().getKey()) - width
				/ 2f;
		float y = -getPixelForFrame(strokeView.getStroke().getStartFrame())
				- height;

		strokeView
				.setRect(new RoundRectangle2D.Float(x, y, width, height, 0, 0));
	}

	/**
	 * Draw.
	 * 
	 * @param g the graphics context
	 */
	public void draw(Graphics2D g) {
		// Draw background
		g.setColor(new Color(0xEEDDDDDD, true));
		g.fillRect(0, 0, getWidth(), getHeight());

		float frameOffset = getPixelForFrame(frame);

		float tabOffset = frameOffset % TAB_DISTANCE;
		g.setColor(new Color(0xAABBBBBB, true));
		for (int i = 0; i < getHeight() / TAB_DISTANCE + 1; i++) {
			Point2D leftP = new Point2D.Float(0, tabOffset + i * TAB_DISTANCE);
			Point2D rightP = new Point2D.Float(getWidth(), tabOffset + i
					* TAB_DISTANCE);
			Line2D line = new Line2D.Float(leftP, rightP);
			g.draw(line);
		}

		for (int i = 0; i < StrokeKey.STROKE_COUNT; i++) {
			float x = getPositionForLine(i);
			if (scoringKeys[i]) {
				g.setStroke(new BasicStroke(3));
				g.setColor(StrokeKey.keyForPosition(i).getPrimaryColor());
			} else {
				g.setStroke(new BasicStroke(1.5f));
				g.setColor(new Color(0xC0C0C0));
			}
			g.draw(new Line2D.Float(x, 0, x, getHeight()));
		}

		// Draw current frame
		/* debug
		g.setColor(Color.black);
		g.drawString("" + frame, 10, 20);
		*/

		float translateY = frameOffset + getVerticalOffset() + strokeOffset;
		g.translate(0, translateY);

		boolean render = maxLoadedFrame < frame
				+ getFrameForPixel((int) (RENDER_FACTOR * getVerticalOffset()));
		// Draw background strokes
		if (render) {
			loadNewStrokes();
		}

		if (drawCounter % CLEAN_INTERVAL == 0) {
			cleanStrokeList(frameOffset - getHeight());
		}

		strokeRects.removeAll(toRemove);
		openStrokeRects.removeAll(toRemove);
		toRemove.clear();

		for (StrokeView strokeView : openStrokeRects) {
			updateStrokeRect(strokeView);
		}

		for (StrokeView strokeView : strokeRects) {
			Color c = strokeView.getStroke().getKey().getPrimaryColor();
			if (strokeView.getStyle() == STYLE_PRESET) {
				c = strokeView.getStroke().getKey().getSecondaryColor();
			}
			g.setColor(c);
			g.fill(strokeView.getRect());
		}

		g.translate(0, -translateY);

		// fixed blocks for stroking
		for (int i = 0; i < StrokeKey.STROKE_COUNT; i++) {
			Color c = StrokeKey.keyForPosition(i).getPrimaryColor();

			float x = getPositionForLine(i);
			float size = STROKE_WIDTH - 10;

			g.setColor(c);
			g.fill(new Rectangle2D.Float(x - size / 2f, getVerticalOffset()
					- size / 2f, size, size));
		}

		drawCounter++;
	}

	/**
	 * Load new strokes.
	 */
	private void loadNewStrokes() {
		Track track = playerController.getTrack();
		if (track.getStrokeSet() != null) {
			int toFrame = getFrameForPixel((int) (getVerticalOffset()));
			List<Stroke> strokes = track.getStrokeSet().getListForFrameInRange(
					maxLoadedFrame, maxLoadedFrame + toFrame);
			addStrokes(strokes, STYLE_PRESET);
			maxLoadedFrame = maxLoadedFrame + toFrame;
		} else {
			maxLoadedFrame = Integer.MAX_VALUE;
		}
	}

	/**
	 * Gets the vertical offset.
	 * 
	 * @return the vertical offset
	 */
	private float getVerticalOffset() {
		return getHeight() - 150;
	}

	/**
	 * Gets the position for line.
	 * 
	 * @param line the line
	 * @return the position for line
	 */
	private float getPositionForLine(int line) {
		return (line + 1) * (getWidth() / 6.0f);
	}

	/**
	 * Gets the position for key.
	 * 
	 * @param key the key
	 * @return the position for key
	 */
	private float getPositionForKey(StrokeKey key) {
		return getPositionForLine(key.getPosition());
	}

	/**
	 * Gets the pixel for frame.
	 * 
	 * @param frame the frame
	 * @return the pixel for frame
	 */
	public float getPixelForFrame(int frame) {
		return frame * 0.3f;
	}

	/**
	 * Gets the frame for pixel.
	 * 
	 * @param pixel the pixel
	 * @return the frame for pixel
	 */
	public int getFrameForPixel(float pixel) {
		return (int) (pixel / 0.3f);
	}

	// MP3Player Listener

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
		strokeOffsetTimer = new Timer();
		strokeOffsetTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				if (strokeOffset >= (getHeight() - getVerticalOffset())) {
					strokeOffsetTimer.cancel();
				} else {
					strokeOffset += 10;
					repaint();
				}

			}
		}, 0, 50);
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
		this.frame = frame;
		repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.player.MP3PlayerListener#playbackDidFail()
	 */
	@Override
	public void playbackDidFail(final MP3Player player) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				countPanel.stopTimer();
			}
		});
	}

	// StrokeRecorder Listener

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.recorder.StrokeRecorderListener#redcorderDidOpenStroke(controller
	 * .recorder.StrokeRecorder, model.Stroke)
	 */
	@Override
	public void redcorderDidOpenStroke(StrokeRecorder recorder, Stroke stroke) {
		addStroke(stroke, STYLE_LIVE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.recorder.StrokeRecorderListener#redcorderDidCloseStroke(controller
	 * .recorder.StrokeRecorder, model.Stroke)
	 */
	@Override
	public void redcorderDidCloseStroke(StrokeRecorder recorder, Stroke stroke) {
		if (stroke.isEmpty()) {
			for (StrokeView strokeView : strokeRects) {
				if (strokeView.getStroke() == stroke) {
					toRemove.add(strokeView);
					break;
				}
			}
		}
	}

	/**
	 * Clean stroke list.
	 * 
	 * @param treshold the treshold
	 */
	private void cleanStrokeList(float treshold) {
		for (StrokeView strokeView : strokeRects) {
			if (strokeView.getRect().getY() + treshold > 0) {
				// System.out.println("Removed " + stroke);
				toRemove.add(strokeView);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ScoreListener#scoringDidStart(model.StrokeKey)
	 */
	@Override
	public void scoringDidStart(StrokeKey key) {
		scoringKeys[key.getPosition()] = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ScoreListener#scoringDidEnd(model.StrokeKey)
	 */
	@Override
	public void scoringDidEnd(StrokeKey key) {
		scoringKeys[key.getPosition()] = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.CountPanel.CountListener#countdownDidEnd()
	 */
	@Override
	public void countdownDidEnd() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				countPanel.setVisible(false);
				for (int i = 0; i < infoPanels.length; i++) {
					infoPanels[i].setVisible(false);
				}
			}
		});
		playerController.play();
	}
}
