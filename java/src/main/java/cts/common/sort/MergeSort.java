package cts.common.sort;

/**
 * Recursive and non-recursive (iterative) MergeSort implementation.
 * Stable, extra memory
 * Time Complexity: O(n * log(n))
 * Space Complexity: O(n)
 * Compares: n * log(n)
 * Array access: 6n * log(n)
 */
public class MergeSort {

    /**
     * Public method to initiate the recursive merge sort process.
     *
     * @param arr The array to be sorted.
     * @param <T> The type of elements in the array, which must implement Comparable.
     */
    public static <T extends Comparable<T>> void sort(T[] arr) {
        // Create an auxiliary array to be used during the merge process.
        T[] aux = (T[]) new Comparable[arr.length];

        // Start the recursive sort process.
        sort(arr, aux, 0, arr.length - 1);
    }

    /**
     * Public method to initiate the iterative merge sort process using a bottom-up approach.
     * But about 10% slower than recursive, top-down mergesort on typical systems.
     *
     * @param arr The array to be sorted.
     * @param <T> The type of elements in the array, which must implement Comparable.
     */
    public static <T extends Comparable<T>> void sortIterative(T[] arr) {
        int n = arr.length;
        // Create an auxiliary array of the same size as the input array.
        T[] aux = (T[]) new Comparable[n];

        // Start with slice of size 1 and double the size in each iteration.
        for (int size = 1; size < n; size *= 2) {
            // Merge slices of the current size.
            for (int lo = 0; lo < n - size; lo += 2 * size) {
                int mid = lo + size - 1;
                int hi = Math.min(lo + 2 * size - 1, n - 1);
                merge(arr, aux, lo, mid, hi);
            }
        }
    }

    /**
     * Recursive method to sort the array.
     *
     * @param arr The array to be sorted.
     * @param aux The auxiliary array used for merging.
     * @param lo  The starting index of the slice to be sorted.
     * @param hi  The ending index of the slice to be sorted.
     * @param <T> The type of elements in the array, which must implement Comparable.
     */
    private static <T extends Comparable<T>> void sort(T[] arr, T[] aux, int lo, int hi) {
        // Base case: If the slice has one or zero elements, it is already sorted.
        if (hi <= lo) {
            return;
        }
        // Find the middle point to divide the array into two halves.
        int mid = lo + (hi - lo) / 2;
        // Recursively sort the left half.
        sort(arr, aux, lo, mid);
        // Recursively sort the right half.
        sort(arr, aux, mid + 1, hi);
        // Merge the two sorted halves.
        merge(arr, aux, lo, mid, hi);
    }

    /**
     * Merges two sorted slices into a single sorted slice.
     *
     * @param arr The array containing the slices to be merged.
     * @param aux The auxiliary array used for merging.
     * @param lo  The starting index of the first slice.
     * @param mid The ending index of the first slice and the starting index of the second slice.
     * @param hi  The ending index of the second slice.
     * @param <T> The type of elements in the array, which must implement Comparable.
     */
    private static <T extends Comparable<T>> void merge(T[] arr, T[] aux, int lo, int mid, int hi) {
        // Assert that the two slices are already sorted.
        assert Sort.isSorted(arr, lo, mid);
        assert Sort.isSorted(arr, mid + 1, hi);

        // Copy the slice from arr to aux.
        if (hi - lo >= 0) {
            System.arraycopy(arr, lo, aux, lo, hi - lo + 1);
        }

        // Initialize pointers for the left and right slices.
        int i = lo;
        int j = mid + 1;

        // Merge the two slices back into the original array.
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                // If the left slice is exhausted, take elements from the right slice.
                arr[k] = aux[j++];
            } else if (j > hi) {
                // If the right slice is exhausted, take elements from the left slice.
                arr[k] = aux[i++];
            } else if (Sort.less(aux[j], aux[i])) {
                // If the current element in the right slice is smaller, take it.
                arr[k] = aux[j++];
            } else {
                // Otherwise, take the current element from the left slice.
                arr[k] = aux[i++];
            }
        }

        // Assert that the merged slice is sorted.
        assert Sort.isSorted(arr, lo, hi);
    }
}