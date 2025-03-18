import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Coursera | Algorithms, Part I | Week 03 Assignment
 * ASSESSMENT SUMMARY
 * Compilation:  PASSED
 * API:          PASSED
 * SpotBugs:     PASSED
 * PMD:          PASSED
 * Checkstyle:   PASSED
 * Correctness:  41/41 tests passed
 * Memory:       1/1 tests passed
 * Timing:       41/41 tests passed
 * Aggregate score: 100.00%
 */
public class FastCollinearPoints {
    private static final int MIN_COLLINEAR_LENGTH = 4;

    private final List<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] input) {
        if (input == null) {
            throw new IllegalArgumentException("The argument to the constructor is null");
        }

        Point[] points = new Point[input.length];
        for (int i = 0; i < input.length; i++) {
            if (input[i] == null) {
                throw new IllegalArgumentException("The point in the array is null");
            }
            points[i] = input[i];
        }

        Arrays.sort(points);
        for (int i = 1; i < points.length; i++) {
            if (points[i - 1].compareTo(points[i]) == 0) {
                throw new IllegalArgumentException("The argument to the constructor contains a repeated point");
            }
        }

        for (Point point : input) {
            Arrays.sort(points, point.slopeOrder());

            int i = 0;
            double iSlope = Double.NEGATIVE_INFINITY;

            for (int j = 0; j < points.length; j++) {
                double jSlope = point.slopeTo(points[j]);
                if (i == 0 || iSlope != jSlope) {
                    if (j - i >= MIN_COLLINEAR_LENGTH - 1 && points[i].compareTo(point) > 0) {
                        segments.add(new LineSegment(point, points[j - 1]));
                    }
                    i = j;
                    iSlope = jSlope;
                }
            }
            if (points.length - i >= MIN_COLLINEAR_LENGTH - 1 && points[i].compareTo(point) > 0) {
                segments.add(new LineSegment(point, points[points.length - 1]));
            }

            Arrays.sort(points);
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-500, 32768);
        StdDraw.setYscale(-500, 32768);

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // print and draw the line segments
        StdDraw.setPenRadius(0.001);
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

        // draw the points
        StdDraw.setPenRadius(0.01);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        StdOut.println("Number of segments: " + collinear.numberOfSegments());
    }
}
