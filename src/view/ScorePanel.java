package view;

import helper.KeyboardHeroConstants;

import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import controller.PlayerController;
import model.Score;
import net.miginfocom.swing.MigLayout;

/**
 * GUI Panel for play mode to display the current scores
 * 
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
public class ScorePanel extends TransparentPanel implements
		PropertyChangeListener {

	JLabel scoreTitleLabel;
	JLabel scoreLabel;
	Score score;

	/**
	 * Creates a Panel to display the current scores
	 */
	public ScorePanel() {

		setLayout(new MigLayout());

		this.scoreTitleLabel = new JLabel(KeyboardHeroConstants.getString("score_title"));
		this.scoreTitleLabel.setFont(new Font("sanserif", Font.BOLD, 19));
		this.add(this.scoreTitleLabel, "wrap");

		this.scoreLabel = new JLabel("0");
		this.scoreLabel.setFont(new Font("sanserif", Font.BOLD, 15));
		this.add(this.scoreLabel);

		PlayerController.getInstance().getScoreController().getScore()
				.addPropertyChangeListener(this);

	}

	/**
	 * Every time the scorevalue from score controller changes the score
	 * label will be updated
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "score") {
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
