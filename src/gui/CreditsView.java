package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.nio.ByteBuffer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import view.ImagePanel;
import view.KeyboardHeroConstants;
import view.MenuButton;
import model.KeyboardHeroFontModel;
import net.miginfocom.swing.MigLayout;

public class CreditsView extends GHPanel {
	
	private KeyEventDispatcher keyEventDispatcher;
	private JButton mainMenuButton; 
	
	public CreditsView(){
		
		// General
		this.setLayout(new MigLayout("insets 50 200 20 200, fillx","[][]20[]", "[]20[]"));
		this.setBackground(Color.WHITE);
		
		// Title
		JLabel titleLabel = new JLabel("Credits");
		titleLabel.setFont(KeyboardHeroFontModel.getInstance().getFont(KeyboardHeroFontModel.FONT_NIGHTMARE).deriveFont(82f));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(new Color(KeyboardHeroConstants.FONT_COLOR_PRIMARY));
		this.add(titleLabel, "wrap, grow");
		
		
		
		JLabel hsrmTitle = new JLabel("Hochschule RheinMain");
		JLabel creditText = new JLabel();
		hsrmTitle.setFont(new Font("sanserif", Font.BOLD, 15));
		creditText.setFont(new Font("sanserif", Font.BOLD, 15));
		
		
		
		creditText.setText("<html><body>Keyboard Hero Version 1.0<br> <br>Ein EIBO Projekt von:<br> <br>"
				+ " Simon Seyer <br> Moritz Moeller <br> Martin Juhasz <br> Julia Kraft <br> <br> </body></html>");
		
		
		
		this.add(hsrmTitle, "wrap, growx");
		this.add(creditText, "wrap, growx");
		
		
		
		ImagePanel hsrmPanel = new ImagePanel("HSRM.png", ImagePanel.SIZE_FIXED);
		this.add(hsrmPanel,"wrap, grow, height 200!");
		
		mainMenuButton = new MenuButton("Back to menu", new Color(KeyboardHeroConstants.FONT_COLOR_SECONDARY));
		mainMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getNavigationController().popPanel();
			}
		});
		this.add(mainMenuButton, "growx, height 60!");
		
		
		keyEventDispatcher = new KeyEventDispatcher() {

		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				getNavigationController().popToRootPanel();
				return true;
    		}
			return false;
		}
	};
	}
	public void actionPerformed(ActionEvent e) {
		getNavigationController().popPanel();
	};

	@Override
	public void panelWillAppear() {
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(keyEventDispatcher);
		
	}

	@Override
	public void panelWillDisappear() {
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.removeKeyEventDispatcher(keyEventDispatcher);
		
	}

}
