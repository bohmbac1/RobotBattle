package math;

import com.sun.tools.javac.util.*;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: stantonbohmbach
 * Date: 7/15/13
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class PolygonUtils {
    public static List<Point> getPointsInPolygon(Polygon polygon) {
        List<Point> retv = new ArrayList<Point>();
        Rectangle bounds = polygon.getBounds();

        for (int i = 0; i < bounds.width; i++) {
            for (int j = 0; j < bounds.height; j++) {
                Point point = new Point(bounds.x + i, bounds.y + j);
                if (polygon.contains(point)) {
                    retv.add(point);
                }
            }
        }
        return retv;
    }
}
