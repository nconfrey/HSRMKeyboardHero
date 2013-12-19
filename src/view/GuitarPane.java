package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import model.Stroke;
import model.StrokeSet;
import model.Track;
import controller.player.MP3Player;
import controller.player.MP3PlayerListener;
import controller.player.Playlist;

public class GuitarPane extends JPanel implements MP3PlayerListener{

	private Track track;
	private MP3Player player;
	private int frame;
	BufferedImage buffer;

	public GuitarPane() {
		player = new MP3Player();
		player.addListener(this);
	}
	
	public void setTrack(Track track) {
		this.track = track;
		render();
		Playlist currentPlaylist = player.getCurrentPlaylist();
		if(currentPlaylist == null) {
			currentPlaylist = player.createPlayList("defaultPlaylist");
		}
		currentPlaylist.addTrack(track.getMp3());
		player.selectPlaylist(0);
		player.selectTrack(0);
	}
	
	public void play() {
		buffer = null;
		render();
		player.play();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		final Graphics2D g2 = (Graphics2D)g.create();
		draw(g2);
		g2.dispose();
	}
	
	public void render() {
		if (buffer == null) {
			int bufferWidth = getPreferredSize().width;
			int bufferHeight = Layouter.getPixelForFrame(calculateBufferHeight(track.getStrokeSet()));
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
			g.drawImage(buffer, null, 0, (int)(Layouter.getPixelForFrame(frame) - buffer.getHeight()));
		}
		
		g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.2f));
		g.fillRect(0, getPreferredSize().height / 2, getPreferredSize().width, 20);
		
		g.setColor(Color.black);
		g.drawString("" + frame, 20, 20);
	}

	@Override
	public void playbackDidStart(MP3Player player) {
		this.frame = 0;
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
}
