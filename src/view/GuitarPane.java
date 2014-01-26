package view;

import gui.PlayerController;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import model.Stroke;
import model.StrokeKey;
import model.Track;
import controller.player.MP3Player;
import controller.player.MP3PlayerListener;
import controller.recorder.StrokeRecorder;
import controller.recorder.StrokeRecorderListener;

public class GuitarPane extends JPanel implements MP3PlayerListener,
		StrokeRecorderListener {

	private static final int CLEAN_INTERVAL = 50;
	private static final float RENDER_FACTOR = 2f;
	
	private static final float STROKE_WIDTH = 30;
	
	private static final int STYLE_PRESET = 0;
	private static final int STYLE_LIVE = 1;
	
	private int frame;
	private int maxLoadedFrame;
	private int drawCounter;
	private BufferedImage buffer;
	private List<StrokeView> strokeRects;
	private List<StrokeView> openStrokeRects;

	public GuitarPane() {
		strokeRects = new ArrayList<>();
		openStrokeRects = new ArrayList<>();

		PlayerController.getInstance().getPlayer().addListener(this);
		PlayerController.getInstance().getRecorder()
				.addStrokeRecorderListener(this);
		
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
		float x = getPositionForKey(strokeView.getStroke().getKey()) - width/2;
		float y = -getPixelForFrame(strokeView.getStroke().getStartFrame()) - height;
		
		strokeView.setRect(new RoundRectangle2D.Float(x, y, width, height,10,10));
	}

	public void draw(Graphics2D g) {
		// Draw background
		g.setColor(new Color(0xF1DDDDDD, true));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(new Color(0xAAAAAAAA, true));
		g.fill(new Rectangle2D.Float(0, getVerticalOffset() - 15, getWidth(), 30));
		
		g.setColor(new Color(0xC0C0C0));
		g.setStroke(new BasicStroke(1.5f));
		for (int i = 0; i < 5; i++) {
			float x = getPositionForLine(i);
			g.draw(new Line2D.Float(x, 0, x, getHeight()));
		}
		
		// Draw current frame
		g.setColor(Color.black);
		g.drawString("" + frame, 10, 20);

		g.translate(0, getPixelForFrame(frame) + getVerticalOffset());

		if (buffer != null) {
			g.drawImage(buffer, null, 0, -buffer.getHeight());
		}
		
		
		boolean render = maxLoadedFrame < frame + getFrameForPixel((int)(RENDER_FACTOR * getVerticalOffset()));
		//Draw background strokes
		if (render) {
			loadNewStrokes();
		}

		if (drawCounter % CLEAN_INTERVAL == 0) {
			cleanStrokeList(getPixelForFrame(frame) - getHeight());
		}

		for (StrokeView strokeView : openStrokeRects) {
			updateStrokeRect(strokeView);
		}
		
		for (StrokeView strokeView : strokeRects) {
			g.setColor(strokeView.getStroke().getKey().getColor());
			if (strokeView.getStyle() == STYLE_LIVE) {
				g.fill(strokeView.getRect());
			} else {
				g.draw(strokeView.getRect());
			}
		}

		drawCounter++;
	}
	
	
	private void loadNewStrokes() {
		Track track = PlayerController.getInstance().getTrack();
		int toFrame = getFrameForPixel((int)(getVerticalOffset()));
		List<Stroke> strokes = track.getStrokeSet().getListForFrameInRange(maxLoadedFrame, maxLoadedFrame + toFrame);
		//System.out.print(frame + "(" + maxLoadedFrame + ", " + (maxLoadedFrame + toFrame) + "): ");
		//System.out.println(strokes);
		addStrokes(strokes, STYLE_PRESET);
		maxLoadedFrame = maxLoadedFrame + toFrame;
	}
	
	private float getVerticalOffset() {
		return (getHeight() - 30) / 1.5f;
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
		this.frame = 0;
		strokeRects.clear();
		openStrokeRects.clear();
	}

	@Override
	public void playbackDidStop(MP3Player player) {
		// TODO Auto-generated method stub

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
}
