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
	
	public ComboPanel(PlayerController playerController) {
		
		this.playerController = playerController;
		this.comboLabels = new ArrayList<>();
		
		initLabels();
		
		playerController.getScoreController().getScore().addPropertyChangeListener(this);
		
	}
	
	private void initLabels() {
		
		this.setLayout(new MigLayout("fill, insets 4", "[]4[]", ""));
		
		Color color = new Color(255,200,200);
		int colorSteps = 175 / Score.MAXCOMBO;
		
		for(int count = 1; count <= Score.MAXCOMBO; count++) {
			color = new Color(color.getRed(), color.getGreen() - colorSteps, color.getBlue() - colorSteps);
			
			JLabel aLabel = new JLabel("x"+count);
			aLabel.setOpaque(true);
			aLabel.setBackground(color);
			aLabel.setForeground(Color.WHITE);
			aLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
			aLabel.setVerticalAlignment(SwingConstants.CENTER);
			aLabel.setHorizontalAlignment(SwingConstants.CENTER);
			aLabel.setFont(new Font("sanserif", Font.BOLD, 16));
			aLabel.setVisible(false);
			
			this.add(aLabel, "grow");
			this.comboLabels.add(aLabel);
		}
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "combo") {
			final int combo = (int)evt.getNewValue();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					showCombo(combo);
				}
			});
		}
	}
	
	private void showCombo(int combo) {
		for(int count = 0; count < Score.MAXCOMBO; count++) {
			JLabel currentLabel = this.comboLabels.get(count);
			currentLabel.setVisible(combo > count);
		}
	}
}
