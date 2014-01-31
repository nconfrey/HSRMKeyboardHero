/**
 * a View that displays information about the authors
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package view;

import helper.KeyboardHeroConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

public class CreditsView extends GHPanel {

	private JButton mainMenuButton;

	/**
	 * Instantiates a new credits view.
	 */
	public CreditsView() {

		// General
		this.setLayout(new MigLayout("insets 50 200 50 200, fillx"));
		this.setBackground(Color.WHITE);

		// Title
		JLabel titleLabel = new TitleLabel(
				KeyboardHeroConstants.getString("credits_title"));
		this.add(titleLabel, "wrap, grow");

		JLabel hsrmTitle = new JLabel(
				KeyboardHeroConstants.getString("credits_organisation"));
		hsrmTitle.setFont(new Font("sanserif", Font.BOLD, 15));
		hsrmTitle.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(hsrmTitle, "wrap, growx");

		JLabel creditText = new JLabel(
				KeyboardHeroConstants.getString("credits_text"));
		creditText.setFont(new Font("sanserif", Font.PLAIN, 15));
		creditText.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(creditText, "wrap, growx");

		ImagePanel hsrmPanel = new ImagePanel("HSRM.png", ImagePanel.SIZE_FIXED);
		this.add(hsrmPanel, "wrap,grow, height 100!");

		mainMenuButton = new MenuButton(
				KeyboardHeroConstants.getString("back_to_menu"), new Color(
						KeyboardHeroConstants.COLOR_SECONDARY));
		mainMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getNavigationController().popPanel();
			}
		});
		this.add(mainMenuButton, "growx, height 60!");
	}

	/**
	 * Action performed.
	 * 
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e) {
		getNavigationController().popPanel();
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.GHPanel#didPressBack(java.awt.event.KeyEvent)
	 */
	@Override
	public void didPressBack(KeyEvent e) {
		getNavigationController().popToRootPanel();
	}

}
