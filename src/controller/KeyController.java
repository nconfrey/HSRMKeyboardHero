package controller;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import controller.recorder.GuitarStringListener;
import model.StrokeKey;

public class KeyController implements KeyEventDispatcher {

	private ArrayList<GuitarStringListener> guitarStringListener;
	private boolean isEnterPressed;

	public KeyController() {
		guitarStringListener = new ArrayList<>();

		isEnterPressed = false;
		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(this);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			keyPressed(e);
		} else if(e.getID() == KeyEvent.KEY_RELEASED) {
			keyReleased(e);
		}
		return false;
	}
	
	public void keyPressed(KeyEvent e) {
		StrokeKey strokeKey = StrokeKey.keyForCode(e.getKeyCode());
		if(strokeKey != StrokeKey.INVALID && strokeKey != StrokeKey.ENTER) {
			for (GuitarStringListener guitarStringListener : this.guitarStringListener) {
				guitarStringListener.guitarStringPressed(strokeKey);
	        }
		} else if(strokeKey == StrokeKey.ENTER) {
			if (!isEnterPressed) {
				isEnterPressed = true;
				for (GuitarStringListener guitarStringListener : this.guitarStringListener) {
					guitarStringListener.guitarStrokePressed(strokeKey);
		        }
			}
		}
    }
	
    public void keyReleased(KeyEvent e) {
    	StrokeKey strokeKey = StrokeKey.keyForCode(e.getKeyCode());
		if(strokeKey != StrokeKey.INVALID && strokeKey != StrokeKey.ENTER) {
			
			for (GuitarStringListener guitarStringListener : this.guitarStringListener) {
				guitarStringListener.guitarStringReleased(strokeKey);
	        }
		} else if(strokeKey == StrokeKey.ENTER) {
			isEnterPressed = false;
			for (GuitarStringListener guitarStringListener : this.guitarStringListener) {
				guitarStringListener.guitarStrokeReleased(strokeKey);
	        }
		}
    }

	public void addGuitarStringListener(GuitarStringListener listener) {
		this.guitarStringListener.add(listener);
	}

	public void removeGuitarStringListener(GuitarStringListener listener) {
		this.guitarStringListener.remove(listener);
	}
}
