/**
 * Remote Track referencing a track on soundcloud
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package model;

import helper.KeyboardHeroConstants;

import java.io.IOException;

import org.json.JSONObject;

import controller.SoundCloud;

/**
 * 
 * @author sseye001
 */
public class MP3PlayerRemoteTrack implements MP3PlayerTrack {

	private static final long serialVersionUID = -1307265295302017540L;
	private String title;
	private String albumTitle;
	private String artist;
	private String url;
	private transient String streamUrl;
	private String artworkUrl;

	/**
	 * Instantiates a new m p3 player remote track.
	 * 
	 * @param data the data
	 */
	public MP3PlayerRemoteTrack(JSONObject data) {
		title = data.getString("title");
		if (!data.isNull("genre")) {
			albumTitle = KeyboardHeroConstants.getString("remote_album_prefix")
					+ " " + data.getString("genre");
		} else {
			albumTitle = "";
		}
		if (!data.isNull("artwork_url")) {
			artworkUrl = data.getString("artwork_url");
		}
		artist = data.getJSONObject("user").getString("username");
		url = data.getString("stream_url");
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
	 * Cache.
	 */
	public void cache() {
		getPath();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.player.MP3PlayerTrack#getPath()
	 */
	@Override
	public synchronized String getPath() {
		if (streamUrl == null) {
			try {
				streamUrl = SoundCloud.getInstance().loadStreamUrl(url);
			} catch (IOException e) {
			}
		}
		return streamUrl;
	}

	/**
	 * Gets the artwork url.
	 * 
	 * @return the artwork url
	 */
	public String getArtworkUrl() {
		return artworkUrl;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.MP3PlayerTrack#isConsistent()
	 */
	@Override
	public boolean isConsistent() {
		return true;
	}
}
