package gui;



import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;
import model.Score;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class ScorePanel extends TransparentPanel implements PropertyChangeListener {

	JLabel scoreTitleLabel;
	JLabel scoreLabel;
	Score score;
	
	

	public ScorePanel(){
		
		setLayout(new MigLayout());
		
		this.scoreTitleLabel = new JLabel("Score");
		this.scoreTitleLabel.setFont(new Font("sanserif", Font.BOLD, 19));
		this.add(this.scoreTitleLabel, "wrap");
		
		this.scoreLabel = new JLabel("0");
		this.scoreLabel.setFont(new Font("sanserif", Font.BOLD, 15));
		this.add(this.scoreLabel);
	
		
		
		
		PlayerController.getInstance().getScoreController().getScore().addPropertyChangeListener(this);
		
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName() == "score") {
			final long newScore = (long) evt.getNewValue();
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					scoreLabel.setText(String.valueOf(newScore));					
				}
			});
		}
	}

}

	
	
	
	

