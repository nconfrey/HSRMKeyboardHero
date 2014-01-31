/**
 * Displays a Countdown
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package view;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

public class CountPanel extends InfoPanel {

	private final int COUNTDOWN_TIME = 6;
	private final int REFRESH_INTERVAL = 50;

	private Timer timer;
	private int timeLeft;
	private CountListener listener;
	private boolean paused;

	public interface CountListener {

		/**
		 * Countdown did end.
		 */
		public void countdownDidEnd();
	}

	/**
	 * Instantiates a new count panel.
	 * 
	 * @param listener the listener
	 */
	public CountPanel(CountListener listener) {
		this.listener = listener;
	}

	/**
	 * Start timer.
	 */
	public void startTimer() {
		paused = false;
		timeLeft = COUNTDOWN_TIME * 1000;
		createTimer();
	}

	/**
	 * Creates the timer.
	 */
	private void createTimer() {
		stopTimer();
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				timeLeft -= REFRESH_INTERVAL;
				if (timeLeft <= 0) {
					stopTimer();
					listener.countdownDidEnd();
				} else {
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							setText(String.valueOf(timeLeft / 1000));
						}
					});
				}
			}
		}, 0, REFRESH_INTERVAL);
	}

	/**
	 * Stop timer.
	 */
	public void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	/**
	 * Pause or resume.
	 */
	public void pauseOrResume() {
		if (timeLeft > 0) {
			if (paused) {
				paused = false;
				createTimer();
			} else {
				paused = true;
				stopTimer();
			}
		}
	}

	/**
	 * Checks if is counting.
	 * 
	 * @return true, if is counting
	 */
	public boolean isCounting() {
		return timeLeft > 0;
	}
}
