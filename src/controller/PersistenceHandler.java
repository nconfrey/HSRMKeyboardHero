/**
 * Handles the saving and loading of persistent data.
 * 
++3 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import model.MP3PlayerLocalTrack;
import model.Playlist;
import model.Track;

import org.apache.commons.io.FileUtils;

/**
 * Handles the saving and loading of persistent data.
 * 
 */
public class PersistenceHandler {

	private static final String LOCAL_FOLDER_NAME = ".keyboardHero";
	private static final String FILE_FOLDER = System.getProperty("user.home")
			+ File.separator + LOCAL_FOLDER_NAME;
	private static final String PLAYLIST_FILE_NAME = "playlist.mpl";
	private static final String[] filesToCopy = new String[] { "playlist.mpl",
			"highway_to_hell.mp3", "smoke_on_the_water.mp3",
			"back_in_black.mp3" };

	/**
	 * Instantiates a new persistence handler.
	 */
	public PersistenceHandler() {
		copyBundleFilesToSystemIfNeeded();
	}

	/**
	 * Copy bundle files to system if needed.
	 */
	public void copyBundleFilesToSystemIfNeeded() {
		File rootFolder = new File(FILE_FOLDER);
		if (!rootFolder.exists()) {
			rootFolder.mkdir();
			for (String filePath : filesToCopy) {
				URL inputUrl = ClassLoader.getSystemClassLoader().getResource(
						filePath);
				File dest = new File(FILE_FOLDER + File.separator + filePath);
				try {
					FileUtils.copyURLToFile(inputUrl, dest);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Save a playlist to disk.
	 * 
	 * @param playlist the playlist
	 */
	public void savePlaylist(Playlist playlist) {
		try (FileOutputStream fileOut = new FileOutputStream(
				getLocalFile(PLAYLIST_FILE_NAME));
				ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(playlist);
			System.out.printf("Serialized data is saved");
		} catch (IOException i) {
			System.out.println("Playlist class not be saved");
		}
	}

	/**
	 * Load a playlist form disk.
	 * 
	 * @return the loaded playlist
	 */
	public Playlist loadPlaylist() {
		Playlist playlist = null;

		try (FileInputStream fileIn = new FileInputStream(
				getLocalFile(PLAYLIST_FILE_NAME));
				ObjectInputStream in = new ObjectInputStream(fileIn)) {
			playlist = (Playlist) in.readObject();
			System.out.println("Playlist: loaded");

			/*
			 * If the file does not exist and the path contains the
			 * .keyboardHero folder, try to get the name of the file and search
			 * in the current .keyboardHero folder (in the user directory), if
			 * the file exists there
			 */
			for (Track track : playlist.getTracks()) {
				String path = track.getMp3().getPath();
				if (track.getMp3() instanceof MP3PlayerLocalTrack
						&& !track.getMp3().isConsistent()
						&& path.contains(LOCAL_FOLDER_NAME)) {
					MP3PlayerLocalTrack localTrack = (MP3PlayerLocalTrack) track
							.getMp3();
					File file = new File(FILE_FOLDER + File.separator
							+ localTrack.getFile().getName());
					if (file.exists()) {
						track.setMp3(new MP3PlayerLocalTrack(file));
					}
				}
			}
			playlist.checkConsistency();
		} catch (IOException i) {
			System.out.println("Playlist could not be loaded ");
		} catch (ClassNotFoundException c) {
			System.out.println("Playlist class not found");
		}

		if (playlist == null) {
			playlist = new Playlist();
		}

		return playlist;
	}

	/**
	 * Gets the local file.
	 * 
	 * @param name the name
	 * @return the local file
	 */
	public static File getLocalFile(String name) {
		return new File(FILE_FOLDER + File.separator + name);
	}
}
