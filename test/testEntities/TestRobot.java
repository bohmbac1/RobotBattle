package testEntities;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import math.Tuple;

import org.junit.Before;
import org.junit.Test;

import entities.Arena;
import entities.Robot;

public class TestRobot {
	public static final float alpha = (float) .001;
	public static final float directionAlpha = (float) 3;
	private Arena arena;
	private Robot standardRobot;
	
	@Before
	public void setUp() throws IOException {
		arena = new Arena(100, 100, "Images/testArena.png");
		standardRobot = new Robot("standard", 100, 0, 0, null, 0, 20, 20);
	}
	
	@Test
	public void testCornersFacingUp() {
		Robot r = new Robot("up", 100, 10, 10, new Point(10, 10), 270, 20, 20);
		List<Point> corners = r.corners();
		List<Point> correctCorners = new ArrayList<Point>();
		correctCorners.add(new Point((int) Math.round(10. - (float)r.getWidth() / 2), (int) Math.round(10. - (float)r.getHeight() / 2)));
		correctCorners.add(new Point((int) Math.round(10. - (float)r.getWidth() / 2), (int) Math.round(10. + (float)r.getHeight() / 2)));
		correctCorners.add(new Point((int) Math.round(10. + (float)r.getWidth() / 2), (int) Math.round(10. - (float)r.getHeight() / 2)));
		correctCorners.add(new Point((int) Math.round(10. + (float)r.getWidth() / 2), (int) Math.round(10. + (float)r.getHeight() / 2)));
		
		for (Point corner : corners) {
			for (Point correctCorner : correctCorners) {
				if (Math.abs(correctCorner.x - corner.x) < alpha && Math.abs(correctCorner.y - corner.y) < alpha) {
					correctCorners.remove(correctCorner);
					break;
				}
			}
		}
		
		assertEquals(correctCorners.size(), 0);
	}
	
	@Test
	public void testCornersFacingRight() {
		Robot r = new Robot("up", 100, 10, 10, new Point(10, 10), 0, 20, 20);
		List<Point> corners = r.corners();
		List<Point> correctCorners = new ArrayList<Point>();
		correctCorners.add(new Point((int) Math.round(10. - (float)r.getWidth() / 2), (int) Math.round(10. - (float)r.getHeight() / 2)));
		correctCorners.add(new Point((int) Math.round(10. - (float)r.getWidth() / 2), (int) Math.round(10. + (float)r.getHeight() / 2)));
		correctCorners.add(new Point((int) Math.round(10. + (float)r.getWidth() / 2), (int) Math.round(10. - (float)r.getHeight() / 2)));
		correctCorners.add(new Point((int) Math.round(10. + (float)r.getWidth() / 2), (int) Math.round(10. + (float)r.getHeight() / 2)));
		
		for (Point corner : corners) {
			for (Point correctCorner : correctCorners) {
				if (Math.abs(correctCorner.x - corner.x) < alpha && Math.abs(correctCorner.y - corner.y) < alpha) {
					correctCorners.remove(correctCorner);
					break;
				}
			}
		}
		
		assertEquals(correctCorners.size(), 0);
	}
	
	@Test
	public void testCornersFacingDown() {
		Robot r = new Robot("up", 100, 10, 10, new Point(10, 10), 90, 20, 20);
		List<Point> corners = r.corners();
		List<Point> correctCorners = new ArrayList<Point>();
		correctCorners.add(new Point((int) Math.round(10. - (float)r.getWidth() / 2), (int) Math.round(10. - (float)r.getHeight() / 2)));
		correctCorners.add(new Point((int) Math.round(10. - (float)r.getWidth() / 2), (int) Math.round(10. + (float)r.getHeight() / 2)));
		correctCorners.add(new Point((int) Math.round(10. + (float)r.getWidth() / 2), (int) Math.round(10. - (float)r.getHeight() / 2)));
		correctCorners.add(new Point((int) Math.round(10. + (float)r.getWidth() / 2), (int) Math.round(10. + (float)r.getHeight() / 2)));
		
		for (Point corner : corners) {
			for (Point correctCorner : correctCorners) {
				if (Math.abs(correctCorner.x - corner.x) < alpha && Math.abs(correctCorner.y - corner.y) < alpha) {
					correctCorners.remove(correctCorner);
					break;
				}
			}
		}
		
		assertEquals(correctCorners.size(), 0);
	}
	
	@Test
	public void testCornersFacingLeft() {
		Robot r = new Robot("up", 100, 10, 10, new Point(10, 10), 180, 20, 20);
		List<Point> corners = r.corners();
		List<Point> correctCorners = new ArrayList<Point>();
		correctCorners.add(new Point((int) Math.round(10. - (float)r.getWidth() / 2), (int) Math.round(10. - (float)r.getHeight() / 2)));
		correctCorners.add(new Point((int) Math.round(10. - (float)r.getWidth() / 2), (int) Math.round(10. + (float)r.getHeight() / 2)));
		correctCorners.add(new Point((int) Math.round(10. + (float)r.getWidth() / 2), (int) Math.round(10. - (float)r.getHeight() / 2)));
		correctCorners.add(new Point((int) Math.round(10. + (float)r.getWidth() / 2), (int) Math.round(10. + (float)r.getHeight() / 2)));
		
		for (Point corner : corners) {
			for (Point correctCorner : correctCorners) {
				if (Math.abs(correctCorner.x - corner.x) < alpha && Math.abs(correctCorner.y - corner.y) < alpha) {
					correctCorners.remove(correctCorner);
					break;
				}
			}
		}
		
		assertEquals(correctCorners.size(), 0);
	}
	
	@Test
	public void testCornersFirstQuadrant() {
		Robot r = new Robot("First quadrant", 100, 10, 10, new Point(10, 10), 315, 20, 20);
		List<Point> corners = r.corners();
		
		float hypoteneuse = (float) Math.sqrt(Math.pow((double)r.getWidth() / 2, 2) + Math.pow((double)r.getHeight() / 2, 2));

		List<Point> correctCorners = new ArrayList<Point>();
		correctCorners.add(new Point(10 + Math.round(hypoteneuse), 10));
		correctCorners.add(new Point(10, Math.round(hypoteneuse) + 10));
		correctCorners.add(new Point(10 - Math.round(hypoteneuse), 10));
		correctCorners.add(new Point(10, 10 - Math.round(hypoteneuse)));
		
		for (Point corner : corners) {
			for (Point correctCorner : correctCorners) {
				if (Math.abs(correctCorner.x - corner.x) < alpha && Math.abs(correctCorner.y - corner.y) < alpha) {
					correctCorners.remove(correctCorner);
					break;
				}
			}
		}
		
		assertEquals(correctCorners.size(), 0);
	}
	
	@Test
	public void testCornersSecondQuadrant() {
		Robot r = new Robot("second quadrant", 100, 10, 10, new Point(10, 10), 45, 20, 20);
		List<Point> corners = r.corners();
		
		float hypoteneuse = (float) Math.sqrt(Math.pow((double) r.getWidth() / 2, 2) + Math.pow((double) r.getHeight() / 2, 2));
		
		List<Point> correctCorners = new ArrayList<Point>();
		correctCorners.add(new Point(10, (int) Math.round(10. + hypoteneuse)));
		correctCorners.add(new Point(10, (int) Math.round(10. - hypoteneuse)));
		correctCorners.add(new Point((int) Math.round(10. - hypoteneuse), 10));
		correctCorners.add(new Point((int) Math.round(10. + hypoteneuse), 10));
		
		for (Point corner : corners) {
			for (Point correctCorner : correctCorners) {
				if (Math.abs(correctCorner.x - corner.x) < alpha && Math.abs(correctCorner.y - corner.y) < alpha) {
					correctCorners.remove(correctCorner);
					break;
				}
			}
		}
		
		assertEquals(correctCorners.size(), 0);
	}
	
	@Test
	public void testCornersThirdQuadrant() {
		Robot r = new Robot("third quadrant", 100, 10, 10, new Point(10, 10), 100, 20, 20);
		List<Point> corners = r.corners();
		
		float hypoteneuse = (float) Math.sqrt(Math.pow((double) r.getWidth() / 2, 2) + Math.pow((double) r.getHeight() / 2, 2));
		float[] degrees = new float[] {100 + 45, 100 + 135, 100 + 225, 100 - 45};
		
		List<Point> correctCorners = new ArrayList<Point>();
		for (float degree : degrees) {
			int xDiff = (int) Math.round(hypoteneuse * Math.cos(Math.toRadians(degree)));
			int yDiff = (int) Math.round(hypoteneuse * Math.sin(Math.toRadians(degree)));
			correctCorners.add(new Point(10 + xDiff, 10 + yDiff));
		}
		
		for (Point corner : corners) {
			for (Point correctCorner : correctCorners) {
				if (Math.abs(correctCorner.x - corner.x) < alpha && Math.abs(correctCorner.y - corner.y) < alpha) {
					correctCorners.remove(correctCorner);
					break;
				}
			}
		}
		
		assertEquals(correctCorners.size(), 0);
	}
	
	@Test
	public void testCornersFourthQuadrant() {
		Robot r = new Robot("fourth quadrant", 100, 10, 10, new Point(10, 10), 245, 20, 20);
		List<Point> corners = r.corners();
		
		float hypoteneuse = (float) Math.sqrt(Math.pow((double) r.getWidth() / 2, 2) + Math.pow((double) r.getHeight() / 2, 2));
				
		float[] degrees = new float[] {245 + 45, 245 - 45, 245 - 135, 245 - 225};
		
		List<Point> correctCorners = new ArrayList<Point>();
		for (float degree : degrees) {
			int xDiff = (int) Math.round(hypoteneuse * Math.cos(Math.toRadians(degree)));
			int yDiff = (int) Math.round(hypoteneuse * Math.sin(Math.toRadians(degree)));
			correctCorners.add(new Point(10 + xDiff, 10 + yDiff));
		}
		
		for (Point corner : corners) {
			for (Point correctCorner : correctCorners) {
				if (Math.abs(correctCorner.x - corner.x) < alpha && Math.abs(correctCorner.y - corner.y) < alpha) {
					correctCorners.remove(correctCorner);
					break;
				}
			}
		}
		
		assertEquals(correctCorners.size(), 0);
	}
	
	@Test
	public void testPositionsStraight() {
		Robot robot = new Robot("up", 100, 10, 10, null, 270, 10, 10);
		Tuple<Float> frontRight = robot.frontRightCorner();
		Tuple<Float> frontLeft = robot.frontLeftCorner();
		Tuple<Float> frontMiddle = robot.frontMiddle();
		
		float diff = 5;
		Tuple<Float> correctFrontRight = new Tuple<Float>(10 + diff, 10 - diff);
		Tuple<Float> correctFrontLeft = new Tuple<Float> (10 - diff, 10 - diff);
		Tuple<Float> correctFrontMiddle = new Tuple<Float> ((float) 10, (float) 5);
		
		assertEquals(correctFrontRight.x, frontRight.x);
		assertEquals(correctFrontRight.y, frontRight.y);
		assertEquals(correctFrontLeft.x, frontLeft.x);
		assertEquals(correctFrontLeft.y, frontLeft.y);
		assertEquals(correctFrontMiddle.x, frontMiddle.x);
		assertEquals(correctFrontMiddle.y, frontMiddle.y);
	}
	
	@Test
	public void testPositionsRight() {
		Robot robot = new Robot("up", 100, 10, 10, null, 0, 10, 10);
		Tuple<Float> frontRight = robot.frontRightCorner();
		Tuple<Float> frontLeft = robot.frontLeftCorner();
		Tuple<Float> frontMiddle = robot.frontMiddle();
		
		float diff = 5;
		Tuple<Float> correctFrontRight = new Tuple<Float>(10 + diff, 10 + diff);
		Tuple<Float> correctFrontLeft = new Tuple<Float> (10 + diff, 10 - diff);
		Tuple<Float> correctFrontMiddle = new Tuple<Float> ((float)10 + 5, (float)10);
		
		assertEquals(correctFrontRight.x, frontRight.x);
		assertEquals(correctFrontRight.y, frontRight.y);
		assertEquals(correctFrontLeft.x, frontLeft.x);
		assertEquals(correctFrontLeft.y, frontLeft.y);
		assertEquals(correctFrontMiddle.x, frontMiddle.x);
		assertEquals(correctFrontMiddle.y, frontMiddle.y);
	}

	@Test 
	public void testPositionsFourthQuad(){
		Robot robot = new Robot("up", 100, 10, 10, null, 225, 10, 10);
		Tuple<Float> frontRight = robot.frontRightCorner();
		Tuple<Float> frontLeft = robot.frontLeftCorner();
		Tuple<Float> frontMiddle = robot.frontMiddle();
		
		float diff = (float) (Math.sqrt(2) * 5);
		Tuple<Float> correctFrontRight = new Tuple<Float>((float) 10, 10 - diff);
		Tuple<Float> correctFrontLeft = new Tuple<Float> (10 - diff, (float) 10);
		Tuple<Float> correctFrontMiddle = new Tuple<Float> ((float) (5 / Math.sqrt(2) * -1 + 10),
				(float) (10 - (5 / Math.sqrt(2))));
		
		assertEquals(correctFrontRight.x, frontRight.x);
		assertEquals(correctFrontRight.y, frontRight.y);
		assertEquals(correctFrontLeft.x, frontLeft.x);
		assertEquals(correctFrontLeft.y, frontLeft.y);
		assertEquals(correctFrontMiddle.x, frontMiddle.x);
		assertEquals(correctFrontMiddle.y, frontMiddle.y);
	}
	
	@Test
	public void testFacingRight() {
		Robot r1 = new Robot("up", 100, 10, 10, null, 315, 10, 10);
		Robot r2 = new Robot("up", 100, 10, 10, null, 30, 10, 10);
		Robot r3 = new Robot("up", 100, 10, 10, null, 160, 10, 10);
		Robot r4 = new Robot("up", 100, 10, 10, null, 200, 10, 10);
		Robot directlyUp = new Robot("up", 100, 10, 10, null, 270, 10, 10);
		
		assertTrue(r1.isFacingRight());
		assertTrue(r2.isFacingRight());
		assertFalse(r3.isFacingRight());
		assertFalse(r4.isFacingRight());
		assertFalse(directlyUp.isFacingRight());
	}
	
	@Test
	public void testFrontLeftCorner() {
		Robot r1 = new Robot("up", 100, 5, 5, null, 270, 10, 10);
		Tuple<Float> frontLeftCorner = r1.frontLeftCorner();
		assertEquals(0, frontLeftCorner.x, alpha);
		assertEquals(0, frontLeftCorner.y, alpha);
	}
	
	@Test
	public void testFrontRightCorner() {
		Robot r1 = new Robot("up", 100, 5, 5, null, 270, 10, 10);
		Tuple<Float> frontRightCorner = r1.frontRightCorner();
		assertEquals(10, frontRightCorner.x, alpha);
		assertEquals(0, frontRightCorner.y, alpha);
	}
	
	@Test
	public void testArenaCenterDirectionLeftCorner() {
		Robot r1 = new Robot("r", 100, 0, 0, null, 0, 10, 10);
		assertEquals(45, r1.arenaCenterDirection(arena), directionAlpha);
	}
	
	@Test
	public void testArenaCenterDirectionLeftSide() {
		standardRobot.setXPos(0);
		standardRobot.setYPos(arena.getHeight() / 2 - 1);
		assertEquals(0, standardRobot.arenaCenterDirection(arena), directionAlpha);
	}
	
	@Test
	public void testArenaCenterDirectionBottomLeft() {
		standardRobot.setXPos(arena.getWidth() / 4 - 1);
		standardRobot.setYPos(arena.getHeight() - 1);
		assertEquals(Math.toDegrees(Math.atan(arena.getHeight() / (arena.getWidth() / 2))) * -1,
				standardRobot.arenaCenterDirection(arena), directionAlpha);
	}
	
	@Test
	public void testArenaCenterRightSide() {
		standardRobot.setXPos(arena.getWidth() - 1);
		standardRobot.setYPos(arena.getHeight() / 2 - 1);
		assertEquals(180, standardRobot.arenaCenterDirection(arena), directionAlpha);
	}
	
	@Test
	public void testArenaCenterRightBottom() {
		standardRobot.setXPos(arena.getWidth() - 1);
		standardRobot.setYPos(arena.getHeight() - 1);
		assertEquals(225, standardRobot.arenaCenterDirection(arena), directionAlpha);
	}
	
	@Test 
	public void testArenaCenterRightTop() {
		standardRobot.setXPos(arena.getWidth() - 1);
		standardRobot.setYPos(0);
		assertEquals(135, standardRobot.arenaCenterDirection(arena), directionAlpha);
	}
}
