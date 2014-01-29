package model;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Saves all user chosen preferences
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Möller
 * 
 **/
public class KeyboardHeroPreferences {
	private Preferences prefs;
	private static final String PREF_NAME = "keyboardhero.pref";
	private static final String FULL_SCREEN = "fullscreen";

	/**
	 * Sets the path for the preferences file
	 */
	public void setPreferences() {
		prefs = Preferences.userRoot().node(PREF_NAME);
	}

	/**
	 * 
	 * @return the preference for fullscreen
	 */
	public boolean getFullScreen() {
		return prefs.getBoolean(FULL_SCREEN, false);
	}

	/**
	 * 
	 * @param fs sets the frame size to fullscreen if true
	 */
	public void setFullScree(boolean fs) {
		prefs.putBoolean(FULL_SCREEN, fs);
		try {
			prefs.sync();
		} catch (BackingStoreException e) {
		}
	}

}
