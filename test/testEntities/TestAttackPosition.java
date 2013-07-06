package testEntities;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import math.ImageLibrary;
import math.Tuple;

import org.junit.Before;
import org.junit.Test;

import attacks.AttackPosition;
import attacks.ChainsawAttackPosition;

import translation.ArenaToView;
import translation.View;

import entities.Arena;
import entities.Attack;
import entities.Robot;

public class TestAttackPosition {
	/*
	private BufferedImage image;
	private Arena arena;
	
	@Before
	public void setUp() throws IOException {
		arena = new Arena(100, 100, "test arena");
		image = ImageIO.read(new File("Images/Arena.jpg"));
	}
	
	@Test
	public void testChainsawStraight() {
		Robot robot = new Robot("robot", 100, 20, 20, new Point(2, 3), 270);
		robot.setColor(0);
		ChainsawAttackPosition chainsaw = new ChainsawAttackPosition();
		BufferedImage rotatedRobot = ImageLibrary.rotateImage(robot.getImage(), robot.getDirectionFacing() - 270);
		image = ImageLibrary.overlayImages(image, rotatedRobot,
				(int) (robot.getxPos() - rotatedRobot.getWidth() / 2), 
				(int) (robot.getyPos() - rotatedRobot.getHeight() / 2));
		robot.performAttack(new Attack("chainsaw", Color.GRAY, new ChainsawAttackPosition(), 7, 7));
		for (Point point : robot.attackPoints()) {
			if (arena.containsPoint(point)) {
				image.setRGB(Math.round(point.x), Math.round(point.y), Color.yellow);
			}
		}
		try {
			ImageIO.write(image, "jpg", new File("Images/chainsawStraight.jpg"));
		}
		catch (Exception e) {
			System.out.println("exception in chainsaw drawing");
		}
	}
	
	@Test 
	public void testChainsawRight() {
		Robot robot = new Robot("robot", 100, 20, 20, new Tuple<Float>(new Float(2), new Float(3)), 0);
		robot.setColor(0);
		ChainsawAttackPosition chainsaw = new ChainsawAttackPosition();
		BufferedImage rotatedRobot = ImageLibrary.rotateImage(robot.getImage(), robot.getDirectionFacing() - 270);
		image = ImageLibrary.overlayImages(image, rotatedRobot,
				(int) (robot.getxPos() - rotatedRobot.getWidth() / 2), 
				(int) (robot.getyPos() - rotatedRobot.getHeight() / 2));
		Color attackColor = Color.GRAY;
		for (Tuple<Float> point : chainsaw.determinePoints(robot, 0)) {
			System.out.println("(" + point.x + ", " + point.y + ")");
			if (arena.containsPoint(point)) {
				image.setRGB(Math.round(point.x), Math.round(point.y), attackColor.getRGB());
			}
		}
		try {
			ImageIO.write(image, "jpg", new File("Images/chainsawRight.jpg"));
		}
		catch (Exception e) {
			System.out.println("exception in chainsaw drawing");
		}
	}
	
	@Test
	public void testChainsawDown() {
		Robot robot = new Robot("robot", 100, 20, 20, new Tuple<Float>(new Float(2), new Float(3)), 90);
		robot.setColor(0);
		ChainsawAttackPosition chainsaw = new ChainsawAttackPosition();
		BufferedImage rotatedRobot = ImageLibrary.rotateImage(robot.getImage(), robot.getDirectionFacing() - 270);
		image = ImageLibrary.overlayImages(image, rotatedRobot,
				(int) (robot.getxPos() - rotatedRobot.getWidth() / 2), 
				(int) (robot.getyPos() - rotatedRobot.getHeight() / 2));
		Color attackColor = Color.GRAY;
		for (Tuple<Float> point : chainsaw.determinePoints(robot, 0)) {
			System.out.println("(" + point.x + ", " + point.y + ")");
			if (arena.containsPoint(point)) {
				image.setRGB(Math.round(point.x), Math.round(point.y), attackColor.getRGB());
			}
		}
		try {
			ImageIO.write(image, "jpg", new File("Images/chainsawDown.jpg"));
		}
		catch (Exception e) {
			System.out.println("exception in chainsaw drawing");
		}
	}
	
	@Test
	public void testChainsawLeft() {
		Robot robot = new Robot("robot", 100, 20, 20, new Tuple<Float>(new Float(2), new Float(3)), 180);
		robot.setColor(0);
		ChainsawAttackPosition chainsaw = new ChainsawAttackPosition();
		BufferedImage rotatedRobot = ImageLibrary.rotateImage(robot.getImage(), robot.getDirectionFacing() - 270);
		image = ImageLibrary.overlayImages(image, rotatedRobot,
				(int) (robot.getxPos() - rotatedRobot.getWidth() / 2), 
				(int) (robot.getyPos() - rotatedRobot.getHeight() / 2));
		Color attackColor = Color.GRAY;
		for (Tuple<Float> point : chainsaw.determinePoints(robot, 0)) {
			System.out.println("(" + point.x + ", " + point.y + ")");
			if (arena.containsPoint(point)) {
				image.setRGB(Math.round(point.x), Math.round(point.y), attackColor.getRGB());
			}
		}
		try {
			ImageIO.write(image, "jpg", new File("Images/chainsawLeft.jpg"));
		}
		catch (Exception e) {
			System.out.println("exception in chainsaw drawing");
		}
	}
	*/
}
