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
import java.util.Timer;
import java.util.TimerTask;
import java.util.WeakHashMap;

import model.MP3PlayerTrack;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class MP3Player {

	private MP3PlayerTrack track;
	private Minim minim;
	private AudioPlayer player;
	private WeakHashMap<MP3PlayerListener, Void> listeners;
	private Timer frameTimer;
	private int frame;
	private boolean playing;
	private boolean paused;

	/**
	 * Instantiates a new mp3 player.
	 */
	public MP3Player() {
		listeners = new WeakHashMap<>();
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
		this.track = track;
	}

	/**
	 * Play.
	 */
	public synchronized void play() {
		if (isPlaying()) {
			stopAndWait();
		}

		if (track != null) {
			player = minim.loadFile(track.getPath());
			paused = false;
			player.play();

			firePlaybackStarted();

		}
	}

	/**
	 * Loop.
	 */
	public synchronized void loop() {
		if (track != null) {
			player = minim.loadFile(track.getPath());
			paused = false;
			player.loop();
		}
	}

	/**
	 * Checks if is playing.
	 * 
	 * @return true, if is playing
	 */
	public boolean isPlaying() {
		return playing;
	}

	/**
	 * Stop and wait.
	 */
	private void stopAndWait() {
		if (player != null) {
			player.pause();
			minim.stop();
			player = null;
			firePlaybackStopped();
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
		listeners.put(listener, null);
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
			for (MP3PlayerListener mP3PlayerListener : listeners.keySet()) {
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

					for (MP3PlayerListener mP3PlayerListener : listeners
							.keySet()) {
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
			for (MP3PlayerListener mP3PlayerListener : listeners.keySet()) {
				mP3PlayerListener.playbackDidStop(this);
			}
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

}
