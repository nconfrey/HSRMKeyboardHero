package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import controller.player.MP3PlayerListener;
import model.Stroke;
import model.StrokeKey;

public class GuitarPane extends JPanel{

	private List<RenderBlock> blocks;
	private Timer frameTimer;
	private double frame;
	private double endFrame;
	
	public GuitarPane() {
		blocks = new ArrayList<>();
		
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		final Graphics2D g2 = (Graphics2D)g.create();
		draw(g2);
		g2.dispose();
	}
	
	public void render() {
		this.frame = 0;
		this.endFrame = Layouter.getFrameForPixel((int)getPreferredSize().getHeight());
		
		frameTimer = new Timer();
        frameTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                frame += 0.5;
                endFrame += 0.5;
                renderIfNeeded();
                repaint();
            }
        }, 0, 10);
	}
	
	public void renderIfNeeded() {
		if (endFrame % Layouter.BLOCK_FRAME_COUNT == 40) {
			ArrayList<Stroke> strokes = new ArrayList<>();
			strokes.add(new Stroke(StrokeKey.F1, (int)endFrame + 20, 60));
			strokes.add(new Stroke(StrokeKey.F2, (int)endFrame + 10, 30));
			
			RenderBlock r = new RenderBlock((int)getPreferredSize().getWidth(), (int)endFrame+10, strokes);
			r.render();
			blocks.add(r);
		}
	}
	
	public void draw(Graphics2D g) {
		ArrayList<RenderBlock> toDelete = new ArrayList<RenderBlock>();
		for (RenderBlock block : this.blocks) {
			System.out.println(block.getMaxFrame());
			if (block.getStartFrame() + block.getMaxFrame() < frame) {
				toDelete.add(block);
			} else {
				int yTranslate = (int)Layouter.getPixelForFrame( (int)(block.getStartFrame() - frame));
				g.translate(0, yTranslate);
				block.drawBuffer(g);
				g.translate(0, -yTranslate);
			}
		}
		blocks.removeAll(toDelete);
	}
}
