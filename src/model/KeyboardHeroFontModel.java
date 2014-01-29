package model;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

import controller.KeyboardHero;


public class KeyboardHeroFontModel {
	
	public static final int FONT_NIGHTMARE = 0;
	
	private Map<Integer, Font> fonts;
	
	private static KeyboardHeroFontModel instance;
	
	public static KeyboardHeroFontModel getInstance() {
		if(instance == null) {
			instance = new KeyboardHeroFontModel();
		}
		return instance;
	}
	
	public KeyboardHeroFontModel() {
		this.fonts = new TreeMap<Integer, Font>();
		
		this.fonts.put(FONT_NIGHTMARE, loadFont("Nightmare_Hero_Normal.ttf"));
		
	}
	
	public Font getFont(int font) {
		return this.fonts.get(font);
	}
	
	private Font loadFont(String fontName) {
		InputStream is = KeyboardHero.class.getResourceAsStream("/"+fontName);
		try {
			return Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
