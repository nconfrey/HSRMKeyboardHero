package controller.player;

import java.io.File;

/**
 *
 * @author sseye001
 */
public class Track {

    private long id;
    private String title;
    private int length;
    private String albumTitle;
    private String artist;
    private final File file;

    public Track(File file) {
        this.file = file;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getLength() {
        return length;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public String getArtist() {
        return artist;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return getArtist() + " - " + getTitle();
    }
}
