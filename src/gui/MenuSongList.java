package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListModel;

import view.KeyboardHeroConstants;

public class MenuSongList<E> extends JList<E> {
	
	public MenuSongList(ListModel<E> dataModel) {
		
		super(dataModel);
		addStyling();
		
	}
	
	private void addStyling() {
		
		this.setFixedCellHeight(60);
		
		DefaultListCellRenderer renderer = new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                
            	Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                
                c.setBackground(new Color(KeyboardHeroConstants.FONT_COLOR_PRIMARY));
                c.setForeground(Color.WHITE);
        		c.setFont(new Font("SansSerif", Font.BOLD, 14));
        		
                setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
                

                return c;
            }
            
            public int getHorizontalAlignment() {
                return CENTER;
            }

        };
		
		setCellRenderer(renderer);
	}
	
}
