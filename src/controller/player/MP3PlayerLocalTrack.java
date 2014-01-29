package controller.player;

import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

public class MP3PlayerLocalTrack implements MP3PlayerTrack {

	private String title;
	private int length;
	private String albumTitle;
	private String artist;
	private final File file;

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

	@Override
	public String getTitle() {
		return title;
	}

	public int getLength() {
		return length;
	}

	@Override
	public String getAlbumTitle() {
		return albumTitle;
	}

	@Override
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

	private String extractTitleFromFileName(String fileName) {
		return fileName.substring(fileName.lastIndexOf('/') + 1).replace(
				".mp3", "");
	}

	@Override
	public String getPath() {
		return file.getAbsolutePath();
	}
}
