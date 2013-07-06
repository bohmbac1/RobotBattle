package entities;

/**
 * Interface for executing functions.
 * 
 * @author stantonbohmbach
 */
public abstract class Function1 {
	protected Object[] args;
	
	public Function1(Object[] args) {
		this.args = args;
	}
	
	public abstract void execute();
}
