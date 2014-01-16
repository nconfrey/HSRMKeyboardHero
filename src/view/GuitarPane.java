package view;

import gui.PlayerController;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import model.Stroke;
import model.StrokeSet;
import model.Track;
import controller.player.MP3Player;
import controller.player.MP3PlayerListener;
import controller.recorder.StrokeRecorder;
import controller.recorder.StrokeRecorderListener;

public class GuitarPane extends JPanel implements MP3PlayerListener,
		StrokeRecorderListener {

	private static final int CLEAN_INTERVAL = 100;

	private int middleLineY;
	private GuitarPaneLayout layout;

	private int frame;
	private int drawCounter;
	BufferedImage buffer;
	private Map<Stroke, RoundRectangle2D> strokeRects;

	public GuitarPane() {
		strokeRects = new HashMap<>();

		PlayerController.getInstance().getPlayer().addListener(this);
		PlayerController.getInstance().getRecorder()
				.addStrokeRecorderListener(this);
		
		setOpaque(false);
		
		layout = new GuitarPaneLayout(getWidth());
	}

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		middleLineY = getPreferredSize().height / 2;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g.create();
		draw(g2);
		g2.dispose();
	}

	private void addStroke(Stroke stroke) {
		strokeRects.put(stroke, getStrokeRect(stroke));
	}

	private void updateStroke(Stroke stroke) {
		addStroke(stroke);
	}
	
	private void removeStroke(Stroke stroke) {
		strokeRects.remove(stroke);
	}

	public void render(Track track) {
		if (buffer == null && track.getStrokeSet() != null) {
			int bufferWidth = getPreferredSize().width;
			int bufferHeight = Layouter
					.getPixelForFrame(calculateBufferHeight(track
							.getStrokeSet()));
			if (bufferHeight > 0) {
				layout.setWidth(getWidth());
				
				buffer = new BufferedImage(bufferWidth, bufferHeight,
						BufferedImage.TYPE_INT_ARGB);

				Graphics2D g = (Graphics2D) buffer.getGraphics();
				g.translate(0, bufferHeight);
				g.setStroke(new BasicStroke(2));
				for (List<Stroke> strokeList : track.getStrokeSet()
						.getStrokes().values()) {
					for (Stroke stroke : strokeList) {
						RoundRectangle2D rect = getStrokeRect(stroke);
						g.setColor(new Color(0xBABABA));
						g.fill(rect);
						g.setColor(stroke.getKey().getColor());
						
						g.draw(getStrokeRect(stroke));
					}
				}
			}
		}
	}

	public int calculateBufferHeight(StrokeSet strokeSet) {
		int maxFrame = 0;
		for (List<Stroke> strokeList : strokeSet.getStrokes().values()) {
			for (Stroke stroke : strokeList) {
				maxFrame = Math.max(maxFrame,
						stroke.getStartFrame() + stroke.getLength());
			}
		}
		return maxFrame;
	}

	private int getStrokeLength(Stroke stroke) {
		if (stroke.isOpen()) {
			return frame - stroke.getStartFrame();
		} else {
			return stroke.getLength();
		}
	}

	private RoundRectangle2D getStrokeRect(Stroke stroke) {
		int width = Layouter.STROKE_WIDTH;
		int height = Layouter.getPixelForFrame(getStrokeLength(stroke));
		int x = layout.getPositionForKey(stroke.getKey()) - width/2;
		int y = -Layouter.getPixelForFrame(stroke.getStartFrame()) - height;
		

		return new RoundRectangle2D.Float(x, y, width, height,10,10);
	}

	public void draw(Graphics2D g) {
		// Draw middle line
		g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.2f));
		g.fillRect(0, middleLineY, getPreferredSize().width, 20);

		// Draw current frame
		g.setColor(Color.black);
		g.drawString("" + frame, 20, 20);

		g.translate(0, Layouter.getPixelForFrame(frame) + middleLineY);

		if (buffer != null) {
			g.drawImage(buffer, null, 0, -buffer.getHeight());
		}

		// Draw Live Strokes
		if (drawCounter % CLEAN_INTERVAL == 0) {
			cleanStrokeList();
		}

		if (strokeRects != null) {
			layout.setWidth(getWidth());
			for (Map.Entry<Stroke, RoundRectangle2D> stroke : strokeRects.entrySet()) {
				if (stroke.getKey().isOpen()) {
					updateStroke(stroke.getKey());
				}
				g.setColor(stroke.getKey().getKey().getColor());
				g.fill(stroke.getValue());
			}
		}

		drawCounter++;
	}

	// MP3Player Listener

	@Override
	public void playbackDidStart(MP3Player player) {
		this.frame = 0;
		strokeRects.clear();
		render(PlayerController.getInstance().getTrack());
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
		addStroke(stroke);
	}

	@Override
	public void redcorderDidCloseStroke(StrokeRecorder recorder, Stroke stroke) {
		if (stroke.isEmpty()) {
			removeStroke(stroke);
		}
	}

	private void cleanStrokeList() {
		List<Stroke> toRemove = new ArrayList<Stroke>();
		for (Map.Entry<Stroke, RoundRectangle2D> stroke : strokeRects.entrySet()) {
			//TODO
			if (stroke.getValue().getY() > getPreferredSize().getHeight()) {
				System.out.println("Removed " + stroke);
				toRemove.add(stroke.getKey());
			}
		}
		strokeRects.keySet().removeAll(toRemove);
	}
}
