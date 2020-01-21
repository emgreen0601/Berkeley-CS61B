package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private Node root;

    private class Node {
        private Point p;
        private Node left, right;
        boolean x_based;

        public Node(Point p, boolean x_based) {
            this.p = p;
            this.x_based = x_based;
            this.left = null;
            this.right = null;
        }

        public Point getPoint() {
            return p;
        }

        public double getX() {
            return p.getX();
        }

        public double getY() {
            return p.getY();
        }
    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = put(p, root, true);
        }
    }

    private Node put(Point p, Node n, Boolean x_based) {
        if (n == null) {
            return new Node(p, x_based);
        }
        if (n.x_based) {
            if (Double.compare(n.getX(), p.getX()) < 0) {
                n.right = put(p, n.right, false);
            } else {
                n.left = put(p, n.left, false);
            }
        } else {
            if (Double.compare(n.getY(), p.getY()) < 0) {
                n.right = put(p, n.right, true);
            } else {
                n.left = put(p, n.left, true);
            }
        }
        return n;
    }

    @Override
    public Point nearest(double x, double y) {
        Point target = new Point(x, y);
        return nearest(root, target, root.getPoint());
    }

    private Point naive_nearest(Node n, Point target, Point min) {
        if (n == null) {
            return min;
        }
        if (Point.distance(n.getPoint(), target) < Point.distance(min, target)) {
            min = n.getPoint();
        }
        if (n.x_based) {
            if (Double.compare(n.getX(), target.getX()) < 0) {
                min = naive_nearest(n.right, target, min);
                min = naive_nearest(n.left, target, min);
            } else {
                min = naive_nearest(n.left, target, min);
                min = naive_nearest(n.right, target, min);
            }
        } else {
            if (Double.compare(n.getY(), target.getY()) < 0) {
                min = naive_nearest(n.right, target, min);
                min = naive_nearest(n.left, target, min);
            } else {
                min = naive_nearest(n.left, target, min);
                min = naive_nearest(n.right, target, min);
            }
        }
        return min;
    }

    private Point nearest(Node n, Point target, Point min) {
        if (n == null) {
            return min;
        }

        if (Double.compare(Point.distance(n.getPoint(), target), Point.distance(min, target)) < 0) {
            min = n.getPoint();
        }

        Node good, bad;
        if (n.x_based) {
            if (Double.compare(n.getX(), target.getX()) >= 0) {
                good = n.left;
                bad = n.right;
            } else {
                good = n.right;
                bad = n.left;
            }
        } else {
            if (Double.compare(n.getY(), target.getY()) >= 0) {
                good = n.left;
                bad = n.right;
            } else {
                good = n.right;
                bad = n.left;
            }
        }

        min = nearest(good, target, min);
        double distance = Point.distance(target, min);
        if (!n.x_based && Double.compare(distance, Point.distance(new Point(n.getX(), target.getY()), target)) >= 0) {
            min = nearest(bad, target, min);
        } else if (n.x_based && Double.compare(distance, Point.distance(new Point(target.getX(), n.getY()), target)) >= 0) {
            min = nearest(bad, target, min);
        }
        return min;
    }
}
