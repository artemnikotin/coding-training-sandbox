package cts.coursera;
/**
 * Coursera | Algorithms, Part I | Week 03
 * Counting inversions.
 * An inversion in an array a[] is a pair of entries a[i] and a[j] such that i < j but a[i] > a[j]. Given an array,
 * design a linearithmic algorithm to count the number of inversions.
 */
public class Inversions {
    public static int countInversions(int[] arr) {
        if (arr.length <= 1) {
            return 0;
        }
        var copy = new int[arr.length];
        var aux = new int[arr.length];
        System.arraycopy(arr, 0, copy, 0, arr.length);
        return countInversions(copy, aux, 0, copy.length - 1);
    }

    private static int countInversions(int[] arr, int[] aux, int lo, int hi) {
        if (hi <= lo) {
            return 0;
        }

        int mid = lo + (hi - lo) / 2;
        int inversions = 0;

        inversions += countInversions(arr, aux, lo, mid);
        inversions += countInversions(arr, aux, mid + 1, hi);
        inversions += merge(arr, aux, lo, mid, hi);

        return inversions;
    }

    private static int merge(int[] arr, int[] aux, int lo, int mid, int hi) {
        if (hi + 1 - lo >= 0) {
            System.arraycopy(arr, lo, aux, lo, hi + 1 - lo);
        }

        int inversions = 0;
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                arr[k] = aux[j++];
            } else if (j > hi) {
                arr[k] = aux[i++];
            } else if (aux[j] < aux[i]) {
                arr[k] = aux[j++];
                inversions += mid - i + 1;
            } else {
                arr[k] = aux[i++];
            }
        }
        return inversions;
    }

    public static void main(String[] args) {
        int[] arr = {9, 1, 6, 4, 5, 7, 10};
        System.out.println(countInversions(arr)); // Output: 7
    }
}
