package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import view.KeyboardHeroConstants;
import view.MenuButton;
import model.KeyboardHeroFontModel;
import net.miginfocom.swing.MigLayout;

public class MenuPanel extends GHPanel implements ActionListener{
	
	private JButton playButton;
	private JButton recordButton;
	private JButton highscoreButton;
	private JButton creditsButton;
	
	public MenuPanel(){
		
		this.setBackground(Color.WHITE);
		this.setLayout(new MigLayout("insets 50 0 0 0, fillx"));
		
		JLabel titleLabel = new JLabel("Keyboard Hero");
		titleLabel.setFont(KeyboardHeroFontModel.getInstance().getFont(KeyboardHeroFontModel.FONT_NIGHTMARE).deriveFont(82f));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(new Color(KeyboardHeroConstants.FONT_COLOR_PRIMARY));
		this.add(titleLabel, "wrap, grow");
		
		playButton = new MenuButton("Play");
		playButton.addActionListener(this);
		recordButton = new MenuButton("Record");
		recordButton.addActionListener(this);
		highscoreButton = new MenuButton("Highscores");
		highscoreButton.addActionListener(this);
		creditsButton = new MenuButton("Credits");
		creditsButton.addActionListener(this);
		
		
		JPanel buttonPanel = new JPanel(new MigLayout("insets 0 200 20 200, fillx", "", "[]15[]"));
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.add(playButton, "wrap, grow, height 60!");
		buttonPanel.add(recordButton, "wrap, grow, height 60");
		buttonPanel.add(highscoreButton, "wrap, grow, height 60");
		buttonPanel.add(creditsButton, "wrap, grow, height 60");
		
		this.add(buttonPanel, "wrap, grow");
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == playButton || e.getSource() == recordButton){
			PlayerController.getInstance().setRecording(e.getSource() == recordButton);
			SongListPanel songViewer = new SongListPanel();
			this.getNavigationController().pushPanel(songViewer);
		}
		if(e.getSource() == highscoreButton){
		}
		if(e.getSource() == creditsButton){
		}
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
