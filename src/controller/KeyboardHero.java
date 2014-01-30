/**
 * Start Point for the Application
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package controller;

import view.BaseFrame;
import view.MenuPanel;

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

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		final PlayerController playerController = new PlayerController();
		BaseFrame baseFrame = new BaseFrame();
		NavigationController navCon = new NavigationController(baseFrame);
		MenuPanel menu = new MenuPanel(playerController);
		navCon.pushPanel(menu);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				playerController.getPlaylistController().save();
			}
		});
	}
}
