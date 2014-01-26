package gui;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class TitlePanel extends JPanel {

	JLabel artistLabel;
	JLabel songLabel;
	
	public TitlePanel(){
		
		this.setLayout(new MigLayout());
		
		this.artistLabel = new JLabel("Interpret");
		this.artistLabel.setFont(new Font("sanserif", Font.BOLD, 19));
		this.add(this.artistLabel, "wrap");
		
		this.songLabel = new JLabel("Song");
		this.songLabel.setFont(new Font("sanserif", Font.BOLD, 15));
		this.add(this.songLabel);
		
		
		
		
		
		
		this.artistLabel.setText(PlayerController.getInstance().getTrack().getMp3().getArtist());
		this.songLabel.setText(PlayerController.getInstance().getTrack().getMp3().getTitle());
	}
}
