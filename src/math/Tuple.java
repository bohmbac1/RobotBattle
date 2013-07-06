package math;

/**
 * 
 * @author stantonbohmbach
 *
 */
public class Tuple<T> {
	public T x;
	public T y;
	
	public Tuple(T x, T y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Tuple<T> other) {
		return this.x == other.x && this.y == other.y;
	}
}
