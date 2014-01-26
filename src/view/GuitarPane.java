package view;

import gui.PlayerController;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;
import model.Stroke;
import model.StrokeKey;
import model.Track;
import controller.ScoreListener;
import controller.player.MP3Player;
import controller.player.MP3PlayerListener;
import controller.recorder.StrokeRecorder;
import controller.recorder.StrokeRecorderListener;

public class GuitarPane extends JPanel implements MP3PlayerListener,
		StrokeRecorderListener, ScoreListener, CountPanel.CountListener {

	private static final int CLEAN_INTERVAL = 50;
	private static final float RENDER_FACTOR = 2f;
	
	private static final float STROKE_WIDTH = 20;
	private static final float TAB_DISTANCE = 150;
	
	private static final int STYLE_PRESET = 0;
	private static final int STYLE_LIVE = 1;
	
	private int frame;
	private float strokeOffset;
	private Timer strokeOffsetTimer;
	private int maxLoadedFrame;
	private int drawCounter;
	private BufferedImage buffer;
	private List<StrokeView> strokeRects;
	private List<StrokeView> openStrokeRects;
	private boolean[] scoringKeys;
	private CountPanel countPanel;	

	public GuitarPane() {
		strokeRects = new ArrayList<>();
		openStrokeRects = new ArrayList<>();
		scoringKeys = new boolean[StrokeKey.STROKE_COUNT];
		
		PlayerController.getInstance().getPlayer().addPlayerListener(this);
		PlayerController.getInstance().getRecorder()
				.addStrokeRecorderListener(this);
		PlayerController.getInstance().getScoreController().addListener(this);
		
		countPanel = new CountPanel(this);
		countPanel.startTimer();
		
		
		setLayout(new MigLayout("fill", "[center]", "[center]"));
		add(countPanel, "width 100!, height 100!");
		
		setOpaque(false);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g.create();
		draw(g2);
		g2.dispose();
	}

	private void addStroke(Stroke stroke, int style) {
		StrokeView strokeView = new StrokeView(stroke, style);
		updateStrokeRect(strokeView);
		strokeRects.add(strokeView);
		if (stroke.isOpen() && !openStrokeRects.contains(stroke)) {
			openStrokeRects.add(strokeView);
		}
	}
	
	private void addStrokes(List<Stroke> strokes, int style) {
		for (Stroke stroke : strokes) {
			addStroke(stroke, style);
		}
	}

	private int getStrokeLength(Stroke stroke) {
		if (stroke.isOpen()) {
			return frame - stroke.getStartFrame();
		} else {
			return stroke.getLength();
		}
	}
	
	private void updateStrokeRect(StrokeView strokeView) {
		float width = STROKE_WIDTH;
		float height = getPixelForFrame(getStrokeLength(strokeView.getStroke()));
		float x = getPositionForKey(strokeView.getStroke().getKey()) - width/2f;
		float y = -getPixelForFrame(strokeView.getStroke().getStartFrame()) - height;
		
		strokeView.setRect(new RoundRectangle2D.Float(x, y, width, height, 0, 0));
	}

	public void draw(Graphics2D g) {
		// Draw background
		g.setColor(new Color(0xF1DDDDDD, true));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		float frameOffset = getPixelForFrame(frame);
		
		float tabOffset = frameOffset % TAB_DISTANCE;
		g.setColor(new Color(0xAABBBBBB, true));
		for (int i = 0; i < getHeight() / TAB_DISTANCE + 1; i++) {
			Point2D leftP = new Point2D.Float(0, tabOffset + i * TAB_DISTANCE);
			Point2D rightP = new Point2D.Float(getWidth(), tabOffset + i * TAB_DISTANCE);
			Line2D line = new Line2D.Float(leftP, rightP);
			g.draw(line);
		}
		
		g.setColor(new Color(0xC0C0C0));
		g.setStroke(new BasicStroke(1.5f));
		for (int i = 0; i < StrokeKey.STROKE_COUNT; i++) {
			float x = getPositionForLine(i);
			g.draw(new Line2D.Float(x, 0, x, getHeight()));
		}
		
		// Draw current frame
		g.setColor(Color.black);
		g.drawString("" + frame, 10, 20);

		float translateY = frameOffset + getVerticalOffset() + strokeOffset;
		g.translate(0, translateY);

		if (buffer != null) {
			g.drawImage(buffer, null, 0, -buffer.getHeight());
		}
		
		
		boolean render = maxLoadedFrame < frame + getFrameForPixel((int)(RENDER_FACTOR * getVerticalOffset()));
		//Draw background strokes
		if (render) {
			loadNewStrokes();
		}

		if (drawCounter % CLEAN_INTERVAL == 0) {
			cleanStrokeList(frameOffset - getHeight());
		}

		for (StrokeView strokeView : openStrokeRects) {
			updateStrokeRect(strokeView);
		}
		
		for (StrokeView strokeView : strokeRects) {
			Color c = strokeView.getStroke().getKey().getColor();
			if (strokeView.getStyle() == STYLE_PRESET) {
				c = c.brighter();
				c = new Color(c.getRed(),c.getGreen(),c.getBlue(), 180);
			}
			g.setColor(c);
			g.fill(strokeView.getRect());
		}
		
		g.translate(0, -translateY);
		
		for (int i = 0; i < StrokeKey.STROKE_COUNT; i++) {
			Color c = StrokeKey.keyForPosition(i).getColor();
			c = new Color(c.getRed(),c.getGreen(),c.getBlue(), 180);
			
			float x = getPositionForLine(i);
			int size = scoringKeys[i] ? 40 : 30;
		
			g.setColor(c);
			g.fill(new Rectangle2D.Float(x - size / 2f, getVerticalOffset() - size / 2f, size, size));
		}

		drawCounter++;
	}
	
	
	private void loadNewStrokes() {
		Track track = PlayerController.getInstance().getTrack();
		if (track.getStrokeSet() != null) {
			int toFrame = getFrameForPixel((int)(getVerticalOffset()));
			List<Stroke> strokes = track.getStrokeSet().getListForFrameInRange(maxLoadedFrame, maxLoadedFrame + toFrame);
			addStrokes(strokes, STYLE_PRESET);
			maxLoadedFrame = maxLoadedFrame + toFrame;
		} else {
			maxLoadedFrame = Integer.MAX_VALUE;
		}
	}
	
	private float getVerticalOffset() {
		return (getHeight() - 30) / 1.2f;
	}
	
	private float getPositionForLine(int line)  {
		return (line + 1) * (getWidth() / 6.0f) ;
	}
	
	private float getPositionForKey(StrokeKey key) {
		return getPositionForLine(key.getPosition());
	}
	
	public float getPixelForFrame(int frame) {
		return frame * 0.3f;
	}
	
	public int getFrameForPixel(float pixel) {
		return (int)( pixel / 0.3f);
	}

	// MP3Player Listener

	@Override
	public void playbackDidStart(MP3Player player) {
		
	}

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

	@Override
	public void playbackPlaying(MP3Player player, int frame) {
		this.frame = frame;
		repaint();
	}

	// StrokeRecorder Listener

	@Override
	public void redcorderDidOpenStroke(StrokeRecorder recorder, Stroke stroke) {
		addStroke(stroke, STYLE_LIVE);
	}

	@Override
	public void redcorderDidCloseStroke(StrokeRecorder recorder, Stroke stroke) {
	}

	private void cleanStrokeList(float treshold) {
		List<StrokeView> toRemove = new ArrayList<>();
		for (StrokeView strokeView : strokeRects) {
			if (strokeView.getRect().getY() + treshold > 0) {
			//	System.out.println("Removed " + stroke);
				toRemove.add(strokeView);
			}
		}
		strokeRects.removeAll(toRemove);
		openStrokeRects.removeAll(toRemove);
	}

	@Override
	public void scoringDidStart(StrokeKey key) {
		scoringKeys[key.getPosition()] = true;
	}

	@Override
	public void scoringDidEnd(StrokeKey key) {
		scoringKeys[key.getPosition()] = false;
	}

	@Override
	public void countdownDidEnd() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				countPanel.setVisible(false);
			}
		});
		PlayerController.getInstance().play();
	}
}
