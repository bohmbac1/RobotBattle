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

public class ElectricFieldSimulation {
	public static void main (String[] args) {
		Arena arena = null;
		AttackLibrary attackLibrary = new AttackLibrary();
		try {
			arena = new Arena(400, 400, "Images/Arena.jpg");
		} catch (IOException e) {
			System.out.println("arena could not be created.");
			System.exit(1);
		}

		Robot robot = new Robot("robot", 100, (arena.getWidth() / 2) ,arena.getHeight() / 2, new Point(20, 25), 270, 20, 20);
		arena.addRobot(robot);

		View view = new View();
		ArenaToView arenaToView = new ArenaToView(arena, view);
		
		Function1 performElectricField = new Function1(new Object[] {attackLibrary, robot}) {
			@Override
			public void execute() {
				((Robot)args[1]).performAttack(((AttackLibrary)args[0]).attackByName("electric field"));
			}
		};
		Thread performAttack = new Thread(new FunctionEverySomeSeconds(performElectricField, 3));
		performAttack.start();

		Thread arenaMotion = new Thread(new ArenaMotion(arenaToView));
		arenaMotion.start();

		arena.startSimulation();
	}
}
