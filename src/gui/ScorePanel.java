package gui;



import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import model.Score;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.SecondaryLoop;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class ScorePanel extends TransparentPanel implements PropertyChangeListener {

	JLabel scoreTitleLabel;
	JLabel scoreLabel;
	private PropertyChangeSupport propertyChangeSupport;
	Score score;
	
	

	public ScorePanel(){
		setLayout(new GridLayout(2,1));
		this.scoreTitleLabel = new JLabel("Score" , SwingConstants.CENTER);
		this.add(this.scoreTitleLabel);
		this.scoreLabel = new JLabel("", SwingConstants.CENTER);
		this.add(this.scoreLabel);
		//scoreTitleLabel.setPreferredSize(new Dimension(100,100));
		//scoreLabel.setPreferredSize(new Dimension(100,100));
		scoreTitleLabel.setVerticalAlignment(SwingConstants.TOP);
		scoreTitleLabel.setFont (scoreTitleLabel.getFont ().deriveFont (20.0f));
		propertyChangeSupport = new PropertyChangeSupport(this);
		scoreTitleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		scoreLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		
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

	
	
	
	

