/**
 * 
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package controller.player;

/**
 *
 * @author sseye001
 */
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
    
}
