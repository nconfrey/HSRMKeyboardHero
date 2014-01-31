/**
 * Local Track Model referencing a mp3 File
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package model;

import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

public class MP3PlayerLocalTrack implements MP3PlayerTrack {

	private static final long serialVersionUID = 1990723577239567262L;
	private String title;
	private int length;
	private String albumTitle;
	private String artist;
	private final File file;

	/**
	 * Instantiates a new m p3 player local track.
	 * 
	 * @param file the file
	 */
	public MP3PlayerLocalTrack(File file) {
		this.file = file;
		AudioFile f;
		try {
			f = AudioFileIO.read(file);
			Tag tag = f.getTag();
			AudioHeader header = f.getAudioHeader();
			this.length = header.getTrackLength();

			if (tag != null) {
				this.artist = tag.getFirst(FieldKey.ARTIST);
				this.title = tag.getFirst(FieldKey.TITLE);
				this.albumTitle = tag.getFirst(FieldKey.ALBUM);

				if (this.title.trim().length() <= 0) {
					this.title = extractTitleFromFileName(file.toString());
				}

			} else {
				this.title = extractTitleFromFileName(file.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the title.
	 * 
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Sets the length.
	 * 
	 * @param length the new length
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Sets the album title.
	 * 
	 * @param albumTitle the new album title
	 */
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}

	/**
	 * Sets the artist.
	 * 
	 * @param artist the new artist
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.player.MP3PlayerTrack#getTitle()
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the length.
	 * 
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.player.MP3PlayerTrack#getAlbumTitle()
	 */
	@Override
	public String getAlbumTitle() {
		return albumTitle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.player.MP3PlayerTrack#getArtist()
	 */
	@Override
	public String getArtist() {
		return artist;
	}

	/**
	 * Gets the file.
	 * 
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getArtist() + " - " + getTitle();
	}

	/**
	 * Extract title from file name.
	 * 
	 * @param fileName the file name
	 * @return the string
	 */
	private String extractTitleFromFileName(String fileName) {
		return fileName.substring(fileName.lastIndexOf('/') + 1).replace(
				".mp3", "");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.player.MP3PlayerTrack#getPath()
	 */
	@Override
	public String getPath() {
		return file.getAbsolutePath();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.MP3PlayerTrack#isConsistent()
	 */
	@Override
	public boolean isConsistent() {
		return file.exists();
	}
}
