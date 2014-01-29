package gui;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import view.KeyboardHeroConstants;
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

		this.artistLabel = new JLabel(KeyboardHeroConstants.getString("artist_label"));
		this.artistLabel.setFont(new Font("sanserif", Font.BOLD, 19));
		this.add(this.artistLabel, "wrap");

		this.songLabel = new JLabel(KeyboardHeroConstants.getString("title_label"));
		this.songLabel.setFont(new Font("sanserif", Font.BOLD, 15));
		this.add(this.songLabel);

		this.artistLabel.setText(PlayerController.getInstance().getTrack()
				.getMp3().getArtist());
		this.songLabel.setText(PlayerController.getInstance().getTrack()
				.getMp3().getTitle());
	}
}
