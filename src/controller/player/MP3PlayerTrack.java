package controller.player;

import java.io.Serializable;

/**
 * Interface for MP3 tracks
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 **/
public interface MP3PlayerTrack extends Serializable {
	/**
	 * 
	 * @return the title of the mp3 track
	 */
	public String getTitle();

	/**
	 * 
	 * @return the album title of the mp3 track
	 */
	public String getAlbumTitle();

	/**
	 * 
	 * @return the artist of the mp3 track
	 */
	public String getArtist();

	/**
	 * 
	 * @return the path of the mp3 file
	 */
	public String getPath();
}
