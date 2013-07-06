package testEntities;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import math.ImageLibrary;

import org.junit.Test;

public class TestImageLibrary {

	@Test
	public void testCopyImage() throws IOException {
		BufferedImage image = ImageIO.read(new File("Images/Arena.jpg"));
		BufferedImage coppied = ImageLibrary.copyImage(image);
		assertEquals(image.getHeight(), coppied.getHeight());
		assertEquals(image.getWidth(), coppied.getWidth());
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				assertEquals(image.getRGB(i, j), coppied.getRGB(i, j));
			}
		}
	}

	@Test
	public void testRotateImage() {
		try {
			BufferedImage image = ImageIO.read(new File("Images/Arena.jpg"));
			ImageLibrary.rotateImage(image, 45);
			ImageIO.write(image, "jpg", new File("Images/RotatedArena.jpg"));
		}
		catch (IOException e) {
			System.out.println("exception in rotate image");
		}
	}

	@Test
	public void testOverLayImages() {
		try {
			BufferedImage image = ImageIO.read(new File("Images/Arena.jpg"));
			BufferedImage over = new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR);
			for (int i = 0; i < over.getWidth(); i++) {
				for (int j = 0; j < over.getHeight(); j++) {
					over.setRGB(i, j, Color.yellow.getRGB());
				}
			}

			image = ImageLibrary.overlayImages(image, over, 10, 10);
			ImageIO.write(over, "jpg", new File("Images/over.jpg"));
			ImageIO.write(image, "jpg", new File("Images/OverlayedArena.jpg"));
		}
		catch (IOException e) {
			System.out.println("exception in overlayimages");
		}
	}
	
	@Test 
	public void testOverlay2() throws IOException {
		BufferedImage bottom = ImageIO.read(new File("Images/Arena.jpg"));
		BufferedImage top = new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR);
		for (int i = 0; i < top.getWidth(); i++) {
			for (int j = 0; j < top.getHeight(); j++) {
				top.setRGB(i, j, 0);
			}
		}
		Graphics bottomGraphics = bottom.getGraphics();
		bottomGraphics.drawImage(top, 0, 0, null);
		
		ImageIO.write(bottom, "jpg", new File("Images/OverlayedAreana2.jpg"));
	}
}
