/**
 * The base frame where the whole gamecontent is build in
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 **/
package view;

import helper.KeyboardHeroConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.KeyboardHeroPreferences;
import controller.NavigationController;

public class BaseFrame extends JFrame {

	public NavigationController navigationController;
	private KeyboardHeroPreferences prefs;

	/**
	 * Create, initialize and show the base frame. Gets its size from a
	 * preference file
	 */
	public BaseFrame() {
		prefs = new KeyboardHeroPreferences();
		prefs.setPreferences();
		Dimension frameSize = new Dimension(prefs.getScreenWidth(),
				prefs.getScreenHeight());
		setLayout(new BorderLayout());

		this.setMinimumSize(new Dimension(1024, 700));
		this.setSize(frameSize);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				prefs.setScreenSize(getWidth(), getHeight());
			}
		});

		WindowListener exitListener = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		this.addWindowListener(exitListener);

		this.setTitle(KeyboardHeroConstants.getString("game_title"));
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
	public void display(final JComponent component) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				getContentPane().add(component, BorderLayout.CENTER);
				getContentPane().revalidate();
				getContentPane().repaint();

			}
		});
	}

	/**
	 * Hide a component and remove it from the view.
	 * 
	 * @param component the component
	 */
	public void hide(final JComponent component) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				getContentPane().remove(component);
			}
		});
	}

}
