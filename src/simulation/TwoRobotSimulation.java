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

public class TwoRobotSimulation {
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
		Robot r2 = new Robot("r2", 200, 10, 10, new Point(5, 5), 45, 10, 10);
		arena.addRobot(r2);
		Robot r3 = new Robot("Stanton", 1000, 300, 45, new Point(45, 10), 270, "Images/IRobot.png");
		arena.addRobot(r3);
		
		View view = new View();
		
		System.out.println("view created");
		ArenaToView arenaToView = new ArenaToView(arena, view);
		System.out.println("arena to view created");
		
		Function1 performElectricField = new Function1(new Object[] {attackLibrary, robot}) {
			@Override
			public void execute() {
				((Robot)args[1]).performAttack(((AttackLibrary)args[0]).attackByName("electric field"));
			}
		};
		Thread electricFieldAttack = new Thread(new FunctionEverySomeSeconds(performElectricField, 10));
		electricFieldAttack.start();
		
		Function1 performBlowTorch = new Function1(new Object[] {attackLibrary, r2}) {
			@Override
			public void execute() {
				((Robot)args[1]).performAttack(((AttackLibrary)args[0]).attackByName("blowtorch"));
			}
		};
		Thread blowTorchAttack = new Thread(new FunctionEverySomeSeconds(performBlowTorch, 5));
		blowTorchAttack.start();
		
		Function1 performChainsaw = new Function1(new Object[] {attackLibrary, r3}) {
			@Override
			public void execute() {
				((Robot)args[1]).performAttack(((AttackLibrary)args[0]).attackByName("chainsaw"));
			}
		};
		Thread startStanton = new Thread(new FunctionEverySomeSeconds(performChainsaw, 5));
		startStanton.start();
		
		System.out.println("blowtorch started");

		Thread arenaMotion = new Thread(new ArenaMotion(arenaToView));
		arenaMotion.start();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		arena.startSimulation();
		
		try {
			arenaMotion.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}