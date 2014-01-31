/**
 * Handles Key Events suited to our Keyset
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package controller;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import model.StrokeKey;
import controller.recorder.GuitarStringListener;

public class KeyController implements KeyEventDispatcher {

	private ArrayList<GuitarStringListener> guitarStringListener;
	private boolean isEnterPressed;

	/**
	 * Instantiates a new key controller.
	 */
	public KeyController() {
		guitarStringListener = new ArrayList<>();

		isEnterPressed = false;
		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.KeyEventDispatcher#dispatchKeyEvent(java.awt.event.KeyEvent)
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			keyPressed(e);
		} else if (e.getID() == KeyEvent.KEY_RELEASED) {
			keyReleased(e);
		}
		return false;
	}

	/**
	 * Key pressed event.
	 * 
	 * @param e the KeyEvent
	 */
	public void keyPressed(KeyEvent e) {
		StrokeKey strokeKey = StrokeKey.keyForCode(e.getKeyCode());
		if (strokeKey != StrokeKey.INVALID && strokeKey != StrokeKey.ENTER) {
			for (GuitarStringListener guitarStringListener : this.guitarStringListener) {
				guitarStringListener.guitarStringPressed(strokeKey);
			}
		} else if (strokeKey == StrokeKey.ENTER) {
			if (!isEnterPressed) {
				isEnterPressed = true;
				for (GuitarStringListener guitarStringListener : this.guitarStringListener) {
					guitarStringListener.guitarStrokePressed(strokeKey);
				}
			}
		}
	}

	/**
	 * Key released event.
	 * 
	 * @param e the KeyEvent
	 */
	public void keyReleased(KeyEvent e) {
		StrokeKey strokeKey = StrokeKey.keyForCode(e.getKeyCode());
		if (strokeKey != StrokeKey.INVALID && strokeKey != StrokeKey.ENTER) {

			for (GuitarStringListener guitarStringListener : this.guitarStringListener) {
				guitarStringListener.guitarStringReleased(strokeKey);
			}
		} else if (strokeKey == StrokeKey.ENTER) {
			isEnterPressed = false;
			for (GuitarStringListener guitarStringListener : this.guitarStringListener) {
				guitarStringListener.guitarStrokeReleased(strokeKey);
			}
		}
	}

	/**
	 * Adds the guitar string listener.
	 * 
	 * @param listener the listener
	 */
	public void addGuitarStringListener(GuitarStringListener listener) {
		this.guitarStringListener.add(listener);
	}

	/**
	 * Removes the guitar string listener.
	 * 
	 * @param listener the listener
	 */
	public void removeGuitarStringListener(GuitarStringListener listener) {
		this.guitarStringListener.remove(listener);
	}
}
