package view;

import gui.PlayerController;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import model.Stroke;
import model.StrokeSet;
import model.Track;
import controller.player.MP3Player;
import controller.player.MP3PlayerListener;
import controller.player.Playlist;
import controller.recorder.StrokeRecorder;
import controller.recorder.StrokeRecorderListener;

public class GuitarPane extends JPanel implements MP3PlayerListener, StrokeRecorderListener{

	private int frame;
	BufferedImage buffer;

	public GuitarPane() {
		PlayerController.getInstance().getPlayer().addListener(this);
		PlayerController.getInstance().getRecorder().addStrokeRecorderListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g.create();
		draw(g2);
		g2.dispose();
	}
	
	public void render(Track track) {
		if (buffer == null && track.getStrokeSet() != null) {
			int bufferWidth = getPreferredSize().width;
			int bufferHeight = Layouter.getPixelForFrame(calculateBufferHeight(track.getStrokeSet()));
			if (bufferHeight > 0) {
				buffer = new BufferedImage(bufferWidth,bufferHeight,  BufferedImage.TYPE_INT_ARGB);
				
				Graphics g = buffer.getGraphics();
				for (List<Stroke> strokeList : track.getStrokeSet().getStrokes().values()) {
					for (Stroke stroke : strokeList) {
						int x = (int)Layouter.getPixelForStroke(stroke.getKey());
						int y = buffer.getHeight() - Layouter.getPixelForFrame(stroke.getStartFrame() + stroke.getLength());
						int width = Layouter.STROKE_WIDTH;
						int height = (int)Layouter.getPixelForFrame(stroke.getLength());
						
						g.fillRoundRect(x, y, width, height, 5, 5);
					}
				}
			}
		}
	}
	
	private void drawStroke(Graphics2D g, Stroke stroke) {
		int height;
		if (stroke.getLength() == 0) {
			height = (int)Layouter.getPixelForFrame(frame - stroke.getStartFrame());
		} else {
			height = (int)Layouter.getPixelForFrame(stroke.getLength());
		}
		
		int x = (int)Layouter.getPixelForStroke(stroke.getKey());
		int y = (getPreferredSize().height / 2) - Layouter.getPixelForFrame(stroke.getStartFrame() - frame) - height;
		int width = Layouter.STROKE_WIDTH;
		
		g.fillRoundRect(x, y, width, height, 5, 5);
	}
	
	public int calculateBufferHeight(StrokeSet strokeSet) {
		int maxFrame = 0;
		for (List<Stroke> strokeList : strokeSet.getStrokes().values()) {
			for (Stroke stroke : strokeList) {
				maxFrame = Math.max(maxFrame, stroke.getStartFrame() + stroke.getLength());
			}
		}
		return maxFrame;
	}
	
	public void draw(Graphics2D g) {
		if (buffer != null) {
			g.drawImage(buffer, null, 0, (int)(Layouter.getPixelForFrame(frame) - buffer.getHeight() + (getPreferredSize().height / 2)));
		}
		
		if (strokes != null) {
			for (Stroke stroke : strokes) {
				drawStroke(g, stroke);
			}
		}
		
		
		g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.2f));
		g.fillRect(0, getPreferredSize().height / 2, getPreferredSize().width, 20);
		
		g.setColor(Color.black);
		g.drawString("" + frame, 20, 20);
	}

	@Override
	public void playbackDidStart(MP3Player player) {
		this.frame = 0;
		strokes = new ArrayList<>();
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
	
	private List<Stroke> strokes;

	@Override
	public void redcorderDidOpenStroke(StrokeRecorder recorder, Stroke stroke) {
		System.out.println(stroke);
		strokes.add(stroke);
	}

	@Override
	public void redcorderDidCloseStroke(StrokeRecorder recorder, Stroke stroke) {
		
	}
}
