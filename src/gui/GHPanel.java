package gui;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public abstract class GHPanel extends JPanel{
	private NavigationController navigationController;

	public NavigationController getNavigationController() {
		return navigationController;
	}

	public void setNavigationController(NavigationController navigationController) {
		this.navigationController = navigationController;
	}
	
	public void panelWillAppear(){
		// Stub implementation
	}
	
	public void panelWillDisappear() {
		// Stub implementation
	}
	
	public void didPressBack(KeyEvent e) {
		// Stub implementation
	}

}
