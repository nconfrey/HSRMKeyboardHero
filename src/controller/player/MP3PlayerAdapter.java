/**
 * Implements all methods (empty) from the MP3PlayerListener
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 **/
package controller.player;

public class MP3PlayerAdapter implements MP3PlayerListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.player.MP3PlayerListener#playbackDidStart(controller.player
	 * .MP3Player)
	 */
	@Override
	public void playbackDidStart(MP3Player player) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.player.MP3PlayerListener#playbackDidStop(controller.player
	 * .MP3Player)
	 */
	@Override
	public void playbackDidStop(MP3Player player) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.player.MP3PlayerListener#playbackPlaying(controller.player
	 * .MP3Player, int)
	 */
	@Override
	public void playbackPlaying(MP3Player player, int frame) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.player.MP3PlayerListener#playbackDidFail()
	 */
	@Override
	public void playbackDidFail(final MP3Player player) {
	}
}
