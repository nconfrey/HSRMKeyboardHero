package gui;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

/**
 * GUI Panel for play/record mode to display the current artist and title
 * 
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
public class TitlePanel extends JPanel {

	JLabel artistLabel;
	JLabel songLabel;

	/**
	 * Creates title panel with to labels for artist and title
	 */
	public TitlePanel() {

		this.setLayout(new MigLayout());

		this.artistLabel = new JLabel("Artist");
		this.artistLabel.setFont(new Font("sanserif", Font.BOLD, 19));
		this.add(this.artistLabel, "wrap");

		this.songLabel = new JLabel("Title");
		this.songLabel.setFont(new Font("sanserif", Font.BOLD, 15));
		this.add(this.songLabel);

		this.artistLabel.setText(PlayerController.getInstance().getTrack()
				.getMp3().getArtist());
		this.songLabel.setText(PlayerController.getInstance().getTrack()
				.getMp3().getTitle());
	}
}
