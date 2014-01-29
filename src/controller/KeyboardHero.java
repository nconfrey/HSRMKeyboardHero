package controller;

import gui.BaseFrame;
import gui.MenuPanel;
import gui.NavigationController;
import model.PersistenceHandler;

/**
 * Creates an instance of the game
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 **/
public class KeyboardHero {

	public static void main(String[] args) {

		BaseFrame baseFrame = new BaseFrame();
		NavigationController navCon = new NavigationController(baseFrame);
		MenuPanel menu = new MenuPanel();
		navCon.pushPanel(menu);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				PersistenceHandler.savePlaylist();
			}
		});

	}
}
