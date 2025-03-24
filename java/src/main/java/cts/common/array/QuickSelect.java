package cts.common.array;

import cts.common.sort.Sort;

import java.util.Comparator;

/**
 * Goal: Given an array of N items, find a k-th smallest item.
 * Implements the QuickSelect algorithm which is based on QuickSort's partitioning approach.
 */
@SuppressWarnings("unchecked")
public class QuickSelect {
    /**
     * Selects the k-th smallest element from the array using natural ordering.
     * @param arr The input array
     * @param k The index of the element to find (0-based)
     * @return The k-th smallest element
     * @param <T> The type of elements in the array, must implement Comparable
     */
    public static <T extends Comparable<T>> T select(T[] arr, int k) {
        Comparator<T> comparator = new Sort.NaturalOrderComparator<>();
        return select(arr, k, comparator);
    }

    /**
     * Selects the k-th smallest element from the array using a custom comparator.
     * @param arr The input array
     * @param k The index of the element to find (0-based)
     * @param comparator The comparator to define the ordering
     * @return The k-th smallest element
     * @param <T> The type of elements in the array
     * @throws IllegalArgumentException if k is out of bounds
     */
    public static <T> T select(T[] arr, int k, Comparator<T> comparator) {
        // Validate input
        if (k < 0 || k >= arr.length) {
            throw new IllegalArgumentException(k + "-th smallest item is out of bound array with " + arr.length + " items");
        }

        // Create a copy of the array to avoid modifying the original
        T[] copy = (T[]) new Object[arr.length];
        System.arraycopy(arr, 0, copy, 0, arr.length);

        // Shuffle the array for probabilistic guarantee of performance
        Sort.shuffle(copy);

        // Initialize search boundaries
        int low = 0;
        int high = copy.length - 1;

        // QuickSelect algorithm implementation
        while (high > low) {
            // Partition the array and get the pivot position
            int j = partition(copy, low, high, comparator);

            // Narrow down the search based on the pivot position
            if (j < k) {
                // Search in the right partition
                low = j + 1;
            } else if (j > k) {
                // Search in the left partition
                high = j - 1;
            } else {
                // Found the k-th smallest element
                return copy[k];
            }
        }
        // When the search range has narrowed to one element
        return copy[k];
    }

    /**
     * Partitions the array around a pivot element.
     * @param arr The array to partition
     * @param low The lower bound of the partition range
     * @param high The upper bound of the partition range
     * @param comparator The comparator to define the ordering
     * @return The final position of the pivot element
     * @param <T> The type of elements in the array
     */
    private static <T> int partition(T[] arr, int low, int high, Comparator<T> comparator) {
        // Choose the first element as pivot (after shuffling this is random)
        T pivot = arr[low];
        int i = low;
        int j = high + 1;

        while (true) {
            // Find item on left to swap (greater than or equal to pivot)
            while (Sort.less(comparator, arr[++i], pivot)) {
                if (i == high) {
                    break;
                }
            }

            // Find item on right to swap (less than or equal to pivot)
            while (Sort.less(comparator, pivot, arr[--j])) {
                if (j == low) {
                    break;
                }
            }

            // Check if pointers cross
            if (i >= j) {
                break;
            }

            // Swap elements to maintain the partition
            Sort.swap(arr, i, j);
        }

        // Swap pivot element into its final position
        Sort.swap(arr, low, j);

        // Return the pivot's final position
        return j;
    }
}