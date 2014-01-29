package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.KeyboardHeroPreferences;

/**
 * The base frame where the whole gamecontent is build in
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 **/
public class BaseFrame extends JFrame {

	private Dimension frameSize;
	public NavigationController navigationController;
	private KeyboardHeroPreferences prefs;

	/**
	 * Create, initialize and show the base frame. Gets its size from a
	 * preference file
	 */
	public BaseFrame() {
		prefs = new KeyboardHeroPreferences();
		prefs.setPreferences();
		frameSize = new Dimension(prefs.getScreenWidth(),
				prefs.getScreenHeight());
		setLayout(new BorderLayout());
		this.setSize(frameSize);
		WindowListener exitListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				prefs.setScreenSize(getWidth(), getHeight());
				System.exit(0);
			}
		};
		this.addWindowListener(exitListener);
		this.setTitle("Keyboard Hero");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * Takes an JComponent and adds it to the view. After that the GUI will be
	 * revalidated and repainted
	 * 
	 * @param component the jcomponent which will be added to the frame.
	 */
	public void display(JComponent component) {
		getContentPane().add(component, BorderLayout.CENTER);
		getContentPane().revalidate();
		getContentPane().repaint();
	}

}
