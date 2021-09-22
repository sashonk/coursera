import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class BruteCollinearPoints {

    private LineSegment[] segments;

    private void checkEquals(Point l, Point r) {
        if (l.compareTo(r) == 0) {
            throw new IllegalArgumentException("input file contains equal points");
        }
    }
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points){
        if (points == null) {
            throw new IllegalArgumentException("point is null");
        }
        LinkedList<LineSegment> segmentsList = new LinkedList<>();
        for (int i = 0; i < points.length; i++) {
            Point pi = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point pj = points[j];
                checkEquals(pi, pj);
                double slopeIJ = pi.slopeTo(pj);
                for (int k = j + 1; k < points.length; k++) {
                    Point pk = points[k];
                    checkEquals(pk, pj);
                    double slopeJK = pj.slopeTo(pk);
                    if (slopeIJ == slopeJK) {
                        for (int l = k + 1; l < points.length; l++) {
                            Point pl = points[l];
                            checkEquals(pk, pl);
                            double slopeKL = pk.slopeTo(pl);
                            if (slopeJK == slopeKL) {
                                Point[] p = new Point[]{pi, pj, pk, pl};
                                Arrays.sort(p);
                                LineSegment segment = new LineSegment(p[0], p[3]);
                                segmentsList.add(segment);
                            }
                        }
                    }
                }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
