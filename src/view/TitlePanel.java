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
package view;

import helper.KeyboardHeroConstants;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import controller.PlayerController;

public class TitlePanel extends JPanel {

	private PlayerController playerController;
	private JLabel artistLabel;
	private JLabel songLabel;

	/**
	 * Creates title panel with to labels for artist and title.
	 * 
	 * @param playerController the player controller
	 */
	public TitlePanel(PlayerController playerController) {
		this.playerController = playerController;
		this.setLayout(new MigLayout());

		this.artistLabel = new JLabel(
				KeyboardHeroConstants.getString("artist_label"));
		this.artistLabel.setFont(new Font("sanserif", Font.BOLD, 19));
		this.add(this.artistLabel, "wrap");

		this.songLabel = new JLabel(
				KeyboardHeroConstants.getString("title_label"));
		this.songLabel.setFont(new Font("sanserif", Font.BOLD, 15));
		this.add(this.songLabel);

		this.artistLabel.setText("<html><body>"
				+ playerController.getTrack().getMp3().getArtist()
				+ "</body></html>");
		this.songLabel.setText("<html><body>"
				+ playerController.getTrack().getMp3().getTitle()
				+ "</body></html>");
	}
}
