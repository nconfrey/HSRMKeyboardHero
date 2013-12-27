package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.Line;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuFrame extends JFrame implements ActionListener{
	
	private final Dimension screenSize;
	private final Dimension frameSize;
	
	private JPanel wrapper;
	private JPanel menuPanel;
	
	private JButton playButton;
	private JButton recordButton;
	private JButton highscoreButton;
	private JButton creditsButton;
	
	public MenuFrame(){
		// Window
		frameSize = new Dimension(200,250);
		screenSize = this.getToolkit().getScreenSize(); 
		this.setSize(frameSize);
		this.setTitle("Keyboard Hero");
				
		// center mainframe
		this.setLocation((int) ((screenSize.getWidth() - this.getWidth()) / 2), (int) ((screenSize.getHeight() - this.getHeight()) / 2));

		playButton = new JButton("Play");
		recordButton = new JButton("Record");
		highscoreButton = new JButton("Highscores");
		creditsButton = new JButton("Credits");
		
		
		playButton.addActionListener(this);
		recordButton.addActionListener(this);
		highscoreButton.addActionListener(this);
		creditsButton.addActionListener(this);

		
		wrapper = new JPanel();
		wrapper.setLayout(new BorderLayout());
		menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(3,1));
		
		menuPanel.add(playButton);
		menuPanel.add(recordButton);
		menuPanel.add(highscoreButton);
	    menuPanel.setBackground(Color.LIGHT_GRAY);

		
		wrapper.add(menuPanel, BorderLayout.NORTH);
		wrapper.add(creditsButton, BorderLayout.SOUTH);
		this.add(wrapper);
		
		
		
		this.setResizable(false);
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    this.setVisible(true);
	    
	    
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == playButton){
			new GameFrame();
			setVisible(false);
		}
	}
}
