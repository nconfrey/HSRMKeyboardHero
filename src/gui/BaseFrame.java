package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.KeyboardHeroPreferences;

public class BaseFrame extends JFrame {
	
	private Dimension frameSize;
	public NavigationController navigationController;
	private KeyboardHeroPreferences prefs;
	

	public BaseFrame(){
		prefs = new KeyboardHeroPreferences();
		prefs.setPreferences();
		System.out.println(prefs.getScreenWidth() + " " + prefs.getScreenHeight());
		frameSize = new Dimension(prefs.getScreenWidth(),prefs.getScreenHeight());
		setLayout(new BorderLayout());
		this.setSize(frameSize);
		WindowListener exitListener = new WindowAdapter() {
			@Override
            public void windowClosing(WindowEvent e) {
				prefs.setScreenSize(getWidth(), getHeight());
				System.exit(0);
            }
		};
		this.addWindowListener(exitListener);
		this.setTitle("Keyboard Hero");		
		this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void display(JComponent component) {
		getContentPane().add(component, BorderLayout.CENTER);
		getContentPane().revalidate();
		getContentPane().repaint();
	}
	
}
