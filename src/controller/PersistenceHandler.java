/**
 * 
 * 
 * @author Simon Seyer
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

import controller.player.Playlist;

/**
 * Handles the saving and loading of persistent data.
 *
 */

public class PersistenceHandler {

    //private static final String FILE_PATH = KeyboardHero.class.getResource("/"+mp3Name).toURI();
	private static final File playlistFile = new File(System.getProperty("user.home") + File.separator + "playlist.mpl");
    
    /**
     * Save a playlist to disk.
     */
    public static void savePlaylist(Playlist playlist) {
        try (FileOutputStream fileOut = new FileOutputStream(playlistFile);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(playlist);
            System.out.printf("Serialized data is saved");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Load a playlist form disk.
     *
     * @return the loaded playlist
     */
    public static Playlist loadPlaylist() {
    	Playlist playlist = null;

        try (FileInputStream fileIn = new FileInputStream(playlistFile);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            playlist = (Playlist) in.readObject();
            System.out.println("Playlist: loaded");

            // TODO: implemnt
            // playlist.checkConsistency();
            
        } catch (IOException i) {
            System.out.println("Playlist could not be loaded ");
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Playlist class not found");
            c.printStackTrace();
        }
        
        if (playlist == null) {
        	// TODO load playlist from bundle
        	playlist = new Playlist();
        }

        return playlist;
    }
}

