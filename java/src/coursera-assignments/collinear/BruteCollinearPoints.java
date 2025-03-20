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
public class BruteCollinearPoints {
    private static final int COLLINEAR_LENGTH = 4;

    private final List<LineSegment> segments = new ArrayList<>();

    public BruteCollinearPoints(Point[] input) {
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

        for (int i = 0; i < points.length - (COLLINEAR_LENGTH - 1); i++) {
            for (int j = i + 1; j < points.length - (COLLINEAR_LENGTH - 2); j++) {
                double slope = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length - (COLLINEAR_LENGTH - 3); k++) {
                    if (slope == points[i].slopeTo(points[k])) {
                        for (int p = k + 1; p < points.length; p++) {
                            if (slope == points[i].slopeTo(points[p])) {
                                segments.add(new LineSegment(points[i], points[p]));
                            }
                        }
                    }
                }
            }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
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
