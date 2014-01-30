/**
 * Handles the saving and loading of persistent data.
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
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream.GetField;

import model.Playlist;

/**
 * Handles the saving and loading of persistent data.
 *
 */

public class PersistenceHandler {

	private static final String PLAYLIST_PATH = System.getProperty("user.home") + File.separator + "playlist.mpl";
	private static final String DEFAULT_PLAYLIST_PATH = "playlist.mpl";
	
    /**
     * Save a playlist to disk.
     */
    public static void savePlaylist(Playlist playlist) {
        try (FileOutputStream fileOut = new FileOutputStream(new File(PLAYLIST_PATH));
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

        try (FileInputStream fileIn = new FileInputStream(new File(PLAYLIST_PATH));
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
        	try(InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(DEFAULT_PLAYLIST_PATH);
        			ObjectInputStream in = new ObjectInputStream(stream)) {
        		playlist = (Playlist) in.readObject();
        	} catch (IOException e) {
        		System.out.println("Default playlist could not be loaded");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("Playlist class not found");
				e.printStackTrace();
			}
        }
        
        if (playlist == null) {
        	playlist = new Playlist();
        }

        return playlist;
    }
}
