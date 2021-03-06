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

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import controller.recorder.GuitarStringListener;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import model.StrokeKey;

public class KeyController implements SerialPortEventListener {

	private ArrayList<GuitarStringListener> guitarStringListener;
	private boolean isEnterPressed;
	SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
			"/dev/cu.usbmodem1411",
			//"/dev/tty.usbserial-A9007UX1", // Mac OS X
//			"/dev/tty.usbserial-AH03F6SU", // Mac OS X EL Sequencer? 
			"/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM3", // Windows
	};
	/**
	 * A BufferedReader which will be fed by a InputStreamReader 
	 * converting the bytes into characters 
	 * making the displayed results codepage independent
	 */
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	/**
	 * Instantiates a new key controller.
	 */
	public KeyController() {
		guitarStringListener = new ArrayList<>();

		isEnterPressed = false;
		//		KeyboardFocusManager manager = KeyboardFocusManager
		//				.getCurrentKeyboardFocusManager();
		//		manager.addKeyEventDispatcher(this);

		// the next line is for Raspberry Pi and 
		// gets us into the while loop and was suggested here was suggested https://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
		// System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");

		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}

	}
	
	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine=input.readLine();
				String[] tokedInput = inputLine.split(" ");
				String pinTouched = tokedInput[0];
				String toggled = tokedInput[1];
				if (toggled.equals("on")) {
					touchBoardPressed(pinTouched);
				} else if (toggled.equals("off")) {
					touchBoardReleased(pinTouched);
				} else {
					System.out.println("wtf got a wierd serial thing");
				}
				System.out.println(inputLine);
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.KeyEventDispatcher#dispatchKeyEvent(java.awt.event.KeyEvent)
	 */
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			keyPressed(e);
		} else if (e.getID() == KeyEvent.KEY_RELEASED) {
			keyReleased(e);
		}
		return false;
	}
	
	public void touchBoardPressed(String pin) {
		StrokeKey strokeKey = StrokeKey.keyForSerialCode(pin);
		for (GuitarStringListener guitarStringListener : this.guitarStringListener) {
			guitarStringListener.guitarStringPressed(strokeKey);
		}
	}
	
	public void touchBoardReleased(String pin) {
		StrokeKey strokeKey = StrokeKey.keyForSerialCode(pin);
		for (GuitarStringListener guitarStringListener : this.guitarStringListener) {
			guitarStringListener.guitarStringReleased(strokeKey);
		}
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
