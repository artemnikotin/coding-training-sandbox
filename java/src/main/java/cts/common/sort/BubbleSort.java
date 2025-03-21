package cts.common.sort;

/**
 * BubbleSort is a simple sorting algorithm that repeatedly steps through the array,
 * compares adjacent elements, and swaps them if they are in the wrong order.
 * This process is repeated until the array is sorted.
 * Characteristics:
 *  - stable
 *  - in place
 * Time Complexity:
 * - Comparisons: ~ n^2 / 2 (where n is the number of elements in the array)
 * - Exchanges: ~ n^2 / 2 (in the worst case)
 */
public class BubbleSort {

    /**
     * Sorts the given array using the Bubble Sort algorithm.
     *
     * @param arr The array to be sorted.
     * @param <T> The type of elements in the array, which must implement Comparable.
     */
    public static <T extends Comparable<T>> void sort(T[] arr) {
        int n = arr.length;

        // Outer loop: Iterate over the array n-1 times.
        // After each iteration, the largest unsorted element "bubbles up" to its correct position.
        for (int i = 0; i < n - 1; i++) {

            // Inner loop: Compare adjacent elements and swap them if they are in the wrong order.
            // The range of comparison decreases with each outer loop iteration because the largest
            // elements are already in their correct positions.
            for (int j = 0; j < n - i - 1; j++) {

                // If the current element is greater than the next element, swap them.
                if (Sort.less(arr[j + 1], arr[j])) {
                    Sort.swap(arr, j, j + 1);
                }
            }
        }
    }
}