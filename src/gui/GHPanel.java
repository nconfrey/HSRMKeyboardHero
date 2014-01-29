package gui;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;

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
	 * 
	 * @return navigationcontroller to switch views
	 */
	public NavigationController getNavigationController() {
		return navigationController;
	}

	/**
	 * 
	 * @param navigationController controller to switch between different views
	 */
	public void setNavigationController(
			NavigationController navigationController) {
		this.navigationController = navigationController;
	}

	public void panelWillAppear() {
		// Stub implementation
	}

	public void panelWillDisappear() {
		// Stub implementation
	}

	public void didPressBack(KeyEvent e) {
		// Stub implementation
	}

}
