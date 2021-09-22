import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {

    private LineSegment[] segments;

    private void checkEquals(Point l, Point r) {
        if (l.compareTo(r) == 0) {
            throw new IllegalArgumentException("input file contains equal points");
        }
    }

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points){
        if (points == null) {
            throw new IllegalArgumentException("point is null");
        }
        LinkedList<LineSegment> segmentsList = new LinkedList<>();
        for (int i = 0; i < points.length; i++) {
            Point origin = points[i];
            //List<Point> aux = new LinkedList<>(Arrays.asList(points));
            Arrays.sort(points, i + 1, points.length, origin.slopeOrder());
            //aux.remove(origin);
            //aux.sort(origin.slopeOrder());
            int reapeats = 0;
            Point lastPoint = null;
            double lastSlope = Double.NEGATIVE_INFINITY;
            for (int j = i + 1; j<points.length; j++) {
                Point point = points[j];
                if (origin.compareTo(point) == 0) {
                    throw new IllegalArgumentException("input file contains dups");
                }
                double slope = origin.slopeTo(point);
                if (lastPoint != null && slope == lastSlope) {
                    reapeats++;
                }
                else {
                    if (reapeats >= 2) {
                        Point[] collinear = Arrays.copyOfRange(points, j - reapeats - 1, j); //aux.subList(j - reapeats - 1, j);
                        LinkedList<Point> list = new LinkedList<>(Arrays.asList(collinear));
                        list.add(origin);
                        list.sort(Point::compareTo);
                        segmentsList.add(new LineSegment(list.getFirst(), list.getLast()));
                    }
                    reapeats = 0;
                }
                lastSlope = slope;
                lastPoint = point;
            }
            if (reapeats >= 2) {
                Point[] collinear = Arrays.copyOfRange(points, points.length - reapeats - 1, points.length); //aux.subList(j - reapeats - 1, j);
                LinkedList<Point> list = new LinkedList<>(Arrays.asList(collinear));
                list.add(origin);
                list.sort(Point::compareTo);
                segmentsList.add(new LineSegment(list.getFirst(), list.getLast()));
            }
        }

        segments = segmentsList.toArray(new LineSegment[0]);
    }
    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }
    // the line segments
    public LineSegment[] segments() {
        return segments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
