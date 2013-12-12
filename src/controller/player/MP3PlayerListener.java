package model;

/**
 *
 * @author sseye001
 */
public interface MP3PlayerListener {
    
    public void playbackDidStart(final MP3Player player);
    public void playbackDidStop(final MP3Player player);
    public void playbackPlaying(final MP3Player player, final int frame);
    
}
