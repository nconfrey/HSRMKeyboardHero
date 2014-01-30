/**
 * Imports Files to Track Model
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package controller.player;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.MP3PlayerLocalTrack;
import model.MP3PlayerTrack;
import model.Playlist;

import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.ID3v1Tag;

public class TrackImporter {

	private File rootPath;
	private Playlist playlist;

	/*
	 * MP3File f = (Mp3File)AudioFileIO.read(testFile); Tag tag = f.getTag();
	 * ID3v1Tag v1Tag = (ID3v1Tag)tag; AbstractID3v2Tag v2tag = f.getID3v2Tag()
	 * ID3v24Tag v24tag = (AbstractID3v2Tag)f.getID3v2TagAsv24();
	 */
	/**
	 * Instantiates a new track importer.
	 * 
	 * @param path the path
	 * @param playlist the playlist
	 */
	public TrackImporter(File path, Playlist playlist) {
		this.rootPath = path;
		this.playlist = playlist;
	}

	/**
	 * Import tracks.
	 */
	public void importTracks() {
		importTrack(rootPath);
	}

	/**
	 * Import tracks.
	 * 
	 * @param path the path
	 */
	private void importTracks(File path) {
		for (File file : path.listFiles()) {
			importTrack(file);
		}
	}

	/**
	 * Import track.
	 * 
	 * @param file the file
	 */
	private void importTrack(File file) {
		if (file.isDirectory()) {
			importTracks(file);
		} else {
			if (file.getName().endsWith(".mp3")
					|| file.getName().endsWith(".MP3")) {
				MP3PlayerTrack track = loadTrack(file);
				// playlist.addTrack(track);
			}
		}
	}

	/**
	 * Load track.
	 * 
	 * @param file the file
	 * @return the mp3 player track
	 */
	private MP3PlayerTrack loadTrack(File file) {
		MP3PlayerLocalTrack track = new MP3PlayerLocalTrack(file);
		try {
			MP3File mp3File = new MP3File(file);

			MP3AudioHeader header = mp3File.getMP3AudioHeader();
			track.setLength(header.getTrackLength());

			if (mp3File.hasID3v1Tag()) {
				ID3v1Tag tagv1 = mp3File.getID3v1Tag();

				track.setAlbumTitle(tagv1.getFirstAlbum());
				track.setArtist(tagv1.getFirstArtist());
				track.setTitle(tagv1.getFirstTitle());
			}
		} catch (IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException ex) {
			Logger.getLogger(TrackImporter.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		return track;
	}
}
