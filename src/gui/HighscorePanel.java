package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import view.KeyboardHeroConstants;
import view.MenuButton;
import net.miginfocom.swing.MigLayout;
import model.Highscore;
import model.KeyboardHeroFontModel;
import model.Track;

public class HighscorePanel extends GHPanel {

	private Track track;
	private JScrollPane scrollPane;
	private JList<Highscore> scoreList;
	
	
	public HighscorePanel(Track track) {
		
		this.track = track;
		
		this.setLayout(new MigLayout("insets 50 200 50 200, fill"));
		this.setBackground(Color.WHITE);

		JLabel titleLabel = new JLabel("Highscores");
		titleLabel.setFont(KeyboardHeroFontModel.getInstance().getFont(KeyboardHeroFontModel.FONT_NIGHTMARE).deriveFont(82f));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(new Color(
				KeyboardHeroConstants.FONT_COLOR_PRIMARY));
		this.add(titleLabel, "wrap, grow");
		
		
		this.scoreList = new MenuSongList<Highscore>(this.track.getStrokeSet().getHighscores());
		this.scoreList.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.scrollPane = new  JScrollPane(this.scoreList);
		this.scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.add(scrollPane, "wrap, growx, pushy, growy");
		
		MenuButton mainMenuButton = new MenuButton("Back to Tracklist", new Color(
				KeyboardHeroConstants.FONT_COLOR_SECONDARY));
		mainMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getNavigationController().popPanel();
			}
		});
		this.add(mainMenuButton, "growx, height 60!");
		
	}
	
	@Override
	public void panelWillAppear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void panelWillDisappear() {
		// TODO Auto-generated method stub
		
	}

}
