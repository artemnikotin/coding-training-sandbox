package cts.common.sort;

import java.util.Comparator;

/**
 * Optimized QuickSort that:
 * - use improved algorithm for selecting the middle element.
 * - use Dijkstra 3-way partitioning for better performance with arrays with duplicates.
 * - uses insertion sort for small slices, as quicksort has too much overhead for tiny arrays.
 * Time Complexity: O(n * log(n))
 * Space Complexity: O(log n) (due to recursion stack)
 */
public class QuickSortOptimized {

    // Cutoff to switch to insertion sort for small arrays
    private static final int CUTOFF = 10;

    /**
     * Sorts the given array using an optimized QuickSort algorithm.
     *
     * @param arr The array to be sorted.
     * @param <T> The type of elements in the array, which must implement Comparable.
     */
    public static <T extends Comparable<T>> void sort(T[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    /**
     * Sorts the given array within the specified range using an optimized QuickSort algorithm.
     *
     * @param arr  The array to be sorted.
     * @param low  The starting index of the range to sort.
     * @param high The ending index of the range to sort.
     * @param <T>  The type of elements in the array, which must implement Comparable.
     */
    public static <T extends Comparable<T>> void sort(T[] arr, int low, int high) {
        Comparator<T> comparator = new Sort.NaturalOrderComparator<>();
        sort(arr, low, high, comparator);
    }

    /**
     * Sorts the given array using an optimized QuickSort algorithm with a custom comparator.
     *
     * @param arr        The array to be sorted.
     * @param comparator The comparator to determine the order of elements.
     * @param <T>        The type of elements in the array.
     */
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        sort(arr, 0, arr.length - 1, comparator);
    }

    /**
     * Sorts the given array within the specified range using an optimized QuickSort algorithm
     * with a custom comparator.
     *
     * @param arr        The array to be sorted.
     * @param low        The starting index of the range to sort.
     * @param high       The ending index of the range to sort.
     * @param comparator The comparator to determine the order of elements.
     * @param <T>        The type of elements in the array.
     */
    public static <T> void sort(T[] arr, int low, int high, Comparator<T> comparator) {
        int length = high - low + 1;

        // Base case: If the slice has 1 or 0 elements, it's already sorted
        if (length <= 1) {
            return;
        }

        // Use insertion sort for small slices (optimization for small sizes)
        if (length < CUTOFF) {
            InsertionSort.sort(arr, low, high, comparator);
            return;
        }

        // Choose the pivot based on the size of the slice
        int pivot;
        if (length > 1000) {
            // For large arrays, use Tukey's Ninther to select a good pivot
            pivot = tukeysNinther(arr, low, high, comparator);
        } else if (length > 50) {
            // For medium arrays, use the median-of-three method
            pivot = medianOfThree(arr, low, low + (high - low) / 2, high, comparator);
        } else {
            // For small arrays, use the middle element as the pivot
            pivot = low + (high - low) / 2;
        }

        // Partition the array around the pivot and get the partition boundaries
        int[] partitionIndices = partition(arr, low, high, arr[pivot], comparator);

        // Recursively sort the left and right slices
        sort(arr, low, partitionIndices[0] - 1, comparator);
        sort(arr, partitionIndices[1] + 1, high, comparator);
    }

    /**
     * Selects a pivot using Tukey's Ninther method for large arrays.
     *
     * @param arr        The array to select the pivot from.
     * @param low        The starting index of the range.
     * @param high       The ending index of the range.
     * @param comparator The comparator to determine the order of elements.
     * @param <T>        The type of elements in the array.
     * @return The index of the selected pivot.
     */
    private static <T> int tukeysNinther(T[] arr, int low, int high, Comparator<T> comparator) {
        int length = high - low + 1;
        int mid = low + (high - low) / 2;

        // Divide the array into 3 groups of 3 elements each and find their medians
        int a = medianOfThree(arr, low, low + length / 3, low + 2 * length / 3, comparator);
        int b = medianOfThree(arr, mid - length / 6, mid, mid + length / 6, comparator);
        int c = medianOfThree(arr, high - 2 * length / 3, high - length / 3, high, comparator);

        // Find the median of the three medians
        return medianOfThree(arr, a, b, c, comparator);
    }

    /**
     * Finds the median of three elements in the array.
     *
     * @param arr        The array containing the elements.
     * @param a          The index of the first element.
     * @param b          The index of the second element.
     * @param c          The index of the third element.
     * @param comparator The comparator to determine the order of elements.
     * @param <T>        The type of elements in the array.
     * @return The index of the median element.
     */
    private static <T> int medianOfThree(T[] arr, int a, int b, int c, Comparator<T> comparator) {
        if (Sort.less(comparator, arr[a], arr[b])) {
            if (Sort.less(comparator, arr[b], arr[c])) {
                return b; // a < b < c
            } else if (Sort.less(comparator, arr[a], arr[c])) {
                return c; // a < c <= b
            } else {
                return a; // c <= a < b
            }
        } else {
            if (Sort.less(comparator, arr[a], arr[c])) {
                return a; // b <= a < c
            } else if (Sort.less(comparator, arr[b], arr[c])) {
                return c; // b < c <= a
            } else {
                return b; // c <= b <= a
            }
        }
    }

    /**
     * Partitions the array into three parts: elements less than the pivot,
     * elements equal to the pivot, and elements greater than the pivot.
     *
     * @param arr        The array to partition.
     * @param low        The starting index of the range.
     * @param high       The ending index of the range.
     * @param pivot      The pivot element.
     * @param comparator The comparator to determine the order of elements.
     * @param <T>        The type of elements in the array.
     * @return An array containing the boundaries of the equal elements.
     */
    private static <T> int[] partition(T[] arr, int low, int high, T pivot, Comparator<T> comparator) {
        int lt = low; // Pointer for elements less than the pivot
        int gt = high; // Pointer for elements greater than the pivot
        int i = low; // Pointer for traversal

        while (i <= gt) {
            if (Sort.less(comparator, arr[i], pivot)) {
                // Move the element to the left partition
                Sort.swap(arr, i, lt);
                lt++;
                i++;
            } else if (Sort.less(comparator, pivot, arr[i])) {
                // Move the element to the right partition
                Sort.swap(arr, i, gt);
                gt--;
            } else {
                // The element is equal to the pivot, so just move the pointer
                i++;
            }
        }

        // Return the boundaries of the equal elements
        return new int[]{lt, gt};
    }
}