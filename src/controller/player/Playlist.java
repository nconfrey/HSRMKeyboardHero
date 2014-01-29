package controller.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.Predicate;
import javax.swing.AbstractListModel;

import model.Track;

/**
 * MP3 Player playlist which saves Track objects in a ArrayList
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 **/
public class Playlist extends AbstractListModel<Track> implements Serializable {

	private List<Track> tracks;

	/**
	 * Creates an empty playlist and instantiates an empty ArrayList for track
	 * objects
	 * 
	 */
	public Playlist() {
		tracks = new ArrayList<>();
	}

	/**
	 * Adds track to the tracklist and fires content change method
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
	 * 
	 * @return size of the tracklist
	 */
	@Override
	public int getSize() {
		return tracks.size();
	}

	/**
	 * 
	 * @return whole tracklist
	 */
	public List<Track> getTracks() {
		return tracks;
	}

	/**
	 * Takes a list of tracks and replaces the old tracklist
	 * 
	 * @param tracks tracklist which replaces the old tracklist
	 */
	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

	/**
	 * Returns a track from tracklist
	 * 
	 * @param index position of Track object in the playlist
	 * @return track from requested position
	 */
	@Override
	public Track getElementAt(int index) {
		return tracks.get(index);
	}
	
	public Playlist getPlaylistWithPlayableTracks() {
		Playlist gamePlaylist = new Playlist();
		for (Track track : getTracks()) {
			if (track.getStrokeSet() != null) {
				gamePlaylist.addTrack(track);
			}
		}
		return gamePlaylist;
	}
}
