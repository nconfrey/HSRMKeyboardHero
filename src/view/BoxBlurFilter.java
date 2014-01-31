/**
 * A Blurring Filter that blurs an Image
 * 
 * Copyright 2005 Huxtable.com. All rights reserved.
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 * @author http://www.jhlabs.com/ip/blurring.html
 * 
 **/
package view;

import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class BoxBlurFilter {

	private int hRadius = 40;
	private int vRadius = 40;
	private int iterations = 2;

	/**
	 * Filter.
	 * 
	 * @param src the src
	 * @param dst the dst
	 * @return the buffered image
	 */
	public BufferedImage filter(BufferedImage src, BufferedImage dst) {
		int width = src.getWidth();
		int height = src.getHeight();

		if (dst == null)
			dst = createCompatibleDestImage(src, null);

		int[] inPixels = new int[width * height];
		int[] outPixels = new int[width * height];
		getRGB(src, 0, 0, width, height, inPixels);

		for (int i = 0; i < iterations; i++) {
			blur(inPixels, outPixels, width, height, hRadius);
			blur(outPixels, inPixels, height, width, vRadius);
		}

		setRGB(dst, 0, 0, width, height, inPixels);
		return dst;
	}

	/**
	 * Blur.
	 * 
	 * @param in the in
	 * @param out the out
	 * @param width the width
	 * @param height the height
	 * @param radius the radius
	 */
	public void blur(int[] in, int[] out, int width, int height, int radius) {
		int widthMinus1 = width - 1;
		int tableSize = 2 * radius + 1;
		int divide[] = new int[256 * tableSize];

		for (int i = 0; i < 256 * tableSize; i++)
			divide[i] = i / tableSize;

		int inIndex = 0;

		for (int y = 0; y < height; y++) {
			int outIndex = y;
			int ta = 0, tr = 0, tg = 0, tb = 0;

			for (int i = -radius; i <= radius; i++) {
				int rgb = in[inIndex + clamp(i, 0, width - 1)];
				ta += (rgb >> 24) & 0xff;
				tr += (rgb >> 16) & 0xff;
				tg += (rgb >> 8) & 0xff;
				tb += rgb & 0xff;
			}

			for (int x = 0; x < width; x++) {
				out[outIndex] = (divide[ta] << 24) | (divide[tr] << 16)
						| (divide[tg] << 8) | divide[tb];

				int i1 = x + radius + 1;
				if (i1 > widthMinus1)
					i1 = widthMinus1;
				int i2 = x - radius;
				if (i2 < 0)
					i2 = 0;
				int rgb1 = in[inIndex + i1];
				int rgb2 = in[inIndex + i2];

				ta += ((rgb1 >> 24) & 0xff) - ((rgb2 >> 24) & 0xff);
				tr += ((rgb1 & 0xff0000) - (rgb2 & 0xff0000)) >> 16;
				tg += ((rgb1 & 0xff00) - (rgb2 & 0xff00)) >> 8;
				tb += (rgb1 & 0xff) - (rgb2 & 0xff);
				outIndex += height;
			}
			inIndex += width;
		}
	}

	/**
	 * Sets the h radius.
	 * 
	 * @param hRadius the new h radius
	 */
	public void setHRadius(int hRadius) {
		this.hRadius = hRadius;
	}

	/**
	 * Gets the h radius.
	 * 
	 * @return the h radius
	 */
	public int getHRadius() {
		return hRadius;
	}

	/**
	 * Sets the v radius.
	 * 
	 * @param vRadius the new v radius
	 */
	public void setVRadius(int vRadius) {
		this.vRadius = vRadius;
	}

	/**
	 * Gets the v radius.
	 * 
	 * @return the v radius
	 */
	public int getVRadius() {
		return vRadius;
	}

	/**
	 * Sets the radius.
	 * 
	 * @param radius the new radius
	 */
	public void setRadius(int radius) {
		this.hRadius = this.vRadius = radius;
	}

	/**
	 * Gets the radius.
	 * 
	 * @return the radius
	 */
	public int getRadius() {
		return hRadius;
	}

	/**
	 * Sets the iterations.
	 * 
	 * @param iterations the new iterations
	 */
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	/**
	 * Gets the iterations.
	 * 
	 * @return the iterations
	 */
	public int getIterations() {
		return iterations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Blur/Box Blur...";
	}

	/**
	 * Creates the compatible dest image.
	 * 
	 * @param src the src
	 * @param dstCM the dst cm
	 * @return the buffered image
	 */
	public BufferedImage createCompatibleDestImage(BufferedImage src,
			ColorModel dstCM) {
		if (dstCM == null)
			dstCM = src.getColorModel();
		return new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(
				src.getWidth(), src.getHeight()), dstCM.isAlphaPremultiplied(),
				null);
	}

	/**
	 * Gets the bounds2 d.
	 * 
	 * @param src the src
	 * @return the bounds2 d
	 */
	public Rectangle2D getBounds2D(BufferedImage src) {
		return new Rectangle(0, 0, src.getWidth(), src.getHeight());
	}

	/**
	 * Gets the point2 d.
	 * 
	 * @param srcPt the src pt
	 * @param dstPt the dst pt
	 * @return the point2 d
	 */
	public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
		if (dstPt == null)
			dstPt = new Point2D.Double();
		dstPt.setLocation(srcPt.getX(), srcPt.getY());
		return dstPt;
	}

	/**
	 * Gets the rendering hints.
	 * 
	 * @return the rendering hints
	 */
	public RenderingHints getRenderingHints() {
		return null;
	}

	/**
	 * A convenience method for getting ARGB pixels from an image. This tries to
	 * avoid the performance penalty of BufferedImage.getRGB unmanaging the
	 * image.
	 * 
	 * @param image the image
	 * @param x the x
	 * @param y the y
	 * @param width the width
	 * @param height the height
	 * @param pixels the pixels
	 * @return the rgb
	 */
	public int[] getRGB(BufferedImage image, int x, int y, int width,
			int height, int[] pixels) {
		int type = image.getType();
		if (type == BufferedImage.TYPE_INT_ARGB
				|| type == BufferedImage.TYPE_INT_RGB)
			return (int[]) image.getRaster().getDataElements(x, y, width,
					height, pixels);
		return image.getRGB(x, y, width, height, pixels, 0, width);
	}

	/**
	 * A convenience method for setting ARGB pixels in an image. This tries to
	 * avoid the performance penalty of BufferedImage.setRGB unmanaging the
	 * image.
	 * 
	 * @param image the image
	 * @param x the x
	 * @param y the y
	 * @param width the width
	 * @param height the height
	 * @param pixels the pixels
	 */
	public void setRGB(BufferedImage image, int x, int y, int width,
			int height, int[] pixels) {
		int type = image.getType();
		if (type == BufferedImage.TYPE_INT_ARGB
				|| type == BufferedImage.TYPE_INT_RGB)
			image.getRaster().setDataElements(x, y, width, height, pixels);
		else
			image.setRGB(x, y, width, height, pixels, 0, width);
	}

	/**
	 * Clamp a value to an interval.
	 * 
	 * @param x the input parameter
	 * @param a the lower clamp threshold
	 * @param b the upper clamp threshold
	 * @return the clamped value
	 */
	public int clamp(int x, int a, int b) {
		return (x < a) ? a : (x > b) ? b : x;
	}
}
