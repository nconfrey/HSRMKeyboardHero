package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class MenuButton extends JButton {
	
	public MenuButton() {
		super();
		setDefaultParams();
	}
	
	public MenuButton(String content) {
		super(content);
		setDefaultParams();
	}
	
	private void setDefaultParams() {
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		this.setBackground(new Color(0x148296));
		this.setOpaque(true);
		this.setForeground(Color.WHITE);
		this.setFont(new Font("SansSerif", Font.BOLD, 14));
	}
	
}
