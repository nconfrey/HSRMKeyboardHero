package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.Line;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements ActionListener{
	
	private final Dimension screenSize;
	private final Dimension frameSize;
	
	private JPanel menuPanel;
	
	private JLayeredPane layeredPane = getLayeredPane();
	
	private JButton playButton;
	private JButton recordButton;
	private JButton highscoreButton;
	private JButton creditsButton;
	private GamePanel gamePanel;
	
	public MainFrame(){
		// Window
		frameSize = new Dimension(800,600);
		screenSize = this.getToolkit().getScreenSize(); 
		this.setSize(frameSize);
		this.setTitle("Keyboard Hero");
		
		// center mainframe
		this.setLocation((int) ((screenSize.getWidth() - this.getWidth()) / 2), (int) ((screenSize.getHeight() - this.getHeight()) / 2));

		
		playButton = new JButton("Play");
		playButton.addActionListener(this);
		recordButton = new JButton("Record");
		recordButton.addActionListener(this);
		highscoreButton = new JButton("Highscores");
		highscoreButton.addActionListener(this);
		creditsButton = new JButton("Credits");
		creditsButton.addActionListener(this);

		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(frameSize);
		
		menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(4,1));
		
		menuPanel.add(playButton);
		menuPanel.add(recordButton);
		menuPanel.add(highscoreButton);
		menuPanel.add(creditsButton);
	   
		menuPanel.setBounds(frameSize.width/2-150,frameSize.height/2-100,300,200);
		
		layeredPane.add(menuPanel, new Integer(100));
		this.add(layeredPane);
		System.out.println("menu panel index: " + layeredPane.getIndexOf(gamePanel));
		this.setResizable(false);
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    this.setVisible(true);
	    
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == playButton){
			gamePanel = new GamePanel(layeredPane.getSize());
			gamePanel.setBounds(0, 0, frameSize.width, frameSize.height);
			layeredPane.add(gamePanel, new Integer(200));
			System.out.println("game panel index: " + layeredPane.getIndexOf(gamePanel));
		}
	}
	
	public Dimension getFrameSize() {
		return frameSize;
	}
	
	public void backToMenu(){
		this.layeredPane.remove(gamePanel);
	}
}
