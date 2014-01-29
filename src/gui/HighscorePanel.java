package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import model.Highscore;
import model.Track;
import net.miginfocom.swing.MigLayout;
import view.KeyboardHeroConstants;
import view.MenuButton;
import view.TitleLabel;

public class HighscorePanel extends GHPanel {

	private Track track;
	private JScrollPane scrollPane;
	private JList<Highscore> scoreList;
	
	
	public HighscorePanel(Track track) {
		
		this.track = track;
		
		this.setLayout(new MigLayout("insets 50 200 50 200, fill"));
		this.setBackground(Color.WHITE);

		JLabel titleLabel = new TitleLabel("Highscores");
		this.add(titleLabel, "wrap, grow");
		
		
		this.scoreList = new MenuSongList<Highscore>(this.track.getStrokeSet().getHighscores(), false);
		this.scoreList.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.scrollPane = new  JScrollPane(this.scoreList);
		this.scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.add(scrollPane, "wrap, growx, pushy, growy");
		
		MenuButton mainMenuButton = new MenuButton("Back to Tracklist", new Color(
				KeyboardHeroConstants.FONT_COLOR_SECONDARY));
		mainMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				didPressBack(null);
			}
		});
		this.add(mainMenuButton, "growx, height 60!");
		
	}

	@Override
	public void didPressBack(KeyEvent e) {
		getNavigationController().popPanel();
	}

}
