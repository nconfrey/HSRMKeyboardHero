package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class BaseFrame extends JFrame {
	
	private Dimension frameSize;
	public NavigationController navigationController;
	

	public BaseFrame(){
		frameSize = new Dimension(800,600);
		setLayout(new BorderLayout());
		this.setSize(frameSize);
		this.setTitle("Keyboard Hero");		
		this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void display(JComponent component) {
		getContentPane().add(component, BorderLayout.CENTER);
		getContentPane().revalidate();
		getContentPane().repaint();
	}
	
}
