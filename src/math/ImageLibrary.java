package math;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageLibrary {
	
	public static BufferedImage copyImage(BufferedImage original) {
		BufferedImage bi = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		for (int i = 0; i < bi.getWidth(); i++) {
			for (int j = 0; j < bi.getHeight(); j++) {
				bi.setRGB(i, j, original.getRGB(i, j));
			}
		}
		return bi;
	}
	
	/**
	 * Return the bottom image with the top image on top of it.
	 * @param bottom
	 * @param top
	 * @param x
	 * @param y
	 * @return
	 */
	public static BufferedImage overlayImages(BufferedImage bottom, BufferedImage top, int x, int y) {
		Graphics2D bottomGraphics = bottom.createGraphics();
		bottomGraphics.drawImage(top, x, y, null);
		return bottom;
	}

	/**
	 * Return a new BufferedImage that is this image rotated clockwise by the degree theta.
	 * @param image
	 * @param theta in degrees
	 * @return
	 */
	public static BufferedImage rotateImage(BufferedImage image, float theta) {
		int w = image.getWidth();  
        int h = image.getHeight(); 
        int hypoteneuse = (int) Math.ceil(Math.sqrt(Math.pow(w, 2) + Math.pow(h, 2)));
        BufferedImage dimg = new BufferedImage(hypoteneuse, hypoteneuse, image.getType());  
        Graphics2D g = dimg.createGraphics();  
        g.rotate(Math.toRadians(theta), hypoteneuse/2, hypoteneuse/2);  
        g.drawImage(image, null, (hypoteneuse - w) / 2, (hypoteneuse - h) / 2);  
        return dimg; 
	}
}
