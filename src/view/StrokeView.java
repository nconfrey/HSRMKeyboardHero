package view;

import java.awt.geom.RoundRectangle2D;

import model.Stroke;

public class StrokeView {

	private Stroke stroke;
	private RoundRectangle2D rect;
	private int style;
	
	
	public StrokeView(Stroke stroke, int style) {
		this.stroke = stroke;
		this.style = style;
	}


	public RoundRectangle2D getRect() {
		return rect;
	}


	public void setRect(RoundRectangle2D rect) {
		this.rect = rect;
	}


	public int getStyle() {
		return style;
	}


	public void setStyle(int style) {
		this.style = style;
	}


	public Stroke getStroke() {
		return stroke;
	}
	
}
