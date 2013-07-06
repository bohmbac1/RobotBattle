package attacks;

import java.awt.Point;
import java.awt.Polygon;

import entities.Robot;

public class ElectricFieldAttackPosition implements AttackPosition {

	@Override
	public Polygon determinePoints(Robot robot, float timeElapsed) {
		Point position = robot.getPosition();
		int totalRadius = 100;
		float totalTime = 2;
		float currRadius = (float) totalRadius * timeElapsed / totalTime;
		
		int numPoints = 32;
		int[] xPoints = new int[numPoints];
		int[] yPoints = new int[numPoints];
		
		for (int i = 0; i < numPoints; i++) {
			float degree = (float) 360 / numPoints * i;
			
			int xDist = Math.round(currRadius * (float) Math.cos(Math.toRadians(degree)));
			int yDist = Math.round(currRadius * (float) Math.sin(Math.toRadians(degree)));
			
			xPoints[i] = position.x + xDist;
			yPoints[i] = position.y + yDist;
		}
		
		return new Polygon(xPoints, yPoints, numPoints);
	}

}
