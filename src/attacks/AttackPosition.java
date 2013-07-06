package attacks;

import java.awt.Polygon;
import entities.Robot;

public interface AttackPosition {
	public Polygon determinePoints(Robot robot, float timeElapsed);
}
