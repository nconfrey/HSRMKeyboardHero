/**
 * Saves all user chosen preferences
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 **/
package model;

import java.util.prefs.Preferences;

public class KeyboardHeroPreferences {
	private Preferences prefs;
	private static final String PREF_NAME = "keyboardhero.pref";
	private static final String SCREEN_WIDTH = "screenwidth";
	private static final String SCREEN_HEIGHT = "screenheight";

	/**
	 * Sets the path for the preferences file.
	 */
	public void setPreferences() {
		prefs = Preferences.userRoot().node(PREF_NAME);
	}

	/**
	 * Gets the screen width.
	 * 
	 * @return the saved screen witdth (default 800)
	 */
	public int getScreenWidth() {
		return prefs.getInt(SCREEN_WIDTH, 1000);
	}

	/**
	 * Gets the screen height.
	 * 
	 * @return the saved screen height (default 600)
	 */
	public int getScreenHeight() {
		return prefs.getInt(SCREEN_HEIGHT, 800);
	}

	/**
	 * Saves user set screensize.
	 * 
	 * @param width sets the preference for screen width
	 * @param height sets the preference for screen height
	 */
	public void setScreenSize(int width, int height) {
		prefs.putInt(SCREEN_WIDTH, width);
		prefs.putInt(SCREEN_HEIGHT, height);
	}

}
