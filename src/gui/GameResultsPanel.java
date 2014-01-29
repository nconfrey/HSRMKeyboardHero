package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Score;
import model.StrokeSet;
import net.miginfocom.swing.MigLayout;
import view.KeyboardHeroConstants;
import view.MenuButton;

public class GameResultsPanel extends JPanel {
	
	private JTextField highscoreNameField;
	JButton highscoreSubmitButton;
	private ResultListener listener;
	
	public interface ResultListener {
		public void resultPanelShouldClose();
		public void resultPanelDidSelectReplay();
	}
	
	public GameResultsPanel(ResultListener aListener) {
		
		this.listener = aListener;
		
		this.setBackground(new Color(0,0,0, 170));
		this.setLayout(new MigLayout("insets 50 200 50 200, fill"));
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new MigLayout("fillx"));
		infoPanel.setBackground(Color.WHITE);
		this.add(infoPanel, "grow");
		
		JLabel scoreTitleLabel = new JLabel("Your Score");
		scoreTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(scoreTitleLabel, "w 45%");
		
		JLabel bestScoreTitleLabel = new JLabel("Best Score");
		bestScoreTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(bestScoreTitleLabel, "w 45%, wrap");
		
		JLabel scoreLabel = new JLabel("1280");
		scoreLabel.setOpaque(true);
		scoreLabel.setBackground(new Color(KeyboardHeroConstants.FONT_COLOR_PRIMARY));
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setFont(new Font("sanserif", Font.BOLD, 27));
		infoPanel.add(scoreLabel, "w 45%, h 100!");
		
		JLabel bestScoreLabel = new JLabel("3500");
		bestScoreLabel.setOpaque(true);
		bestScoreLabel.setBackground(new Color(KeyboardHeroConstants.FONT_COLOR_PRIMARY));
		bestScoreLabel.setForeground(Color.WHITE);
		bestScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bestScoreLabel.setFont(new Font("sanserif", Font.BOLD, 27));
		infoPanel.add(bestScoreLabel, "w 45%, h 100!, wrap");
		
		JLabel highscoreTitleLabel = new JLabel("Enter your Name and submit your highscore:");
		infoPanel.add(highscoreTitleLabel, "gapy 30,span, wrap");
		
		highscoreNameField = new JTextField();
		infoPanel.add(highscoreNameField, "w 45%");
		
		highscoreSubmitButton = new MenuButton("Submit");
		infoPanel.add(highscoreSubmitButton, "w 45%, wrap");
		highscoreSubmitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(highscoreNameField.getText().trim().length() <= 0) return;
				
				Score score = PlayerController.getInstance().getScoreController().getScore();
				StrokeSet strokeSet = PlayerController.getInstance().getTrack().getStrokeSet();
				
				strokeSet.addHighscore((int)score.getScore(), highscoreNameField.getText());
				
				highscoreNameField.setEditable(false);
				highscoreSubmitButton.setEnabled(false);
				
			}
		});
		
		JPanel pushPanel = new JPanel();
		pushPanel.setBackground(Color.WHITE);
		infoPanel.add(pushPanel, "grow, pushy, span");
		
		
		JButton playButton = new MenuButton("play again", new Color(KeyboardHeroConstants.FONT_COLOR_SECONDARY));
		playButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.resultPanelDidSelectReplay();
			}
		});
		infoPanel.add(playButton, "w 45%");
		
		JButton closeButton = new MenuButton("close", new Color(KeyboardHeroConstants.FONT_COLOR_SECONDARY));
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.resultPanelShouldClose();
			}
		});
		infoPanel.add(closeButton, "w 45%");
		
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

}
