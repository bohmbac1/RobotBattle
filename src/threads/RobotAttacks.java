package threads;

import java.util.List;

import entities.Arena;
import entities.Robot;

/**
 * Every 10 seconds a random robot will perform one of their attacks.
 * 
 * @author stantonbohmbach
 *
 */
public class RobotAttacks implements Runnable {
	private Arena arena;
	
	public RobotAttacks(Arena arena) {
		this.arena = arena;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("RobotAttacks sleeping");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				System.out.println("InterruptedException occurd sleeping in RobotAttacks");
				return;
			}
			List<Robot> robots = arena.getAliveRobots();
			int robotIndex = (int) (Math.random() * robots.size());
			robots.get(robotIndex).performRandomAttack();
			System.out.println(robots.get(robotIndex).getName() + " num(" + robotIndex + ") performing " 
					+ robots.get(robotIndex).getAttackPerforming().getName());
		}
	}

}
