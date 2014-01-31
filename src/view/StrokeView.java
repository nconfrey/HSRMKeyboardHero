/**
 * a view that holds a single stroke for displaying
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package view;

import java.awt.geom.RoundRectangle2D;

import model.Stroke;

public class StrokeView {

	private Stroke stroke;
	private RoundRectangle2D rect;
	private int style;

	/**
	 * Instantiates a new stroke view.
	 * 
	 * @param stroke the stroke
	 * @param style the style
	 */
	public StrokeView(Stroke stroke, int style) {
		this.stroke = stroke;
		this.style = style;
	}

	/**
	 * Gets the rect.
	 * 
	 * @return the rect
	 */
	public RoundRectangle2D getRect() {
		return rect;
	}

	/**
	 * Sets the rect.
	 * 
	 * @param rect the new rect
	 */
	public void setRect(RoundRectangle2D rect) {
		this.rect = rect;
	}

	/**
	 * Gets the style.
	 * 
	 * @return the style
	 */
	public int getStyle() {
		return style;
	}

	/**
	 * Sets the style.
	 * 
	 * @param style the new style
	 */
	public void setStyle(int style) {
		this.style = style;
	}

	/**
	 * Gets the stroke.
	 * 
	 * @return the stroke
	 */
	public Stroke getStroke() {
		return stroke;
	}

}
