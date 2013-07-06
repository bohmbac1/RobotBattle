package attacks;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import math.Tuple;

import entities.Robot;

public class BlowtorchAttackPosition implements AttackPosition {

	@Override
	public Polygon determinePoints(Robot robot, float timeElapsed) {
		Tuple<Float> front = robot.frontMiddle();
		
		float height = robot.getHeight() * 3;
		float attackDirection = robot.getDirectionFacing();
		float rightSideDirection = (attackDirection + 30) % 360;
		float leftSideDirection = (attackDirection - 30) % 360;
		
		List<Tuple<Float>> polygonPoints = new ArrayList<Tuple<Float>>();
		
		Tuple<Float> top = new Tuple<Float>(front.x + height * (float) Math.cos(Math.toRadians(attackDirection)),
				front.y + height * (float) Math.sin(Math.toRadians(attackDirection)));
		Tuple<Float> right = new Tuple<Float>(front.x + height * (float) Math.cos(Math.toRadians(rightSideDirection)),
				front.y + height * (float) Math.sin(Math.toRadians(rightSideDirection)));
		Tuple<Float> left = new Tuple<Float>(front.x + height * (float) Math.cos(Math.toRadians(leftSideDirection)),
				front.y + height * (float) Math.sin(Math.toRadians(leftSideDirection)));
		
		polygonPoints.add(front);
		polygonPoints.add(left);
		polygonPoints.add(top);
		polygonPoints.add(right);
		
		int[] xPoints = new int[4], yPoints = new int[4];
		
		for (int i = 0; i < polygonPoints.size(); i++) {
			Tuple<Float> point = polygonPoints.get(i);
			xPoints[i] = Math.round(point.x);
			yPoints[i] = Math.round(point.y);
		}
		
		return new Polygon(xPoints, yPoints, polygonPoints.size());
	}

}
