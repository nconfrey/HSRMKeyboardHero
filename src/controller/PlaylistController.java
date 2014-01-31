/**
 * Holds a playlist, handles loading and saving of it and 
 * delegates searches to soundCloud
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package controller;

import model.Playlist;

public class PlaylistController {

	/** The playlist. */
	private Playlist playlist;
	private PersistenceHandler persistenceHandler;

	/** The sound cloud. */
	private SoundCloud soundCloud;

	/**
	 * Instantiates a new playlist controller.
	 */
	public PlaylistController() {
		persistenceHandler = new PersistenceHandler();
		playlist = persistenceHandler.loadPlaylist();
		soundCloud = new SoundCloud();
	}

	/**
	 * Gets the playlist.
	 * 
	 * @return the playlist
	 */
	public Playlist getPlaylist() {
		return playlist;
	}

	/**
	 * Gets the playlist.
	 * 
	 * @param playable the playable
	 * @return the playlist
	 */
	public Playlist getPlaylist(boolean playable) {
		if (playable) {
			return playlist.getPlaylistWithPlayableTracks();
		}
		return playlist;
	}

	/**
	 * Gets the playlist for search.
	 * 
	 * @param searchString the search string
	 * @return the playlist for search
	 */
	public Playlist getPlaylistForSearch(String searchString) {
		return soundCloud.search(searchString);
	}

	/**
	 * Save.
	 */
	public void save() {
		persistenceHandler.savePlaylist(playlist);
	}

}
