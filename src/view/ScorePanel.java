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
package view;

import helper.KeyboardHeroConstants;

import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;
import controller.PlayerController;

public class ScorePanel extends TransparentPanel implements
		PropertyChangeListener {

	private PlayerController playerController;
	private JLabel scoreTitleLabel;
	private JLabel scoreLabel;

	/**
	 * Creates a Panel to display the current scores.
	 * 
	 * @param playerController the player controller
	 */
	public ScorePanel(PlayerController playerController) {
		this.playerController = playerController;
		setLayout(new MigLayout());

		this.scoreTitleLabel = new JLabel(
				KeyboardHeroConstants.getString("score_title"));
		this.scoreTitleLabel.setFont(new Font("sanserif", Font.BOLD, 19));
		this.add(this.scoreTitleLabel, "wrap");

		this.scoreLabel = new JLabel("0");
		this.scoreLabel.setFont(new Font("sanserif", Font.BOLD, 15));
		this.add(this.scoreLabel);

		playerController.getScoreController().getScore()
				.addPropertyChangeListener(this);

	}

	/**
	 * Clean up. Remove all listener.
	 */
	public void cleanUp() {
		playerController.getScoreController().getScore()
				.removePropertyChangeListener(this);
	}

	/**
	 * Every time the scorevalue from score controller changes the score label
	 * will be updated.
	 * 
	 * @param evt the evt
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
