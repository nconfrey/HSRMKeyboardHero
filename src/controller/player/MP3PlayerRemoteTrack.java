package controller.player;

import java.io.IOException;

import model.SoundCloud;

import org.json.JSONObject;

import view.KeyboardHeroConstants;

/**
 * 
 * @author sseye001
 */
public class MP3PlayerRemoteTrack implements MP3PlayerTrack {

	private String title;
	private String albumTitle;
	private String artist;
	private String url;
	private transient String streamUrl;
	private transient SoundCloud soundCloud;
	private String artworkUrl;
	

	public MP3PlayerRemoteTrack(JSONObject data, SoundCloud soundCloud) {
		this.soundCloud = soundCloud;
		title = data.getString("title");
		if (!data.isNull("genre")) {
			albumTitle = KeyboardHeroConstants.getString("remote_album_prefix") + " " + data.getString("genre");
		} else {
			albumTitle = "";
		}
		if (!data.isNull("artwork_url")) {
			artworkUrl = data.getString("artwork_url");
		}
		artist = data.getJSONObject("user").getString("username");
		url = data.getString("stream_url");
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getAlbumTitle() {
		return albumTitle;
	}

	@Override
	public String getArtist() {
		return artist;
	}
	
	public void cache() {
		getPath();
	}

	@Override
	public synchronized String getPath() {
		if (streamUrl == null) {
			try {
				streamUrl = soundCloud.loadStreamUrl(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return streamUrl;
	}
	
	public String getArtworkUrl() {
		return artworkUrl;
	}

	@Override
	public String toString() {
		return getArtist() + " - " + getTitle();
	}
}
