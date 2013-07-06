package simulation;

import java.awt.Point;
import java.io.IOException;

import threads.ArenaMotion;
import threads.FunctionEverySomeSeconds;
import translation.ArenaToView;
import translation.View;

import attacks.AttackLibrary;

import entities.Arena;
import entities.Function1;
import entities.Robot;

public class BlowtorchSimulation {
	public static void main (String[] args) {
		Arena arena = null;
		AttackLibrary attackLibrary = new AttackLibrary();
		try {
			arena = new Arena(400, 400, "Images/Arena.jpg");
		} catch (IOException e) {
			System.out.println("arena could not be created.");
			System.exit(1);
		}

		float divider = (float) arena.getWidth() / 10;

		// set up 8 robots going in the y direction. each faces a different quadrant, including the exact directions
		for (int i = 1; i < 9; i++) {
			Robot robot = new Robot("robot " + i, 100, (int)(divider * i), 10, new Point(0, i * 4), (float) (45 * i), 20, 20);
			robot.setColorByInt(-1677721 * i);
			Function1 performAttack = new Function1(new Object[] {attackLibrary, robot}) {
				@Override
				public void execute() {
					((Robot)args[1]).performAttack(((AttackLibrary)args[0]).attackByName("blowtorch"));
				}
			};
			Thread attackThread = new Thread(new FunctionEverySomeSeconds(performAttack, 1));
			attackThread.start();
			arena.addRobot(robot);
		}


		View view = new View();
		ArenaToView arenaToView = new ArenaToView(arena, view);

		Thread arenaMotion = new Thread(new ArenaMotion(arenaToView));
		arenaMotion.start();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		arena.startSimulation();
	}
}
