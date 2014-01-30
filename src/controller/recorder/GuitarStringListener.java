/**
 * Listener Interface for GuitarString Keys
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package controller.recorder;

import model.StrokeKey;

public interface GuitarStringListener {

	/**
	 * Guitar string pressed.
	 * 
	 * @param strokeKey the stroke key
	 */
	public void guitarStringPressed(StrokeKey strokeKey);

	/**
	 * Guitar string released.
	 * 
	 * @param strokeKey the stroke key
	 */
	public void guitarStringReleased(StrokeKey strokeKey);

	/**
	 * Guitar stroke pressed.
	 * 
	 * @param strokeKey the stroke key
	 */
	public void guitarStrokePressed(StrokeKey strokeKey);

	/**
	 * Guitar stroke released.
	 * 
	 * @param strokeKey the stroke key
	 */
	public void guitarStrokeReleased(StrokeKey strokeKey);

}