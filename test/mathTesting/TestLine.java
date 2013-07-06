package mathTesting;

import static org.junit.Assert.*;
import math.Line;
import math.Tuple;

import org.junit.Before;
import org.junit.Test;

public class TestLine {
	private Line line;
	public static final float alpha = (float) .0001;
	
	@Before
	public void setUp() {
		Tuple<Float> p1 = new Tuple<Float>((float) 0, (float) 0);
		Tuple<Float> p2 = new Tuple<Float>((float) 3, (float) 4);
		line = new Line(p1, p2);
	}
	
	@Test
	public void testSlope() {
		assertEquals(line.getSlope(), (float)4/3, alpha);
	}
	
	@Test 
	public void testYIntercept() {
		assertEquals(line.getYIntercept(), 0, alpha);
	}
	
	@Test
	public void testLength() {
		assertEquals(line.getLength(), 5, alpha);
	}

}
