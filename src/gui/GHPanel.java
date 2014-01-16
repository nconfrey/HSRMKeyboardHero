package gui;

import javax.swing.JPanel;

public abstract class GHPanel extends JPanel{
	private NavigationController navigationController;

	public NavigationController getNavigationController() {
		return navigationController;
	}

	public void setNavigationController(NavigationController navigationController) {
		this.navigationController = navigationController;
	}
	
}
