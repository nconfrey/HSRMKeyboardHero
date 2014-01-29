package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class InfoPanel extends JPanel{

	private JLabel label;
	
	public InfoPanel() {
		setOpaque(false);
		setBackground(new Color(50,50,50,195));
		setLayout(new MigLayout("fill", "[center]", "[center]"));
		
		label = new JLabel();
		label.setForeground(Color.white);
		setFontSize(40);
		
		add(label);
	}
	
	public JLabel getLabel() {
		return label;
	}
	
	public void setText(String text) {
		getLabel().setText(text);
	}
	
	public void setFontSize(int size) {
		getLabel().setFont(new Font("sanserif", Font.BOLD, size));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g);
	}
	
}
