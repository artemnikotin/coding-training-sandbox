package cts.coursera;
import java.util.Arrays;
import java.util.Random;

/**
 * Coursera | Algorithms, Part I | Week 03
 * Nuts and bolts.
 * A disorganized carpenter has a mixed pile of n nuts and n bolts. The goal is to find the corresponding pairs of
 * nuts and bolts. Each nut fits exactly one bolt and each bolt fits exactly one nut. By fitting a nut and a bolt together,
 * the carpenter can see which one is bigger (but the carpenter cannot compare two nuts or two bolts directly).
 * Design an algorithm for the problem that uses at most proportional to n log(n) compares (probabilistically).
 */
public class QuickNutsAndBoltsMatch {
    private static final Random rand = new Random();

    public static void match(int[] nuts, int[] bolts) {
        assert nuts.length == bolts.length;

        shuffle(nuts);
        shuffle(bolts);

        match(nuts, bolts, 0, nuts.length - 1);
    }

    private static void match(int[] nuts, int[] bolts, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        // Take any bolt and separate the nuts into groups:
        //  1. suitable nut
        //  2. nuts with smaller size
        //  3. nuts with greater size
        int i = partition(nuts, lo, hi, bolts[lo]);

        // Now do the same with bolts and found nut
        partition(bolts, lo, hi, nuts[i]);

        match(nuts, bolts, lo, i - 1);
        match(nuts, bolts, i + 1, hi);
    }

    private static void shuffle(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            exchange(arr, i, rand.nextInt(i + 1));
        }
    }


    private static int partition(int[] arr, int lo, int hi, int value) {
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (arr[j] < value) {
                exchange(arr, i, j);
                i++;
            } else if (arr[j] == value) {
                exchange(arr, hi, j);
                j--;
            }
        }
        exchange(arr, hi, i);
        return i;
    }

    private static void exchange(int[] arr, int i, int j) {
        int swap = arr[i];
        arr[i] = arr[j];
        arr[j] = swap;
    }

    public static void main(String[] args) {
        int[] bolts = {1, 2, 4, 3, 6, 5};
        int[] nuts = {1, 4, 3, 2, 5, 6};
        match(bolts, nuts);
        System.out.printf("Bolts: %s\n", Arrays.toString(bolts));
        System.out.printf("Nuts : %s\n", Arrays.toString(nuts));
    }
}
