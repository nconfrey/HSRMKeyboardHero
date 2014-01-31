/**
 * a cell renderer specialized to display tracks to record
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package view;

import java.awt.Component;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;

import model.MP3PlayerRemoteTrack;
import model.Track;

public class RecordListCellRenderer extends PlayListCellRenderer {

	private ImageIcon soundCloudIcon;
	private ImageIcon noteIcon;

	/**
	 * Instantiates a new record list cell renderer.
	 */
	public RecordListCellRenderer() {
		init();
	}

	/**
	 * Inits the.
	 */
	private void init() {
		try {
			soundCloudIcon = new ImageIcon(ImageIO.read(getClass()
					.getResourceAsStream("/soundcloud.png")));
		} catch (IOException e) {
		}
		try {
			noteIcon = new ImageIcon(ImageIO.read(getClass()
					.getResourceAsStream("/note.png")));
		} catch (IOException e) {
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax
	 * .swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		Component c = super.getListCellRendererComponent(list, value, index,
				isSelected, cellHasFocus);

		if (value instanceof Track && c instanceof JLabel) {
			Track track = (Track) value;
			JLabel label = (JLabel) c;
			label.setHorizontalTextPosition(SwingConstants.LEFT);
			label.setIconTextGap(15);
			if (track.getStrokeSet() != null) {
				label.setIcon(noteIcon);
			} else if (track.getMp3() instanceof MP3PlayerRemoteTrack) {
				label.setIcon(soundCloudIcon);
			}
		}
		return c;
	}

}
