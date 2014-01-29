package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Score;
import model.StrokeSet;
import net.miginfocom.swing.MigLayout;

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
		this.add(infoPanel, "grow");
		
		JLabel scoreLabel = new JLabel("Your Score: 12378");
		infoPanel.add(scoreLabel, "wrap");
		
		JLabel bestScoreLabel = new JLabel("Best Score: 12378");
		infoPanel.add(bestScoreLabel, "wrap");
		
		JLabel highscoreTitleLabel = new JLabel("Submit your Score:");
		infoPanel.add(highscoreTitleLabel, "wrap");
		
		highscoreNameField = new JTextField();
		infoPanel.add(highscoreNameField, "growx, pushx");
		
		highscoreSubmitButton = new JButton("Submit");
		infoPanel.add(highscoreSubmitButton, "wrap");
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
		
		
		JButton playButton = new JButton("play again");
		playButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.resultPanelDidSelectReplay();
			}
		});
		infoPanel.add(playButton);
		
		JButton closeButton = new JButton("close");
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.resultPanelShouldClose();
			}
		});
		infoPanel.add(closeButton);
		
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

}
