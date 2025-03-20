import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

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
public class KdTree {
    private static final double LINE_SIZE = 0.002;
    private static final double POINT_SIZE = 0.008;
    private static final double SPECIAL_POINT_SIZE = 0.012;

    private Node root;
    private int size = 0;

    private static class Node {
        private final Point2D point;
        private final RectHV rect;
        private Node left;
        private Node right;

        public Node(Point2D point, RectHV rect) {
            this.point = point;
            this.rect = rect;
        }
    }

    public KdTree() {
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Can't insert a null point");
        }
        if (contains(p)) {
            return;
        }
        root = insert(root, p, root != null ? root.rect : new RectHV(0, 0, 1, 1), true);
        size++;
    }

    private Node insert(Node node, Point2D p, RectHV rect, boolean isVertical) {
        if (node == null) {
            return new Node(p, rect);
        }

        if (isVertical) {
            int cmp = Double.compare(p.x(), node.point.x());
            if (cmp < 0) {
                node.left = insert(
                        node.left, p,
                        node.left != null ? node.left.rect : new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax()),
                        false);
            } else {
                node.right = insert(
                        node.right, p,
                        node.right != null ? node.right.rect : new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax()),
                        false);
            }
        } else {
            int cmp = Double.compare(p.y(), node.point.y());
            if (cmp < 0) {
                node.left = insert(
                        node.left, p,
                        node.left != null ? node.left.rect : new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y()),
                        true);
            } else {
                node.right = insert(
                        node.right, p,
                        node.right != null ? node.right.rect : new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax()),
                        true);
            }
        }
        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Can't check a null point");
        }
        return contains(root, p, true);
    }

    private boolean contains(Node node, Point2D p, boolean isVertical) {
        if (node == null) {
            return false;
        }
        if (node.point.equals(p)) {
            return true;
        }
        int cmp = isVertical
                ? Double.compare(p.x(), node.point.x())
                : Double.compare(p.y(), node.point.y());
        return cmp < 0
                ? contains(node.left, p, !isVertical)
                : contains(node.right, p, !isVertical);
    }

    public void draw() {
        draw(root, true);
    }

    private void draw(Node node, boolean isVertical) {
        if (node == null) {
            return;
        }
        if (isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(LINE_SIZE);
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(LINE_SIZE);
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }
        StdDraw.setPenRadius(POINT_SIZE);
        StdDraw.setPenColor(StdDraw.BLACK);
        node.point.draw();

        draw(node.left, !isVertical);
        draw(node.right, !isVertical);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Rectangle is null");
        }
        if (isEmpty()) {
            return null;
        }
        return traverse(root, rect, new ArrayList<>());
    }

    private Iterable<Point2D> traverse(Node node, RectHV rect, List<Point2D> list) {
        if (node == null) {
            return list;
        }
        if (node.rect.intersects(rect)) {
            if (rect.contains(node.point)) {
                list.add(node.point);
            }
            traverse(node.left, rect, list);
            traverse(node.right, rect, list);
        }
        return list;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Can't get the nearest point to null");
        }
        if (isEmpty()) {
            return null;
        }
        return nearest(root, p, root.point, true);
    }

    private Point2D nearest(Node node, Point2D p, Point2D champion, boolean isVertical) {
        if (node == null) {
            return champion;
        }

        if (node.point.distanceSquaredTo(p) <= champion.distanceSquaredTo(p)) {
            champion = node.point;
        }
        if (!(node.rect.distanceSquaredTo(p) < champion.distanceSquaredTo(p))) {
            return champion;
        }

        int cmp = isVertical
                ? Double.compare(p.x(), node.point.x())
                : Double.compare(p.y(), node.point.y());
        if (cmp < 0) {
            champion = nearest(node.left, p, champion, !isVertical);
            champion = nearest(node.right, p, champion, !isVertical);
        } else {
            champion = nearest(node.right, p, champion, !isVertical);
            champion = nearest(node.left, p, champion, !isVertical);
        }
        return champion;
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree points = new KdTree();
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
