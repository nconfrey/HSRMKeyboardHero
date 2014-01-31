/**
 * a Panel that visualises the current Combo-Multiplier
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package view;

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import model.Score;
import net.miginfocom.swing.MigLayout;
import controller.PlayerController;

public class ComboPanel extends JPanel implements PropertyChangeListener {

	private PlayerController playerController;
	private ArrayList<JLabel> comboLabels;

	/**
	 * Instantiates a new combo panel.
	 * 
	 * @param playerController the player controller
	 */
	public ComboPanel(PlayerController playerController) {

		this.playerController = playerController;
		this.comboLabels = new ArrayList<>();

		initLabels();

		playerController.getScoreController().getScore()
				.addPropertyChangeListener(this);

	}

	/**
	 * Clean up. Remove all listeners.
	 */
	public void cleanUp() {
		playerController.getScoreController().getScore()
				.removePropertyChangeListener(this);
	}

	/**
	 * Inits the labels.
	 */
	private void initLabels() {

		this.setLayout(new MigLayout("fill, insets 4", "[]4[]", ""));

		for (int count = 1; count <= Score.MAXCOMBO; count++) {
			JLabel aLabel = new JLabel("x" + count);
			aLabel.setOpaque(true);
			aLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			aLabel.setVerticalAlignment(SwingConstants.CENTER);
			aLabel.setHorizontalAlignment(SwingConstants.CENTER);
			aLabel.setFont(new Font("sanserif", Font.BOLD, 16));
			aLabel.setBackground(Color.LIGHT_GRAY);
			aLabel.setForeground(Color.DARK_GRAY);

			this.add(aLabel, "grow");
			this.comboLabels.add(aLabel);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.
	 * PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "combo") {
			final int combo = (int) evt.getNewValue();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					showCombo(combo);
				}
			});
		}
	}

	/**
	 * Changes the view to reflect the current combo multiplier
	 * 
	 * @param combo the combo
	 */
	private void showCombo(int combo) {

		Color color = new Color(255, 200, 200);
		int colorSteps = 175 / Score.MAXCOMBO;

		for (int count = 0; count < Score.MAXCOMBO; count++) {
			color = new Color(color.getRed(), color.getGreen() - colorSteps,
					color.getBlue() - colorSteps);
			JLabel currentLabel = this.comboLabels.get(count);

			if (combo > count) {
				currentLabel.setBackground(color);
				currentLabel.setForeground(Color.WHITE);
			} else {
				currentLabel.setBackground(Color.LIGHT_GRAY);
				currentLabel.setForeground(Color.DARK_GRAY);
			}
		}
	}
}
