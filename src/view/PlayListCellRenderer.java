package view;

import helper.KeyboardHeroConstants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class PlayListCellRenderer extends DefaultListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list,
			Object value, int index, boolean isSelected, boolean cellHasFocus) {
		
		Component c = super.getListCellRendererComponent(list, value,
				index, isSelected, cellHasFocus);
		
		c.setBackground(new Color(
				KeyboardHeroConstants.COLOR_PRIMARY));
		c.setForeground(Color.WHITE);
		c.setFont(new Font("SansSerif", Font.BOLD, 14));

		setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
		
		return c;
	}
	
	@Override
	public int getHorizontalAlignment() {
		return CENTER;
	}

}
