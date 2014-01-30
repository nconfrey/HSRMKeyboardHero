/**
 * Listener Inferface for recorded Strokes
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package controller.recorder;

import model.Stroke;

public interface StrokeRecorderListener {

	/**
	 * Redcorder did open stroke.
	 * 
	 * @param recorder the recorder
	 * @param stroke the stroke
	 */
	public void redcorderDidOpenStroke(StrokeRecorder recorder, Stroke stroke);

	/**
	 * Redcorder did close stroke.
	 * 
	 * @param recorder the recorder
	 * @param stroke the stroke
	 */
	public void redcorderDidCloseStroke(StrokeRecorder recorder, Stroke stroke);
}
