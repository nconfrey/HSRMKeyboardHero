package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class MenuButton extends JButton {
	
	private Color backgroundColor;
	
	public MenuButton() {
		super();
		backgroundColor = new Color(KeyboardHeroConstants.FONT_COLOR_PRIMARY);
		setDefaultParams();
	}
	
	public MenuButton(String text) {
		super(text);
		backgroundColor = new Color(KeyboardHeroConstants.FONT_COLOR_PRIMARY);
		setDefaultParams();
	}
	
	public MenuButton(String text, Color backgroundColor) {
		super(text);
		this.backgroundColor = backgroundColor;
		setDefaultParams();
	}
	
	private void setDefaultParams() {
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		this.setBackground(backgroundColor);
		this.setOpaque(true);
		this.setForeground(Color.WHITE);
		this.setFont(new Font("SansSerif", Font.BOLD, 14));
	}
	
}
