/**
 * an abstract panel that needs to be subclassed if you want to push a panel to the NavigationController
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package view;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import controller.NavigationController;

/**
 * Standard panel used for the different views ingame
 * 
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
public abstract class GHPanel extends JPanel {
	private NavigationController navigationController;

	/**
	 * Gets the navigation controller.
	 * 
	 * @return navigationcontroller to switch views
	 */
	public NavigationController getNavigationController() {
		return navigationController;
	}

	/**
	 * Sets the navigation controller.
	 * 
	 * @param navigationController controller to switch between different views
	 */
	public void setNavigationController(
			NavigationController navigationController) {
		this.navigationController = navigationController;
	}

	/**
	 * Panel will appear.
	 */
	public void panelWillAppear() {
		// Stub implementation
	}

	/**
	 * Panel will disappear.
	 */
	public void panelWillDisappear() {
		// Stub implementation
	}

	/**
	 * Did press back.
	 * 
	 * @param e the e
	 */
	public void didPressBack(KeyEvent e) {
		// Stub implementation
	}

}
