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
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;

/**
 *
 * @author sseye001
 */
public class MP3Player {

    private List<Playlist> playlists;
    private Playlist currentPlaylist;
    private int currentTrack;
    private Player player;
    private ArrayList<MP3PlayerListener> listeners;
    private boolean continues;
    private Timer frameTimer;
    private boolean playing;

    public MP3Player() {
        playlists = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    public Playlist createPlayList(String name) {
        Playlist newPlaylist = new Playlist(name);
        playlists.add(newPlaylist);
        return newPlaylist;
    }

    public void selectPlaylist(int number) {
        if (number + 1 > playlists.size()) {
            return;
        }

        this.currentPlaylist = this.playlists.get(number);
        this.currentTrack = 0;
    }

    public boolean selectTrack(int number) {
        if (number < 0) {
            return false;
        }
        if (number + 1 > this.currentPlaylist.getTrackCount()) {
            return false;
        }

        this.currentTrack = number;
        return true;
    }

    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    public MP3PlayerTrack getCurrentTrack() {
        if (!isPlaying()) {
            return null;
        }
        return this.currentPlaylist.getTrack(currentTrack);
    }

    public void importTracksToCurrentPlaylist(final List<File> files) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (File file : files) {
                    TrackImporter importer = new TrackImporter(file, getCurrentPlaylist());
                    importer.importTracks();
                }
            }
        }).start();

    }

    public boolean isContinues() {
        return continues;
    }

    public void setContinues(boolean continues) {
        this.continues = continues;
    }

    public synchronized void play() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                if (isPlaying()) {
                    stopAndWait();
                }

                MP3PlayerTrack t = currentPlaylist.getTrack(currentTrack);
                if (t != null) {
                    File f = t.getFile();
                    try {
                        player = new Player(new FileInputStream(f));

                        firePlaybackStarted();
                        try {
                            player.play();
                        } catch (JavaLayerException ex) {
                            Logger.getLogger(MP3Player.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        if (continues) {
                            skip();
                        }
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
        player.close();
        player = null;
        firePlaybackStopped();
    }

    public synchronized void stop() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                stopAndWait();
            }
        }).start();
    }

    public synchronized void skip() {
        if (selectTrack(this.currentTrack + 1)) {
            play();
        } else {
            stop();
        }
    }

    public synchronized void skipBack() {
        if (selectTrack(this.currentTrack - 1)) {
            play();
        } else {
            stop();
        }
    }

    public void play(int song) {
        if (song >= 0 && song < this.currentPlaylist.getSize()) {
            this.currentTrack = song;
            play();
        }
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
                        mP3PlayerListener.playbackPlaying(finalThis, player.getPosition());
                    }
                }
            }, 0, 100);
        }
    }

    private void firePlaybackStopped() {
        if (isPlaying()) {
            playing = false;
            frameTimer.cancel();
            for (MP3PlayerListener mP3PlayerListener : listeners) {
                mP3PlayerListener.playbackDidStop(this);
            }
        }
    }
}
