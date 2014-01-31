/**
 * Handles Playing of MP3 Files 
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package controller.player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import model.MP3PlayerTrack;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class MP3Player {

	private MP3PlayerTrack track;
	private Minim minim;
	private AudioPlayer player;
	private List<MP3PlayerListener> listeners;
	private Timer frameTimer;
	private int frame;
	private boolean playing;
	private boolean looping;
	private boolean paused;
	private boolean volumeIsDecreased;
	private static final int GAIN_REDUCE = -10;
	private static final long REDUCE_TIME = 500;
	private Object lock = new Object();
	private boolean buffered;

	/**
	 * Instantiates a new m p3 player.
	 */
	public MP3Player() {
		listeners = new LinkedList<>();
		frame = 0;
		minim = new Minim(this);
	}

	/**
	 * Gets the track.
	 * 
	 * @return the track
	 */
	public MP3PlayerTrack getTrack() {
		return track;
	}

	/**
	 * Sets the track.
	 * 
	 * @param track the new track
	 */
	public void setTrack(MP3PlayerTrack track) {
		synchronized (lock) {
			this.track = track;
			buffered = false;
		}
	}

	/**
	 * Buffer.
	 */
	public void buffer() {
		synchronized (lock) {
			if (!buffered && track != null) {
				try {
					player = minim.loadFile(track.getPath());
					buffered = true;
				} catch (NullPointerException e) {
					track = null;
					firePlaybackDidFail();
				}
			}
		}
	}

	/**
	 * Play.
	 */
	public synchronized void play() {
		if (isPlaying()) {
			stopAndWait();
		}

		if (track != null) {
			buffer();

			if (player != null) {
				paused = false;
				player.play();

				firePlaybackStarted();
			}
		}
	}

	/**
	 * Loop.
	 */
	public synchronized void loop() {
		if (track != null) {
			player = minim.loadFile(track.getPath());
			paused = false;
			looping = true;
			player.loop();
		}
	}

	/**
	 * Checks if is playing.
	 * 
	 * @return true, if is playing
	 */
	public boolean isPlaying() {
		return playing || looping;
	}
	
	/**
	 * Checks if is looping.
	 *
	 * @return true, if is looping
	 */
	public boolean isLooping() {
		return looping;
	}

	/**
	 * Stop and wait.
	 */
	private void stopAndWait() {
		if (player != null) {
			player.pause();
			minim.stop();
			player = null;
			if (playing) {
				firePlaybackStopped();
			}
			looping = false;
			buffered = false;
		}
	}

	/**
	 * Stop.
	 */
	public synchronized void stop() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				stopAndWait();
			}
		}).start();
	}

	/**
	 * Pause resume.
	 */
	public synchronized void pauseResume() {
		if (player != null) {
			if (!paused) {
				paused = true;
				player.pause();
				System.out.println("Pause");
			} else if (paused) {
				paused = false;
				player.play();
				System.out.println("Resume");
			}
		}
	}

	// listener
	/**
	 * Adds the player listener.
	 * 
	 * @param listener the listener
	 */
	public void addPlayerListener(MP3PlayerListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes the player listener.
	 * 
	 * @param listener the listener
	 */
	public void removePlayerListener(MP3PlayerListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Fire playback started.
	 */
	private void firePlaybackStarted() {
		if (!isPlaying()) {
			playing = true;
			for (MP3PlayerListener mP3PlayerListener : listeners) {
				mP3PlayerListener.playbackDidStart(this);
			}
			final MP3Player finalThis = this;

			frameTimer = new Timer();
			frameTimer.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {

					if (!player.isPlaying() && !paused) {
						stopAndWait();
						return;
					}

					for (MP3PlayerListener mP3PlayerListener : listeners) {
						frame = player.position();
						mP3PlayerListener.playbackPlaying(finalThis, frame);
					}
				}
			}, 0, 50);
		}
	}

	/**
	 * Fire playback stopped.
	 */
	private void firePlaybackStopped() {
		if (isPlaying()) {
			playing = false;
			frameTimer.cancel();
			frame = 0;
			for (MP3PlayerListener mP3PlayerListener : listeners) {
				mP3PlayerListener.playbackDidStop(this);
			}
		}
	}

	/**
	 * Fire playback did fail.
	 */
	private void firePlaybackDidFail() {
		System.out.println(listeners);
		for (MP3PlayerListener mP3PlayerListener : listeners) {
			System.out.println(mP3PlayerListener);
			mP3PlayerListener.playbackDidFail(this);
		}
	}

	/**
	 * Sketch path. Needed by Minim.
	 * 
	 * @param fileName the file name
	 * @return the string
	 */
	public String sketchPath(String fileName) {
		return "";
	}

	/**
	 * Creates the input. Needed by Minim.
	 * 
	 * @param fileName the file name
	 * @return the input stream
	 */
	public InputStream createInput(String fileName) {
		try {
			return new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	/**
	 * Reduces the volume for 0.5 seconds
	 */
	public void volumeControl() {
		final Timer volumeTimer = new Timer();

		if (!volumeIsDecreased) {
			player.setGain(GAIN_REDUCE);
			volumeIsDecreased = true;
		}

		volumeTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				player.setGain(0);
				volumeTimer.cancel();
				volumeIsDecreased = false;
			}
		}, REDUCE_TIME, REDUCE_TIME);

	}
}
