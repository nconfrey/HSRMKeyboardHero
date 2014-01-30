package view;

import helper.KeyboardHeroConstants;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import model.KeyboardHeroFontModel;

public class TitleLabel extends JLabel {

	public TitleLabel() {
		setFont(KeyboardHeroFontModel.getInstance().getFont(KeyboardHeroFontModel.FONT_NIGHTMARE).deriveFont(82f));
		setHorizontalAlignment(SwingConstants.CENTER);
		setForeground(new Color(KeyboardHeroConstants.FONT_COLOR_PRIMARY));
	}
	
	public TitleLabel(String text) {
		this();
		setText(text);
	}
}
