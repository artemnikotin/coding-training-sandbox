package cts.coursera;
/**
 * Coursera | Algorithms, Part I | Week 02
 * Intersection of two sets.
 * Given two arrays a[] and b[], each containing nn distinct 2D points in the plane, design a subquadratic algorithm to
 * count the number of points that are contained both in array a[] and array b[].
 */
public class Intersection2D {
    public static int count(Point2D[] a, Point2D[] b) {
        int res = 0;
        shellSort(a);
        shellSort(b);

        int i = 0;
        int j = 0;
        while (i < a.length && j < b.length) {
            int relationship = a[i].compareTo(b[j]);
            if (relationship == 0) {
                i++;
                j++;
                res++;
            } else if (relationship < 0) {
                i++;
            } else {
                j++;
            }
        }
        return res;
    }

    private static void shellSort(Point2D[] a) {
        int n = a.length;
        int h = 1;
        while (h < n / 3) {
            h = 3 * h + 1;
        }
        while (h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                }
            }

            h = h / 3;
        }
    }

    private static boolean less(Point2D v, Point2D w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Point2D[] a, int i, int j) {
        Point2D swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static class Point2D implements Comparable<Point2D> {
        public final int x, y;

        public Point2D(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int compareTo(Point2D that) {
            if (this.x < that.x) {
                return -1;
            }
            if (this.x > that.x) {
                return 1;
            }
            return Integer.compare(this.y, that.y);
        }
    }

    public static void main(String[] args) {
        Point2D[] a = {new Point2D(1, 1), new Point2D(1, 2), new Point2D(2, 1)};
        Point2D[] b = {new Point2D(2, 2), new Point2D(2, 1), new Point2D(1, 2), new Point2D(3, 3)};

        System.out.print(count(a, b)); // Output: 2
    }
}
