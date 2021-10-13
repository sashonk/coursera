import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Collection;
import java.util.LinkedList;

public class KdTree {

    private static class Node {
        Point2D point;
        Node left;
        Node right;
        Node parent;
    }

    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {

    }
    // is the set empty?
    public  boolean isEmpty(){
        return root == null;
    }
    // number of points in the set
    public int size(){
        return size;
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("point is null");
        }

        if (contains(p)) {
            return;
        }

        root = put(root, p);
    }

    private boolean odd(Node node) {
        if (node == root) {
            return true;
        }

        return !odd(node.parent);
    }

    private Node put(Node node, Point2D p) {
        if (node == null) {
            Node nd = new Node();
            nd.point = p;
            size++;
            return nd;
        }

        boolean isLeft = odd(node) ? p.x() < node.point.x() : p.y() < node.point.y();
        if (isLeft) {
            node.left = put(node.left, p);
            node.left.parent = node;
        }
        else {
            node.right = put(node.right, p);
            node.right.parent = node;
        }
        return node;
    }
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("point is null");
        }

        return find(root, p) != null;
    }

    private Node find(Node node, Point2D p) {
        if (node == null) {
            return null;
        }

        if (node.point.equals(p)) {
            return node;
        }

        boolean isLeft = odd(node) ? p.x() < node.point.x() : p.y() < node.point.y();
        return find(isLeft ? node.left : node.right, p);
    }

    private interface Callback {
        void apply(Point2D point);
    }

    private void forEach (Node node, Callback consumer) {
        if (node == null) {
            return;
        }

        consumer.apply(node.point);
        forEach(node.left, consumer);
        forEach(node.right, consumer);
    }

    // draw all points to standard draw
    public void draw() {
        forEach(root, (point) -> {
            if (point != null) {
                StdDraw.point(point.x(), point.y());
            }
        });
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("rect is null");
        }

        Collection<Point2D> result = new LinkedList<>();
        getPointsInRect(root, rect, result);
        return result;
    }

    private enum Relation {
        IN,
        OUT
    }

    private void getPointsInRect(Node node, RectHV rect, Collection<Point2D> result) {
        if (node == null) {
            return;
        }

        Relation left = node.point.x() >= rect.xmin() ? Relation.IN : Relation.OUT;
        Relation right = node.point.x() <= rect.xmax() ? Relation.IN : Relation.OUT;
        Relation bottom = node.point.y() >= rect.ymin() ? Relation.IN : Relation.OUT;
        Relation top = node.point.y() <= rect.ymax() ? Relation.IN : Relation.OUT;
        if (left == Relation.IN && right == Relation.IN && bottom == Relation.IN && top == Relation.IN) {
            result.add(node.point);
        }
        if (odd(node)) {
            if (left == Relation.IN) {
                getPointsInRect(node.left, rect, result);
            }
            if (right == Relation.IN) {
                getPointsInRect(node.right, rect, result);
            }
        }
        else {
            if (bottom == Relation.IN) {
                getPointsInRect(node.left, rect, result);
            }
            if (top == Relation.IN) {
                getPointsInRect(node.right, rect, result);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D target) {
        if (target == null) {
            throw new IllegalArgumentException("point is null");
        }

        if (isEmpty()) {
            return null;
        }

        ValueHolder minDistance = new ValueHolder();
        minDistance.set(distance(root.point, target));
        Node node = findClosestPoint(root, target, root, minDistance);
        return node != null ? node.point : null;
    }

    private static class ValueHolder {
        private Double value;
        Double get() {
            return value;
        }

        void set(Double newValue) {
            this.value = newValue;
        }
    }

    private Node findClosestPoint(Node node, Point2D target, Node closest, ValueHolder minDistance) {
        if (node == null) {
            return closest;
        }

        double distance = distance(node.point, target);
        if (distance < minDistance.get()) {
            closest = node;
            minDistance.set(distance);
        }
        boolean isLeftFirst = odd(node) ? target.x() < node.point.x() : target.y() < node.point.y();
        double distFromRect = odd(node) ? Math.abs(target.x() - node.point.x()) : Math.abs(target.y() - node.point.y());
        if (isLeftFirst) {
            closest = findClosestPoint(node.left, target, closest, minDistance);
            if (minDistance.get() > distFromRect) {
                closest = findClosestPoint(node.right, target, closest, minDistance);
            }
        }
        else {
            closest = findClosestPoint(node.right, target, closest, minDistance);
            if (minDistance.get() > distFromRect) {
                closest = findClosestPoint(node.left, target, closest, minDistance);
            }
        }
        return closest;
    }

    private double distance(Point2D p1, Point2D p2) {
        return Math.sqrt(Math.pow(p2.x() - p1.x(), 2) + Math.pow(p2.y() - p1.y(), 2));
    }
}
