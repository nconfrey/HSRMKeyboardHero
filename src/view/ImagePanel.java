package view;

import javax.swing.JPanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.*;

import javax.imageio.*;

public class ImagePanel extends JPanel {
	
	public static final int SIZE_FIXED = 0;
	public static final int SIZE_FILL = 1;

	private BufferedImage coverImage;
	private int sizeMode;
	
	
	public ImagePanel(String path, int mode){
		this.sizeMode = mode;
		this.setBackground(Color.WHITE);
		
		try {
			coverImage = ImageIO.read(getClass().getResourceAsStream("/"+path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void setCoverImage(BufferedImage coverImage) {
		this.coverImage = coverImage;
		
		validate();
		repaint();
	}
	
	private double getScaleFactor(int iMasterSize, int iTargetSize) {

	    double dScale = 1;
	    if (iMasterSize > iTargetSize) {
	        dScale = (double) iTargetSize / (double) iMasterSize;
	    } else {
	        dScale = (double) iTargetSize / (double) iMasterSize;
	    }

	    return dScale;
	}

	private double getScaleFactorToFill(Dimension masterSize, Dimension targetSize) {

	    double dScaleWidth = getScaleFactor(masterSize.width, targetSize.width);
	    double dScaleHeight = getScaleFactor(masterSize.height, targetSize.height);

	    double dScale = Math.max(dScaleHeight, dScaleWidth);

	    return dScale;

	}

	@Override
	protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	    
	    if(this.coverImage == null) return;
	    
	    if(this.sizeMode == SIZE_FILL) {
	    	double scaleFactor = Math.max(1d, getScaleFactorToFill(new Dimension(coverImage.getWidth(), coverImage.getHeight()), getSize()));

		    int scaleWidth = (int) Math.round(coverImage.getWidth() * scaleFactor);
		    int scaleHeight = (int) Math.round(coverImage.getHeight() * scaleFactor);

		    Image scaled = coverImage.getScaledInstance(scaleWidth, scaleHeight, Image.SCALE_SMOOTH);

		    int width = getWidth() - 1;
		    int height = getHeight() - 1;

		    int x = (width - scaled.getWidth(this)) / 2;
		    int y = (height - scaled.getHeight(this)) / 2;

		    g.drawImage(scaled, x, y, this);
	    } else {
	    	
	    	int x = Math.max((getWidth() - 1 - coverImage.getWidth()) / 2, 0);
	    	g.drawImage(coverImage, x, 0, this);
	    	
	    }
	    
	    
	}
}
