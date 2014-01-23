package controller.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Track;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;

/**
 *
 * @author sseye001
 */
public class MP3Player {

    private MP3PlayerTrack track;
    private Player player;
    private ArrayList<MP3PlayerListener> listeners;
    private Timer frameTimer;
    private int frame;
    private boolean playing;

    public MP3Player() {
        listeners = new ArrayList<>();
        frame = 0;
    }

    public MP3PlayerTrack getTrack() {
		return track;
	}

	public void setTrack(MP3PlayerTrack track) {
		this.track = track;
	}

    public synchronized void play() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                if (isPlaying()) {
                    stopAndWait();
                }

                if (track != null) {
                    File f = track.getFile();
                    try {
                        player = new Player(new FileInputStream(f));

                        firePlaybackStarted();
                        try {
                            player.play();
                        } catch (JavaLayerException ex) {
                            Logger.getLogger(MP3Player.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        stopAndWait();
                    } catch (JavaLayerException ex) {
                        Logger.getLogger(MP3Player.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MP3Player.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }).start();
    }

    public boolean isPlaying() {
        return playing;
    }

    private void stopAndWait() {
    	if (player != null) {
    		player.close();
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
        listeners.add(listener);
    }

    public void removeListener(MP3PlayerListener listener) {
        listeners.remove(listener);
    }

    private void firePlaybackStarted() {
        if (!isPlaying()) {
            playing = true;
            for (MP3PlayerListener mP3PlayerListener : listeners) {
                mP3PlayerListener.playbackDidStart(this);
            }
            final MP3Player finalThis = this;

            frameTimer = new Timer();
            frameTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    for (MP3PlayerListener mP3PlayerListener : listeners) {
                        mP3PlayerListener.playbackPlaying(finalThis, frame++);
                    }
                }
            }, 0, 10);
        }
    }

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
}
