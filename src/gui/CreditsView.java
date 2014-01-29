package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import view.ImagePanel;
import view.KeyboardHeroConstants;
import view.MenuButton;
import view.TitleLabel;

public class CreditsView extends GHPanel {
	
	private JButton mainMenuButton; 
	
	public CreditsView(){
		
		// General
		this.setLayout(new MigLayout("insets 50 200 50 200, fillx"));
		this.setBackground(Color.WHITE);
		
		// Title
		JLabel titleLabel = new TitleLabel("Credits");
		this.add(titleLabel, "wrap, grow");
		
		
		
		JLabel hsrmTitle = new JLabel("Hochschule RheinMain");
		JLabel creditText = new JLabel();
		hsrmTitle.setFont(new Font("sanserif", Font.BOLD, 15));
		creditText.setFont(new Font("sanserif", Font.PLAIN, 15));
		creditText.setHorizontalAlignment(SwingConstants.CENTER);
		hsrmTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		
		creditText.setText("<html><body>Keyboard Hero Version 1.0<br> <br><b>Ein EIBO Projekt von:</b><br>"
				+ " Simon Seyer <br> Moritz Moeller <br> Martin Juhasz <br> Julia Kraft <br> </body></html>");
		
		
		
		this.add(hsrmTitle, "wrap, growx");
		this.add(creditText, "wrap, growx");
		
		
		
		ImagePanel hsrmPanel = new ImagePanel("HSRM.png", ImagePanel.SIZE_FIXED);
		this.add(hsrmPanel,"wrap,grow, height 100!");
		
		mainMenuButton = new MenuButton("Back to menu", new Color(KeyboardHeroConstants.FONT_COLOR_SECONDARY));
		mainMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getNavigationController().popPanel();
			}
		});
		this.add(mainMenuButton, "growx, height 60!");
	}
	public void actionPerformed(ActionEvent e) {
		getNavigationController().popPanel();
	};
	
	@Override
	public void didPressBack(KeyEvent e) {
		getNavigationController().popToRootPanel();
	}


}
