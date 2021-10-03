import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class PointSET {
    private SET<Point2D> set;
    // construct an empty set of points
    public PointSET() {
        set = new SET<>();
    }
    // is the set empty?
    public  boolean isEmpty(){
        return set.isEmpty();
    }
    // number of points in the set
    public int size(){
        return set.size();
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("point is null");
        }
        set.add(p);
    }
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("point is null");
        }
        return set.contains(p);
    }
    // draw all points to standard draw
    public void draw() {
        set.forEach(point2D -> StdDraw.point(point2D.x(), point2D.y()));
    }
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("rect is null");
        }
        Collection<Point2D> result = new LinkedList<>();
        Iterator<Point2D> iter = set.iterator();
        while (iter.hasNext()) {
            Point2D point = iter.next();
            if (point.x() >= rect.xmin() && point.x() <= rect.xmax()
                    && point.y() >= rect.ymin() && point.y() <= rect.ymax()) {
                result.add(point);
            }
        }
        return result;
    }
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D target) {
        if (target == null) {
            throw new UnsupportedOperationException("point is null");
        }
        Iterator<Point2D> iter = set.iterator();
        Point2D result = null;
        double minDistance = 0;
        while (iter.hasNext()) {
            Point2D point = iter.next();
            double distance = distance(point, target);
            if (result == null || distance < minDistance) {
                minDistance = distance;
                result = point;
            }

        }
        return result;
    }

    private double distance(Point2D p1, Point2D p2) {
        return Math.sqrt(Math.pow(p2.x() - p1.x(), 2) + Math.pow(p2.y() - p1.y(), 2));
    }
}
