package controller.player;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import model.Track;
import de.umass.lastfm.Album;
import de.umass.lastfm.ImageSize;

/**
 * 
 * @author privat
 */
public class AlbumLoader {

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
			imageURL = album.getImageURL(ImageSize.MEGA);
		} else if (mp3Track instanceof MP3PlayerRemoteTrack) {
			MP3PlayerRemoteTrack remoteTrack = (MP3PlayerRemoteTrack) mp3Track;
			imageURL = remoteTrack.getArtworkUrl();
		}

		BufferedImage image = null;
		if (imageURL != null) {
			try {
				URL url = new URL(imageURL);
				image = ImageIO.read(url);
			} catch (IOException e) {
			}
		}

		return image;
	}

}
