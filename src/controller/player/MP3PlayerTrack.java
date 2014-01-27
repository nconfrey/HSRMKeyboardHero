package controller.player;

import java.io.Serializable;

/**
 * 
 * @author sseye001
 */
public interface MP3PlayerTrack extends Serializable {

	public String getTitle();

	public String getAlbumTitle();

	public String getArtist();

	public String getPath();
}
