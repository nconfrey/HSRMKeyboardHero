package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MenuPanel extends GHPanel implements ActionListener{
	
	private JButton playButton;
	private JButton recordButton;
	private JButton highscoreButton;
	private JButton creditsButton;
	
	public MenuPanel(){
		
		this.setLayout(new GridLayout(4,1));
		
		
		playButton = new JButton("Play");
		playButton.addActionListener(this);
		recordButton = new JButton("Record");
		recordButton.addActionListener(this);
		highscoreButton = new JButton("Highscores");
		highscoreButton.addActionListener(this);
		creditsButton = new JButton("Credits");
		creditsButton.addActionListener(this);
		
		this.add(playButton);
		this.add(recordButton);
		this.add(highscoreButton);
		this.add(creditsButton);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == playButton || e.getSource() == recordButton){
			PlayerController.getInstance().setRecording(e.getSource() == recordButton);
			SongViewer songViewer = new SongViewer();
			this.getNavigationController().pushPanel(songViewer);
		}
		if(e.getSource() == highscoreButton){
		}
		if(e.getSource() == creditsButton){
		}
	}
}
