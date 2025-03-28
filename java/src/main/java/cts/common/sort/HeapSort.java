package cts.common.sort;

import java.util.Comparator;

/**
 * HeapSort is a comparison-based sorting algorithm that uses a binary heap data structure.
 * The algorithm works in two main phases:
 *  - Heap Construction: Builds a max-heap (or min-heap) from the input data
 *  - Sorting Phase: Repeatedly extracts the maximum (or minimum) element from the heap
 *    and reconstructs the heap until all elements are sorted
 * Characteristics:
 *  - not stable
 *  - in place
 * Time Complexity: O(n log n) for all cases (best, average, and worst)
 * Space Complexity: O(1) - it sorts in place, requiring only constant additional space
 */
public class HeapSort {
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
     * @param arr  The array to be sorted.
     * @param low  The starting index of the range to be sorted.
     * @param high The ending index of the range to be sorted.
     * @param <T>  The type of elements in the array, which must implement Comparable.
     */
    public static <T extends Comparable<T>> void sort(T[] arr, int low, int high) {
        Comparator<T> comparator = new Sort.NaturalOrderComparator<>();
        sort(arr, low, high, comparator);
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
     * Sorts a specific range of the array using a custom comparator with HeapSort algorithm.
     *
     * @param arr        The array to be sorted.
     * @param low        The starting index of the range to be sorted.
     * @param high       The ending index of the range to be sorted.
     * @param comparator The comparator to determine the order of elements.
     * @param <T>        The type of elements in the array.
     */
    public static <T> void sort(T[] arr, int low, int high, Comparator<T> comparator) {
        int length = high - low + 1;
        int half = low + length / 2;

        // Build the heap by sinking each non-leaf node starting from the middle
        for (int k = half - 1; k >= low; k--) {
            sink(arr, low, high, k, comparator);
        }

        // Sort the heap by repeatedly moving the max element to the end
        while (high > low) {
            Sort.swap(arr, low, high);  // Move current max to end
            sink(arr, low, --high, low, comparator);  // Restore heap property
        }
    }

    /**
     * Sinks an element in the heap to its proper position to maintain the heap property.
     *
     * @param arr        The array representing the heap.
     * @param low        The lower bound of the heap in the array.
     * @param high       The upper bound of the heap in the array.
     * @param current    The index of the element to sink.
     * @param comparator The comparator to determine the order of elements.
     * @param <T>        The type of elements in the array.
     */
    private static <T> void sink(T[] arr, int low, int high, int current, Comparator<T> comparator) {
        int length = high - low + 1;
        int half = low + length / 2;  // Index of the first leaf node

        // Continue sinking until we reach a leaf node
        while (current < half) {
            int left = 2 * current - low + 1;  // Left child index
            int right = left + 1;              // Right child index

            // Choose the child with higher priority (larger for max-heap)
            if (right <= high && Sort.less(comparator, arr[left], arr[right])) {
                left = right;
            }

            // If current element has higher priority than both children, stop
            if (!Sort.less(comparator, arr[current], arr[left])) {
                break;
            }

            Sort.swap(arr, current, left);  // Swap with higher priority child
            current = left;                 // Move to child position
        }
    }
}