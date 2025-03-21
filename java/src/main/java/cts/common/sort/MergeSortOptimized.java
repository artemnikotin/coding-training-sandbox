package cts.common.sort;

import java.util.Comparator;

/**
 * Optimized MergeSort that:
 * - eliminates the copy to the auxiliary array by switching the roles of the input and auxiliary arrays in each recursive call.
 * - eliminates merging if the two halves are already in order.
 * - uses insertion sort for small slices, as mergesort has too much overhead for tiny arrays.
 * Time Complexity: O(n * log(n))
 * Space Complexity: O(n) (due to the auxiliary array)
 */
public class MergeSortOptimized {

    // Cutoff to insertion sort
    private static final int CUTOFF = 7;

    /**
     * Public method to initiate the merge sort process.
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
        if (hi - lo + 1 <= 1) {
            return;
        }
        // Create an auxiliary array and copy the input slice into it.
        T[] aux = (T[]) new Object[arr.length];
        System.arraycopy(arr, 0, aux, 0, arr.length);
        // Start the recursive sort process, using aux as the source and arr as the destination array.
        sort(aux, arr, lo, hi, comparator);
    }

    /**
     * Recursive method to sort the array.
     *
     * @param src        The source array (input or auxiliary, depending on the recursion level).
     * @param dest       The destination array (auxiliary or input, depending on the recursion level).
     * @param lo         The starting index of the slice to be sorted.
     * @param hi         The ending index of the slice to be sorted.
     * @param comparator The comparator to determine the order of elements.
     * @param <T>        The type of elements in the array.
     */
    private static <T> void sort(T[] src, T[] dest, int lo, int hi, Comparator<T> comparator) {
        // Apply insertion sort for tiny slices.
        if (hi - lo <= CUTOFF) {
            InsertionSort.sort(dest, lo, hi, comparator);
            return;
        }
        // Find the middle point to divide the array into two halves.
        int mid = lo + (hi - lo) / 2;
        // Recursively sort the left half, switching the roles of src and dest.
        sort(dest, src, lo, mid, comparator);
        // Recursively sort the right half, switching the roles of src and dest.
        sort(dest, src, mid + 1, hi, comparator);
        // Skip merge if the two halves are already in order.
        if (!Sort.less(comparator, src[mid + 1], src[mid])) {
            System.arraycopy(src, lo, dest, lo, hi - lo + 1);
            return;
        }
        // Merge the two sorted halves, switching the roles of src and dest.
        merge(src, dest, lo, mid, hi, comparator);
    }

    /**
     * Merges two sorted slices into a single sorted slice.
     *
     * @param src        The source array containing the slices to be merged.
     * @param dest       The destination array where the merged result will be stored.
     * @param lo         The starting index of the first slice.
     * @param mid        The ending index of the first slice and the starting index of the second slice.
     * @param hi         The ending index of the second slice.
     * @param comparator The comparator to determine the order of elements.
     * @param <T>        The type of elements in the array.
     */
    private static <T> void merge(T[] src, T[] dest, int lo, int mid, int hi, Comparator<T> comparator) {
        // Initialize pointers for the left and right slices.
        int i = lo;
        int j = mid + 1;

        // Merge the two slices into the destination array.
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                // If the left slice is exhausted, take elements from the right slice.
                dest[k] = src[j++];
            } else if (j > hi) {
                // If the right slice is exhausted, take elements from the left slice.
                dest[k] = src[i++];
            } else if (Sort.less(comparator, src[j], src[i])) {
                // If the current element in the right slice is smaller, take it.
                dest[k] = src[j++];
            } else {
                // Otherwise, take the current element from the left slice.
                dest[k] = src[i++];
            }
        }
    }
}