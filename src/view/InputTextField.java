package view;

import helper.KeyboardHeroConstants;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class InputTextField extends JTextField {

	public InputTextField() {
		this.setForeground(Color.white);
		this.setCaretColor(Color.white);
		this.setFont(new Font("SansSerif", Font.BOLD, 14));
		Color searchFieldColor = new Color(KeyboardHeroConstants.COLOR_PRIMARY);
		this.setBackground(searchFieldColor);
		this.setBorder(BorderFactory.createCompoundBorder(new LineBorder(
				searchFieldColor.darker(), 1), new EmptyBorder(8, 8, 8, 8)));
	}

}
