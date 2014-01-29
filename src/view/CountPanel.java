package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

public class CountPanel extends JPanel {
	
	private final int COUNTDOWN_TIME = 6;
	private final int REFRESH_INTERVAL = 50;
	
	private Timer timer;
	private int timeLeft;
	private CountListener listener;
	private JLabel label;
	
	public interface CountListener {
		public void countdownDidEnd();
	}
	
	public CountPanel(CountListener listener) {
		this.listener = listener;
		
		setOpaque(false);
		setBackground(new Color(50,50,50,180));
		setLayout(new MigLayout("fill", "[center]", "[center]"));
		
		label = new JLabel();
		label.setFont(new Font("sanserif", Font.BOLD, 40));
		label.setForeground(Color.white);
		add(label);
	}
	
	public void startTimer() {
		timeLeft = COUNTDOWN_TIME * 1000;
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				timeLeft -= REFRESH_INTERVAL;
				if (timeLeft <= 0) {
					timer.cancel();
					listener.countdownDidEnd();
				} else {
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							label.setText(String.valueOf((int)timeLeft / 1000));
						}
					});
				}
			}
		}, 0, REFRESH_INTERVAL);
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g);
	}
	
}
