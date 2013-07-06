package threads;

import entities.Function1;

/**
 * Run a function every some seconds. 
 * @author stantonbohmbach
 *
 */
public class FunctionEverySomeSeconds implements Runnable {
	private Function1 function;
	private float seconds;
	
	/**
	 * The function will run, and then wait for the amount of seconds, then run again.
	 * 
	 * @param func A function to be executed
	 * @param seconds The time in seconds to wait in between executions.
	 */
	public FunctionEverySomeSeconds(Function1 func, float seconds) {
		this.function = func;
		this.seconds = seconds;
	}
	
	@Override
	public void run() {
		do {
			function.execute();
			try {
				Thread.sleep((long) (seconds * 1000));
			} catch (InterruptedException e) {
				System.out.println("error running function.");
				return;
			}
		} while (true);
	}

}
