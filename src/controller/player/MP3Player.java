package controller.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.WeakHashMap;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

/**
 *
 * @author sseye001
 */
public class MP3Player {

    private MP3PlayerTrack track;
    private Minim minim;
    private AudioPlayer player;
    private WeakHashMap<MP3PlayerListener, Void> listeners;
    private Timer frameTimer;
    private int frame;
    private boolean playing;

    public MP3Player() {
        listeners = new WeakHashMap<>();
        frame = 0;
        minim = new Minim(this);
    }

    public MP3PlayerTrack getTrack() {
		return track;
	}

	public void setTrack(MP3PlayerTrack track) {
		this.track = track;
	}

    public synchronized void play() {
    	if (isPlaying()) {
            stopAndWait();
        }

        if (track != null) {
            File f = track.getFile();
            player = minim.loadFile(f.getAbsolutePath());
            firePlaybackStarted();
            player.play();
            //stopAndWait();
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    private void stopAndWait() {
    	if (player != null) {
    		player.pause();
    		minim.stop();
            player = null;
            firePlaybackStopped();
    	}
    }

    public synchronized void stop() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                stopAndWait();
            }
        }).start();
    }

    //listener
    public void addListener(MP3PlayerListener listener) {
        listeners.put(listener, null);
    }

    public void removeListener(MP3PlayerListener listener) {
        listeners.remove(listener);
    }

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
                    for (MP3PlayerListener mP3PlayerListener : listeners.keySet()) {
                    	frame = player.position();
                        mP3PlayerListener.playbackPlaying(finalThis, frame);
                    }
                }
            }, 0, 50);
        }
    }

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
    
    public String sketchPath(String fileName) {
    	return "";
    }
    
    public InputStream createInput(String fileName) {
    	try {
			return new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			return null;
		}
    }

}
