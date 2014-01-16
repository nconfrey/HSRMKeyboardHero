package gui;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class BaseFrame extends JFrame {
	
	private Dimension frameSize;
	public NavigationController navigationController;
	

	public BaseFrame(){
		frameSize = new Dimension(800,600);
		CardLayout cardLayout = new CardLayout();
		this.setSize(frameSize);
		this.setTitle("Keyboard Hero");		
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
}
