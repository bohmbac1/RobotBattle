package threads;

import java.io.IOException;

import translation.ArenaToView;

public class ArenaMotion implements Runnable {
	private ArenaToView arenaToView;
	
	public ArenaMotion(ArenaToView arenaToView) {
		this.arenaToView = arenaToView;
	}
		
	public void run() {
		while (true) {
			try {
				arenaToView.getArena().moveRobots();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				arenaToView.updateView();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			arenaToView.getView().display();
		}
	}
}