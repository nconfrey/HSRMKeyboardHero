package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import controller.player.MP3Player;
import controller.player.MP3PlayerListener;
import controller.player.Playlist;
import model.Stroke;
import model.StrokeKey;
import model.StrokeSet;
import model.Track;

public class GuitarPane extends JPanel implements MP3PlayerListener{

	private List<RenderBlock> blocks;
	private Track track;
	private MP3Player player;
	private int frame;

	public GuitarPane() {
		blocks = new ArrayList<>();
		player = new MP3Player();
		player.addListener(this);
	}
	
	public void setTrack(Track track) {
		this.track = track;
		this.blocks.clear();
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
		
	}
	
	private static final int RENDER_BUFFER = 300;
	
	public void renderIfNeeded() {
		if (frame <= 2 || frame % Layouter.BLOCK_FRAME_COUNT == 0) {
			synchronized (this) {
				int renderFrame = frame + RENDER_BUFFER;
				int renderWidth = (int) getPreferredSize().getWidth();
				StrokeSet strokeSet = track.getStrokeSet();
				List<Stroke> strokeList;
				if (strokeSet != null) {
					strokeList = strokeSet.getListForFrameInRange(
							renderFrame, renderFrame + Layouter.BLOCK_FRAME_COUNT);
				} else {
					strokeList = new ArrayList<>();
				}
				RenderBlock renderBLock = new RenderBlock(renderWidth, renderFrame, Layouter.BLOCK_FRAME_COUNT, 
						strokeList);
				renderBLock.render();
				blocks.add(renderBLock);
			}
		}
	}
	
	public void draw(Graphics2D g) {
		synchronized (this) {
			ArrayList<RenderBlock> toDelete = new ArrayList<RenderBlock>();
			for (RenderBlock block : this.blocks) {
				if (frame - block.getStartFrame() > 1500) {
					toDelete.add(block);
				} else {
					int yTranslate = (int)Layouter.getPixelForFrame((frame - block.getStartFrame()));
					g.translate(0, yTranslate);
					block.drawBuffer(g);
					g.translate(0, -yTranslate);
				}
			}
			
			g.drawString("" + frame, 20, 20);
			
			blocks.removeAll(toDelete);
		}
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
		renderIfNeeded();
        repaint();
	}
}
