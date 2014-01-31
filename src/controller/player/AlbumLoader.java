/**
 * Loads album artworks from last.fm using the last.fm java API
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 **/
package controller.player;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import model.MP3PlayerLocalTrack;
import model.MP3PlayerRemoteTrack;
import model.MP3PlayerTrack;
import model.Track;
import de.umass.lastfm.Album;
import de.umass.lastfm.ImageSize;

public class AlbumLoader {

	private static Map<String, BufferedImage> cache;
	static {
		cache = new HashMap<>();
	}

	/**
	 * Gets the mp3file from the Track object and uses the id3 tags to search in
	 * the last.fm database for the album artwork
	 * 
	 * @param track holds the id3 tags which are needed to find the album
	 *            artwork
	 * @return the album artwork
	 */
	public static BufferedImage loadCover(Track track) {
		/*
		 * API Key: 61047b4acdccb090ea7a05ac7e5602f8 Secret: is
		 * 54452eda42c6f50c9305a86573acaafb
		 */

		MP3PlayerTrack mp3Track = track.getMp3();

		String imageURL = null;
		if (mp3Track instanceof MP3PlayerLocalTrack) {
			Album album = Album.getInfo(mp3Track.getArtist(),
					mp3Track.getAlbumTitle(),
					"61047b4acdccb090ea7a05ac7e5602f8");
			if (album != null) {
				imageURL = album.getImageURL(ImageSize.MEGA);
			}
		} else if (mp3Track instanceof MP3PlayerRemoteTrack) {
			MP3PlayerRemoteTrack remoteTrack = (MP3PlayerRemoteTrack) mp3Track;
			imageURL = remoteTrack.getArtworkUrl();
		}

		BufferedImage image = null;
		if (imageURL != null) {
			if (cache.containsKey(imageURL)) {
				image = cache.get(imageURL);
			} else {
				try {
					URL url = new URL(imageURL);
					image = ImageIO.read(url);
					cache.put(imageURL, image);
				} catch (IOException e) {
				}
			}
		}

		return image;
	}

}
