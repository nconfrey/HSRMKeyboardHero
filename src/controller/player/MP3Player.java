package controller.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;

/**
 *
 * @author sseye001
 */
public class MP3Player implements Runnable {

    private List<Playlist> playlists;
    private Playlist currentPlaylist;
    private int currentTrack;
    private Player player;
    private ArrayList<MP3PlayerListener> listeners;
    private boolean continues;

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

    public void selectTrack(int number) {
        if (number + 1 > this.currentPlaylist.getTrackCount()) {
            return;
        }

        this.currentTrack = number;
    }

    public Track getCurrentTrack() {
        return this.currentPlaylist.getTrack(currentTrack);
    }

    public boolean isContinues() {
        return continues;
    }

    public void setContinues(boolean continues) {
        this.continues = continues;
    }

    public void play() {
        if (player != null) {
            stop();
        }

        File f = getCurrentTrack().getFile();
        try {
            player = new Player(new FileInputStream(f));
            new Thread(this).start();
        } catch (JavaLayerException ex) {
            Logger.getLogger(MP3Player.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MP3Player.class.getName()).log(Level.SEVERE, null, ex);
        }

        firePlaybackStarted();
    }

    @Override
    public void run() {
        if (player != null) {
            try {
                player.play();
            } catch (JavaLayerException ex) {
                Logger.getLogger(MP3Player.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(continues){
                skip();
            }
        }
    }

    public boolean isPlaying() {
        return player != null;
    }

    public void stop() {
        player.close();
        player = null;
        firePlaybackStopped();
    }

    public void skip() {
        this.currentTrack++;
        play();
    }

    public void skipBack() {
        this.currentTrack--;
        play();
    }

    //listener
    public void addListener(MP3PlayerListener listener) {
        listeners.add(listener);
    }

    public void removeListener(MP3PlayerListener listener) {
        listeners.remove(listener);
    }

    private void firePlaybackStarted() {
        for (MP3PlayerListener mP3PlayerListener : listeners) {
            mP3PlayerListener.playbackDidStart(this);
        }
    }

    private void firePlaybackStopped() {
        for (MP3PlayerListener mP3PlayerListener : listeners) {
            mP3PlayerListener.playbackDidStop(this);
        }
    }
}
