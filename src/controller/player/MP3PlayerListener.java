/**
 * Listener Interface for MP3Player
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package controller.player;

public interface MP3PlayerListener {

	/**
	 * Playback did start.
	 * 
	 * @param player the player
	 */
	public void playbackDidStart(final MP3Player player);

	/**
	 * Playback did stop.
	 * 
	 * @param player the player
	 */
	public void playbackDidStop(final MP3Player player);

	/**
	 * Playback playing.
	 * 
	 * @param player the player
	 * @param frame the frame
	 */
	public void playbackPlaying(final MP3Player player, final int frame);

	/**
	 * Playback did fail.
	 * 
	 * @param player the player
	 */
	public void playbackDidFail(final MP3Player player);

}
