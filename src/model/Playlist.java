/**
 * MP3 Player playlist which saves Track objects in a ArrayList
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 **/
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

public class Playlist extends AbstractListModel<Track> implements Serializable {

	private static final long serialVersionUID = -909941238215193625L;
	private List<Track> tracks;

	/**
	 * Creates an empty playlist and instantiates an empty ArrayList for track
	 * objects.
	 */
	public Playlist() {
		tracks = new ArrayList<>();
	}

	/**
	 * Adds track to the tracklist and fires content change method.
	 * 
	 * @param track will be added to the tracklist
	 */
	public void addTrack(Track track) {
		if (!tracks.contains(track)) {
			this.tracks.add(track);
			fireContentsChanged(track, this.tracks.size() - 1,
					this.tracks.size() - 1);
		}
	}

	/**
	 * Gets the size.
	 * 
	 * @return size of the tracklist
	 */
	@Override
	public int getSize() {
		return tracks.size();
	}

	/**
	 * Gets the tracks.
	 * 
	 * @return whole tracklist
	 */
	public List<Track> getTracks() {
		return tracks;
	}

	/**
	 * Takes a list of tracks and replaces the old tracklist.
	 * 
	 * @param tracks tracklist which replaces the old tracklist
	 */
	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

	/**
	 * Returns a track from tracklist.
	 * 
	 * @param index position of Track object in the playlist
	 * @return track from requested position
	 */
	@Override
	public Track getElementAt(int index) {
		return tracks.get(index);
	}

	/**
	 * Gets the playlist with playable tracks.
	 * 
	 * @return the playlist with playable tracks
	 */
	public Playlist getPlaylistWithPlayableTracks() {
		Playlist gamePlaylist = new Playlist();
		for (Track track : getTracks()) {
			if (track.getStrokeSet() != null) {
				gamePlaylist.addTrack(track);
			}
		}
		return gamePlaylist;
	}

	/**
	 * Check consistency of the tracks.
	 */
	public void checkConsistency() {
		List<Track> toRemove = new ArrayList<>();
		for (Track track : tracks) {
			if (!track.getMp3().isConsistent()) {
				toRemove.add(track);
				System.out.println("Removed track '" + track
						+ "' from playlist"
						+ " because track is not consitent "
						+ "(for example the mp3 "
						+ "file could not exist anymore)");
			}
		}
		tracks.removeAll(toRemove);
	}
}
