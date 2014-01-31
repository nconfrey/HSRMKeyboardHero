/**
 * Searching on SoundCloud
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package controller;

import java.io.IOException;

import model.MP3PlayerRemoteTrack;
import model.Playlist;
import model.Track;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import com.soundcloud.api.ApiWrapper;
import com.soundcloud.api.Http;
import com.soundcloud.api.Request;

public class SoundCloud {

	private ApiWrapper wrapper;
	private static final String CLIENT_ID = "e2fb08b6ea0356fa18e41a1a5cea0b38";
	private static final String CLIENT_SECRET = "7095d828c8b1fc344361953444fd19cc";
	private static SoundCloud instance;

	/**
	 * Instantiates a new sound cloud.
	 */
	public SoundCloud() {
		wrapper = new ApiWrapper(CLIENT_ID, CLIENT_SECRET, null, null);
	}

	/**
	 * Gets the single instance of SoundCloud.
	 * 
	 * @return single instance of SoundCloud
	 */
	public static SoundCloud getInstance() {
		if (instance == null) {
			instance = new SoundCloud();
		}
		return instance;
	}

	/**
	 * Search.
	 * 
	 * @param searchText the search text
	 * @return the playlist
	 */
	public Playlist search(String searchText) {
		Playlist playlist = new Playlist();
		try {
			HttpResponse searchResp;
			synchronized (wrapper) {
				searchResp = wrapper.get(Request.to("/tracks")
						.with("q", searchText).with("license", "cc-by"));
			}

			JSONObject json = new JSONObject("{elements:"
					+ Http.getString(searchResp) + "}");
			JSONArray elements = json.getJSONArray("elements");
			for (int i = 0; i < elements.length(); i++) {
				JSONObject track = elements.getJSONObject(i);
				if (track.getBoolean("streamable")) {
					playlist.addTrack((new Track(
							new MP3PlayerRemoteTrack(track))));
				}
			}
		} catch (IOException e) {
		}

		return playlist;
	}

	/**
	 * Load stream url.
	 * 
	 * @param url the url
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public synchronized String loadStreamUrl(String url) throws IOException {
		HttpResponse urlRes;
		synchronized (wrapper) {
			urlRes = wrapper
					.get(Request.to(url).with("allow_redirects", false));
		}

		if (urlRes.getStatusLine().getStatusCode() == 404) {
			throw new IOException("Stream not found");
		} else {
			JSONObject urlData = Http.getJSON(urlRes);
			return urlData.getString("location");
		}
	}

}
