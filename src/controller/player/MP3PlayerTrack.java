package controller.player;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;


/**
 *
 * @author sseye001
 */
public class MP3PlayerTrack implements Serializable{

    private long id;
    private String title;
    private int length;
    private String albumTitle;
    private String artist;
    private final File file;

    public MP3PlayerTrack(File file) {
        this.file = file;
        AudioFile f;
        	try {
				f = AudioFileIO.read(file);
				Tag tag = f.getTag();
				AudioHeader header = f.getAudioHeader();
	   			this.artist = tag.getFirst(FieldKey.ARTIST);
	   			this.title = tag.getFirst(FieldKey.TITLE);
	   			this.length = header.getTrackLength();
	   			this.albumTitle = tag.getFirst(FieldKey.ALBUM);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
