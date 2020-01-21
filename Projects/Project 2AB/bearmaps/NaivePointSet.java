package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {
    List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point target = new Point(x, y);
        Point min = null;
        double min_distance = -1;
        double distance;
        for (Point p : this.points) {
            distance = Point.distance(target, p);
            if (min == null || distance < min_distance) {
                min = p;
                min_distance = distance;
            }
        }
        return min;
    }
}
