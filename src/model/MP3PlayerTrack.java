/**
 * 
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package model;

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
	 * Gets the title.
	 * 
	 * @return the title of the mp3 track
	 */
	public String getTitle();

	/**
	 * Gets the album title.
	 * 
	 * @return the album title of the mp3 track
	 */
	public String getAlbumTitle();

	/**
	 * Gets the artist.
	 * 
	 * @return the artist of the mp3 track
	 */
	public String getArtist();

	/**
	 * Gets the path.
	 * 
	 * @return the path of the mp3 file
	 */
	public String getPath();
}
