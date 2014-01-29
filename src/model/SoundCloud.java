package model;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import com.soundcloud.api.ApiWrapper;
import com.soundcloud.api.Http;
import com.soundcloud.api.Request;

import controller.player.MP3PlayerRemoteTrack;
import controller.player.Playlist;

public class SoundCloud {
	
	private ApiWrapper wrapper;
	private static final String CLIENT_ID = "e2fb08b6ea0356fa18e41a1a5cea0b38";
	private static final String CLIENT_SECRET = "7095d828c8b1fc344361953444fd19cc";

	public SoundCloud() {
		wrapper = new ApiWrapper(CLIENT_ID, CLIENT_SECRET, null, null);
	}
	
	public Playlist search(String searchText) {
		Playlist playlist = new Playlist();
		try {
			HttpResponse searchResp;
			synchronized(wrapper){
				searchResp = wrapper.get(Request.to("/tracks").with("q", searchText).with("license", "cc-by"));
			}
			
			JSONObject json = new JSONObject("{elements:" + Http.getString(searchResp) + "}");
	    	JSONArray elements = json.getJSONArray("elements");
	    	for (int i = 0; i < elements.length(); i++) {
	    		JSONObject track = elements.getJSONObject(i);
	    		if (track.getBoolean("streamable"))  {
	    			playlist.addTrack((new Track(new MP3PlayerRemoteTrack(track, this))));
	    		}
			}
		} catch (IOException e) {
		}
    	
		return playlist;
	}

	public synchronized String loadStreamUrl(String url) throws IOException {
		HttpResponse urlRes;
		synchronized(wrapper){
			urlRes = wrapper.get(Request.to(url).with("allow_redirects", false));
		}
		
		if (urlRes.getStatusLine().getStatusCode() == 404) {
			throw new IOException("Stream not found");
		} else {
			JSONObject urlData = Http.getJSON(urlRes);
			return urlData.getString("location");
		}
	}
	
}
