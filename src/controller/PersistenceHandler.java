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

import model.Playlist;

import org.apache.commons.io.FileUtils;

/**
 * Handles the saving and loading of persistent data.
 * 
 */
public class PersistenceHandler {

	private static final String FILE_FOLDER = System.getProperty("user.home")
			+ File.separator + ".keyboardHero";
	private static final String PLAYLIST_FILE_NAME = "playlist.mpl";
	private static final String[] filesToCopy = new String[] { "playlist.mpl",
			"smoke_on_the_water_short.mp3",
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
	 * @param name
	 *            the name
	 * @return the local file
	 */
	public static File getLocalFile(String name) {
		return new File(FILE_FOLDER + File.separator + name);
	}
}
