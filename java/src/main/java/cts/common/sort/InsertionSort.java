package cts.common.sort;

import java.util.Comparator;

/**
 * InsertionSort is a simple sorting algorithm that builds the final sorted array one element at a time.
 * It is efficient for small datasets and partially sorted arrays.
 * Characteristics:
 *  - stable
 *  - in place
 * Time Complexity:
 * - Comparisons: ~ n^2 / 4 (on average; n-1 in the best case, n^2 / 2 in the worst case)
 * - Exchanges: ~ n^2 / 4 (on average; 0 in the best case, n^2 / 2 in the worst case)
 */
public class InsertionSort {

    /**
     * Sorts the entire array using the natural order of elements.
     *
     * @param arr The array to be sorted.
     * @param <T> The type of elements in the array, which must implement Comparable.
     */
    public static <T extends Comparable<T>> void sort(T[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    /**
     * Sorts a specific range of the array using the natural order of elements.
     *
     * @param arr The array to be sorted.
     * @param lo  The starting index of the range to be sorted.
     * @param hi  The ending index of the range to be sorted.
     * @param <T> The type of elements in the array, which must implement Comparable.
     */
    public static <T extends Comparable<T>> void sort(T[] arr, int lo, int hi) {
        Comparator<T> comparator = new Sort.NaturalOrderComparator<>();
        sort(arr, lo, hi, comparator);
    }

    /**
     * Sorts the entire array using a custom comparator.
     *
     * @param arr        The array to be sorted.
     * @param comparator The comparator to determine the order of elements.
     * @param <T>        The type of elements in the array.
     */
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        sort(arr, 0, arr.length - 1, comparator);
    }

    /**
     * Sorts a specific range of the array using a custom comparator.
     *
     * @param arr        The array to be sorted.
     * @param lo         The starting index of the range to be sorted.
     * @param hi         The ending index of the range to be sorted.
     * @param comparator The comparator to determine the order of elements.
     * @param <T>        The type of elements in the array.
     */
    public static <T> void sort(T[] arr, int lo, int hi, Comparator<T> comparator) {
        // Iterate over the array from the starting index (lo) to the ending index (hi).
        for (int i = lo; i <= hi; i++) {
            // Start from the current element and move backwards towards the start of the range.
            for (int j = i; j > lo; j--) {
                // If the current element is smaller than the previous element, swap them.
                if (Sort.less(comparator, arr[j], arr[j - 1])) {
                    Sort.swap(arr, j, j - 1);
                } else {
                    // If the current element is not smaller, stop the inner loop.
                    // This is because the left part of the array is already sorted.
                    break;
                }
            }
        }
    }
}