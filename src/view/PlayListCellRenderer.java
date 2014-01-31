/**
 * a cell renderer for the playlist
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package view;

import helper.KeyboardHeroConstants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;

import model.MP3PlayerRemoteTrack;
import model.Track;

public class PlayListCellRenderer extends DefaultListCellRenderer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing
	 * .JList, java.lang.Object, int, boolean, boolean)
	 */

	private ImageIcon soundCloudIcon;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing
	 * .JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list,
			Object value, int index, boolean isSelected, boolean cellHasFocus) {

		Component c = super.getListCellRendererComponent(list, value, index,
				isSelected, cellHasFocus);

		if (value instanceof Track && c instanceof JLabel) {
			Track track = (Track) value;
			JLabel label = (JLabel) c;
			label.setHorizontalTextPosition(SwingConstants.LEFT);
			label.setIconTextGap(15);
			try {
				soundCloudIcon = new ImageIcon(ImageIO.read(getClass()
						.getResourceAsStream("/soundcloud.png")));
			} catch (IOException e) {
			}
			if (track.getMp3() instanceof MP3PlayerRemoteTrack) {
				label.setIcon(soundCloudIcon);
			}
		}
		
		Color backgroundColor = new Color(KeyboardHeroConstants.COLOR_PRIMARY);
		
		if(isSelected || cellHasFocus) {
			backgroundColor = backgroundColor.brighter();
		}
		
		c.setBackground(backgroundColor);
		c.setForeground(Color.WHITE);
		c.setFont(new Font("SansSerif", Font.BOLD, 14));

		setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

		return c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JLabel#getHorizontalAlignment()
	 */
	@Override
	public int getHorizontalAlignment() {
		return CENTER;
	}

}
