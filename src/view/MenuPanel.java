/**
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

import java.awt.Color;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Track;
import net.miginfocom.swing.MigLayout;
import controller.PlayerController;
import controller.player.MP3PlayerLocalTrack;

/**
 * Main menu panel. This is the root panel. Every section/view of Keyboard Hero
 * is reachable from this view
 * 
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
public class MenuPanel extends GHPanel implements ActionListener {

	private JButton playButton;
	private JButton recordButton;
	private JButton highscoreButton;
	private JButton creditsButton;

	/**
	 * Creates the main menu with its components.
	 */
	public MenuPanel() {

		this.setBackground(Color.WHITE);
		this.setLayout(new MigLayout("insets 50 0 0 0, fillx"));

		JLabel titleLabel = new TitleLabel(KeyboardHeroConstants.getString("game_title"));
		this.add(titleLabel, "wrap, grow");

		playButton = new MenuButton(KeyboardHeroConstants.getString("start_game"));
		playButton.addActionListener(this);
		recordButton = new MenuButton(KeyboardHeroConstants.getString("start_recording"));
		recordButton.addActionListener(this);
		highscoreButton = new MenuButton(KeyboardHeroConstants.getString("show_highscore"));
		highscoreButton.addActionListener(this);
		creditsButton = new MenuButton(KeyboardHeroConstants.getString("show_credits"));
		creditsButton.addActionListener(this);

		JPanel buttonPanel = new JPanel(new MigLayout(
				"insets 0 200 20 200, fillx", "", "[]15[]"));
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.add(playButton, "wrap, grow, height 60!");
		buttonPanel.add(recordButton, "wrap, grow, height 60");
		buttonPanel.add(highscoreButton, "wrap, grow, height 60");
		buttonPanel.add(creditsButton, "wrap, grow, height 60");

		this.add(buttonPanel, "wrap, grow");
		loadIntro();
	}

	/**
	 * Action Listener for the buttons in main menu.
	 *
	 * @param e the e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playButton || e.getSource() == recordButton) {
			PlayerController.getInstance().setRecording(
					e.getSource() == recordButton);
			int mode = (e.getSource() == recordButton) ? SongListPanel.MODE_RECORD
					: SongListPanel.MODE_PLAY;
			SongListPanel songListPanel = new SongListPanel(mode);
			this.getNavigationController().pushPanel(songListPanel);
		} else if (e.getSource() == highscoreButton) {
			SongListPanel songListPanel = new SongListPanel(
					SongListPanel.MODE_HIGHSCORE);
			this.getNavigationController().pushPanel(songListPanel);
		} else if (e.getSource() == creditsButton) {
			CreditsView creditPanel = new CreditsView();
			this.getNavigationController().pushPanel(creditPanel);
		}
	}

	/**
	 * Loads the intro song of keyboard hero and plays it in loop mode.
	 */
	public void loadIntro() {
		MP3PlayerLocalTrack mp3 = new MP3PlayerLocalTrack(new File(
				"music/back_in_black.mp3"));
		Track intro = new Track(mp3);
		PlayerController.getInstance().setTrack(intro);
		PlayerController.getInstance().loop();
	}

	/**
	 * Closes Window on ESC button.
	 *
	 * @param e the e
	 */
	@Override
	public void didPressBack(KeyEvent e) {
		System.exit(0);
	}
}
