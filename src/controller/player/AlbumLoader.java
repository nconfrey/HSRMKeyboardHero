package controller.player;

import de.umass.lastfm.Album;
import de.umass.lastfm.ImageSize;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author privat
 */
public class AlbumLoader {
	// das ist mist
    public static BufferedImage loadCover(MP3PlayerTrack track) {
        /* API Key: 61047b4acdccb090ea7a05ac7e5602f8
           Secret: is 54452eda42c6f50c9305a86573acaafb */
        Album album = Album.getInfo(
                track.getArtist(), track.getAlbumTitle(),
                "61047b4acdccb090ea7a05ac7e5602f8"
        );
        String imageURL = album.getImageURL(ImageSize.MEGA);
        
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
