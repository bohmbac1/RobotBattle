package attacks;

import java.awt.Polygon;

import math.Line;
import math.Tuple;

import entities.Robot;

public class ChainsawAttackPosition implements AttackPosition {
	@Override
	public Polygon determinePoints(Robot robot, float timeElapsed) {
		Tuple<Float> frontMiddle = robot.frontMiddle();

		Tuple<Float> frontLeft = robot.frontLeftCorner();
		Tuple<Float> frontRight = robot.frontRightCorner();
		Line topLine = new Line(frontLeft, frontRight);
		
		Tuple<Float> bottomLeft = new Line(frontLeft, frontMiddle).midpoint();
		Tuple<Float> bottomRight = new Line(frontRight, frontMiddle).midpoint();
		
		float lineDirection = robot.getDirectionFacing();
		
		float height = topLine.getLength() * 3 / 2;
		float xDist = height * (float) Math.cos(Math.toRadians(lineDirection));
		float yDist = height * (float) Math.sin(Math.toRadians(lineDirection));
		
		Tuple<Float> topLeft = new Tuple<Float>(bottomLeft.x + xDist, bottomLeft.y + yDist);
		Tuple<Float> topRight = new Tuple<Float>(bottomRight.x + xDist, bottomRight.y + yDist);
		
		int[] xValues = new int[4];
		int[] yValues = new int[4];
		xValues[0] = Math.round(topLeft.x);
		yValues[0] = Math.round(topLeft.y);
		xValues[1] = Math.round(topRight.x);
		yValues[1] = Math.round(topRight.y);
		xValues[2] = Math.round(bottomRight.x);
		yValues[2] = Math.round(bottomRight.y);
		xValues[3] = Math.round(bottomLeft.x);
		yValues[3] = Math.round(bottomLeft.y);
		
		return new Polygon(xValues, yValues, 4);
	}
}
