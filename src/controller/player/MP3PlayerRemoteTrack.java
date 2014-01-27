package controller.player;

import gui.PlayerController;

import java.io.File;
import java.io.IOException;

import model.SoundCloud;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import com.soundcloud.api.Http;
import com.soundcloud.api.Request;

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

	public MP3PlayerRemoteTrack(JSONObject data) {
		title = data.getString("title");
		if (!data.isNull("genre")) {
			albumTitle = "Genre: " + data.getString("genre");
		} else {
			albumTitle = "";
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
				streamUrl = PlayerController.getInstance().getSoundCloud().loadStreamUrl(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return streamUrl;
	}

	@Override
	public String toString() {
		return getArtist() + " - " + getTitle();
	}
}
