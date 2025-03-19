import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

/**
 * Coursera | Algorithms, Part I | Week 05 Assignment
 * ASSESSMENT SUMMARY
 * Compilation:  PASSED
 * API:          PASSED
 * SpotBugs:     PASSED
 * PMD:          PASSED
 * Checkstyle:   FAILED (0 errors, 6 warnings)
 * Correctness:  35/35 tests passed
 * Memory:       16/16 tests passed
 * Timing:       42/42 tests passed
 * Aggregate score: 100.00%
 */
public class PointSET {
    private static final double LINE_SIZE = 0.002;
    private static final double POINT_SIZE = 0.008;
    private static final double SPECIAL_POINT_SIZE = 0.012;

    private final SET<Point2D> points;

    public PointSET() {
        points = new SET<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Can't insert a null point");
        }

        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Can't check a null point");
        }

        return points.contains(p);
    }

    public void draw() {
        StdDraw.setPenRadius(POINT_SIZE);
        for (Point2D point : points) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Rectangle is null");
        }

        ArrayList<Point2D> range = new ArrayList<>();

        for (Point2D point : points) {
            if (rect.contains(point)) {
                range.add(point);
            }
        }

        return range;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Can't get the nearest point to null");
        }
        if (isEmpty()) {
            return null;
        }

        Point2D champion = null;
        for (Point2D point : points) {
            if (champion == null || (p.distanceSquaredTo(point) < p.distanceSquaredTo(champion))) {
                champion = point;
            }
        }

        return champion;
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        PointSET points = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            points.insert(p);
        }

        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        points.draw();

        RectHV queryRect = new RectHV(0.05, 0.05, 0.25, 0.25);

        StdDraw.setPenRadius(LINE_SIZE);
        StdDraw.setPenColor(200, 200, 200);
        queryRect.draw();

        StdDraw.setPenRadius(SPECIAL_POINT_SIZE);
        var range = points.range(queryRect);
        for (Point2D p : range) {
            p.draw();
        }

        Point2D queryPoint = new Point2D(0.4, 0.6);
        StdDraw.setPenColor(0, 255, 0);
        queryPoint.draw();

        Point2D nearestPoint = points.nearest(queryPoint);
        StdDraw.setPenColor(255, 0, 0);
        nearestPoint.draw();

        System.out.println("Point set size: " + points.size());
        System.out.println("Point set contain (0.1, 0.1): " + points.contains(new Point2D(0.1, 0.1)));
    }
}
