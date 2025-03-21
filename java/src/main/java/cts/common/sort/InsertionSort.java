package cts.common.sort;

import java.util.Comparator;

/**
 * Compares: ~ n^2 / 4 (n - 1; n^2 / 2)
 * Exchanges: ~ n^2 / 4 (0; n^2 / 2)
 */
public class InsertionSort {
    public static <T extends Comparable<T>> void sort(T[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    public static <T extends Comparable<T>> void sort(T[] arr, int lo, int hi) {
        Comparator<T> comparator = new Sort.NaturalOrderComparator<>();
        sort(arr, lo, hi, comparator);
    }

    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        sort(arr, 0, arr.length - 1, comparator);
    }

    public static <T> void sort(T[] arr, int lo, int hi, Comparator<T> comparator) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo; j--) {
                if (Sort.less(comparator, arr[j], arr[j - 1])) {
                    Sort.swap(arr, j, j - 1);
                } else {
                    break;
                }
            }
        }
    }
}
