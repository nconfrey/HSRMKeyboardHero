/**
 * Interface to listen to scores
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package controller;

import model.StrokeKey;

public interface ScoreListener {

	/**
	 * Scoring did start.
	 * 
	 * @param key the key
	 */
	public void scoringDidStart(StrokeKey key);

	/**
	 * Scoring did end.
	 * 
	 * @param key the key
	 */
	public void scoringDidEnd(StrokeKey key);

}
