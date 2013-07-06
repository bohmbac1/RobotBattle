package math;

import java.util.ArrayList;
import java.util.List;

public class Line {
	public Tuple<Float> point1;
	public Tuple<Float> point2;
	public static final float alpha = (float) .0001;

	public Line(Tuple<Float> p1, Tuple<Float> p2) {
		point1 = p1;
		point2 = p2;
	}

	public Line(Tuple<Float> point, float slope) {
		point1 = point;
		point2 = new Tuple<Float>(point1.x + 1, point1.y + slope);
	}

	public Tuple<Float> getPoint1() {
		return point1;
	}

	public Tuple<Float> getPoint2() {
		return point2;
	}

	public float getSlope() {
		if (Math.abs(point1.x - point2.x) < alpha) {
			return (float) Double.NaN;
		}
		return (point1.y - point2.y) / (point1.x - point2.x);
	}

	public float getYIntercept() {
		float slope = getSlope();
		if (Double.isNaN(slope)) {
			return (Float) null;
		}
		return point1.y - slope * point1.x;
	}

	public float getLength() {
		return (float) Math.sqrt(Math.pow(point2.x - point1.x, 2) + Math.pow(point2.y - point1.y, 2));
	}

	/**
	 * Find all the points on the line
	 * @param precision the gaps in the points on the line. If the slope is more horizonta
	 * @return
	 */
	public List<Tuple<Float>> pointsOnLine(float precision) {
		List<Tuple<Float>> retv = new ArrayList<Tuple<Float>>();
		float slope = getSlope();
		float yInt;
		if (Double.isNaN(slope)) {
			float startY = Math.min(point1.y, point2.y);
			while (startY > Math.max(point1.y, point2.y)) {
				float y = startY;
				float x = point1.x;
				retv.add(new Tuple<Float>(x, y));
				startY += precision;
			}
		}
		else {
			yInt = getYIntercept();
			if (Math.abs(slope) < 1) {
				float startX = Math.min(point1.x, point2.x);
				while (startX < Math.max(point1.x, point2.x)) {
					float x = startX;
					float y = slope * x + yInt;
					retv.add(new Tuple<Float>(x, y));
					startX += precision;
				}
			}
			else {
				float startY = Math.min(point1.y, point2.y);
				while (startY < Math.max(point1.y, point2.y)) {
					float y = startY;
					float x = (y - yInt) / slope;
					retv.add(new Tuple<Float>(x, y));
					startY += precision;
				}
			}
		}
		return retv;
	}

	public Tuple<Float> getPointByXValue(float x) {
		float y = getSlope() * x + getYIntercept();
		return new Tuple<Float>(x, y);
	}

	public float inverseSlope() {
		return -1 / getSlope();
	}

	public Tuple<Float> midpoint() {
		float midX = (point1.x + point2.x) / 2;
		float midY = (point1.y + point2.y) / 2;
		return new Tuple<Float>(midX, midY);
	}
}
