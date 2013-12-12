package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 8845013593437103736L;
	
	
	private JPanel wrapper;			// Background Panel
	private JPanel contentPanel;	
	private JPanel leftContent;		// Panel fuer Punktestand Statistik etc...
	private JPanel rightContent;	// Panel fuer Punktestand Statistik etc...
	private JPanel gameContent;		// Spielablauf
	
	private final Dimension screenSize;
	private final Dimension frameSize;
	
	public MainFrame(){
		
		frameSize = new Dimension(800,600);
		screenSize = this.getToolkit().getScreenSize(); 
		
		this.setSize(frameSize);
		this.setName("Keyboard Hero");
		
		// Frame zentrieren
	    this.setLocation((int) ((screenSize.getWidth() - this.getWidth()) / 2), (int) ((screenSize.getHeight() - this.getHeight()) / 2));
		
	    wrapper = new JPanel();
	    
	    contentPanel = new JPanel();
	    contentPanel.setLayout(new BorderLayout());
	    contentPanel.add(this.buildLeftContent(), BorderLayout.WEST);
	    contentPanel.add(this.buildGameContent(), BorderLayout.CENTER);
	    contentPanel.add(this.buildRightContent(), BorderLayout.EAST);

	    wrapper.add(contentPanel);
	   	    
	    this.add(wrapper);
	    
	    this.setResizable(false);
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    this.setVisible(true);
	}
	
	public JPanel buildLeftContent(){
		leftContent = new JPanel();
	    leftContent.setBackground(Color.LIGHT_GRAY);
	    leftContent.setPreferredSize(new Dimension(frameSize.width/6,frameSize.height)); // 1/6 der Frame Size

	    leftContent.add(new JLabel("WEST"));
	    return leftContent;
	}
	
	public JPanel buildRightContent(){
		rightContent = new JPanel();
	    rightContent.setBackground(Color.LIGHT_GRAY);
	    rightContent.setPreferredSize(new Dimension(frameSize.width/6,frameSize.height)); // 1/6 der Frame Size
	    rightContent.add(new JLabel("EAST"));
	    return rightContent;
	}
	
	public JPanel buildGameContent(){
		gameContent = new JPanel();
	    gameContent.setBackground(Color.WHITE);
	    gameContent.setPreferredSize(new Dimension(frameSize.width/6*4,frameSize.height)); // 4/6 der Frame Size
	    gameContent.add(new JLabel("CENTER"));
	    return gameContent;
	}
	
}
