package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import view.GuitarPane;
import model.StrokeKey;
import controller.GuitarStringListener;
import controller.player.MP3PlayerListener;

public class MainFrame extends JFrame implements KeyListener {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 8845013593437103736L;
	
	private JPanel wrapper;			// background Panel
	private JPanel contentPanel;	
	private JPanel leftContent;		// sidepanel for scores, songtitle ...
	private JPanel rightContent;	// sidepanel for scores, songtitle ...
	private JPanel gameContent;		// main game content
	private GuitarPane gp;
	
	private final Dimension screenSize;
	private final Dimension frameSize;
	
	private ArrayList<GuitarStringListener> guitarStringListener;
	
	public MainFrame(){
		
		frameSize = new Dimension(800,600);
		screenSize = this.getToolkit().getScreenSize(); 
		
		this.setSize(frameSize);
		this.setName("Keyboard Hero");
		
		// center mainframe
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
	    
	    
	    // key related
	    wrapper.addKeyListener(this);
	    wrapper.setFocusable(true);
	    guitarStringListener = new ArrayList<>();
	    
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
	    gp = new GuitarPane();
	    gp.setPreferredSize(gameContent.getPreferredSize());
	    gp.render();
	    gameContent.add(gp);
	    return gameContent;
	}
	
	
	// Key Handling
	
	public void addGuitarStringListener(GuitarStringListener listener) {
        this.guitarStringListener.add(listener);
    }

    public void removeGuitarStringListener(GuitarStringListener listener) {
    	this.guitarStringListener.remove(listener);
    }
	
	public void keyPressed(KeyEvent e) {
		StrokeKey strokeKey = StrokeKey.keyForCode(e.getKeyCode());
		if(strokeKey != StrokeKey.INVALID && strokeKey != StrokeKey.ENTER) {
			for (GuitarStringListener guitarStringListener : this.guitarStringListener) {
				guitarStringListener.guitarStringPressed(strokeKey);
	        }
		} else if(strokeKey == StrokeKey.ENTER) {
			for (GuitarStringListener guitarStringListener : this.guitarStringListener) {
				guitarStringListener.guitarStrokePressed(strokeKey);
	        }
		}
    }
	
    public void keyReleased(KeyEvent e) {
    	StrokeKey strokeKey = StrokeKey.keyForCode(e.getKeyCode());
		if(strokeKey != StrokeKey.INVALID && strokeKey != StrokeKey.ENTER) {
			for (GuitarStringListener guitarStringListener : this.guitarStringListener) {
				guitarStringListener.guitarStringReleased(strokeKey);
	        }
		} else if(strokeKey == StrokeKey.ENTER) {
			for (GuitarStringListener guitarStringListener : this.guitarStringListener) {
				guitarStringListener.guitarStrokeReleased(strokeKey);
	        }
		}
    }
    
    public void keyTyped(KeyEvent e) {
        
    }
	
}
