package helper;

import java.util.ResourceBundle;

public class KeyboardHeroConstants {
	public static final int FONT_COLOR_PRIMARY = 0x148296;
	public static final int FONT_COLOR_SECONDARY = 0xC92607;
	public static final ResourceBundle BUNDLE_RESOURCE = ResourceBundle.getBundle("KeyboardHero");
	
	public static String getString(String key){
		return BUNDLE_RESOURCE.getString(key);
	}
}
