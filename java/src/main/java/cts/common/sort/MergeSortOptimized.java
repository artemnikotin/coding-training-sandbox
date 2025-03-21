package cts.common.sort;

/**
 * Optimized MergeSort that:
 * - eliminates the copy to the auxiliary array by switching the roles of the input and auxiliary arrays in each recursive call.
 * - eliminates merging if halves are already sorted
 * - use insertion sort for small slices, as mergesort has too much overhead for tiny arrays.
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
        // Create an auxiliary array and copy the input array into it.
        T[] aux = (T[]) new Comparable[arr.length];
        System.arraycopy(arr, 0, aux, 0, arr.length);
        // Start the recursive sort process, using arr as the source and aux as the auxiliary array.
        sort(arr, aux, 0, arr.length - 1);
        System.arraycopy(aux, 0, arr, 0, arr.length);
    }

    /**
     * Recursive method to sort the array.
     *
     * @param src  The source array (input or auxiliary, depending on the recursion level).
     * @param dest The destination array (auxiliary or input, depending on the recursion level).
     * @param lo   The starting index of the slice to be sorted.
     * @param hi   The ending index of the slice to be sorted.
     * @param <T>  The type of elements in the array, which must implement Comparable.
     */
    private static <T extends Comparable<T>> void sort(T[] src, T[] dest, int lo, int hi) {
        // Apply insertion sort for tiny slices.
        if (hi < lo + CUTOFF) {
            InsertionSort.sort(dest);
            return;
        }
        // Find the middle point to divide the array into two halves.
        int mid = lo + (hi - lo) / 2;
        // Recursively sort the left half, switching the roles of src and dest.
        sort(dest, src, lo, mid);
        // Recursively sort the right half, switching the roles of src and dest.
        sort(dest, src, mid + 1, hi);
        // Eliminates merge if source is already sorted. Helps for partially-ordered arrays.
        if (!Sort.less(src[mid + 1], src[mid])) {
            System.arraycopy(src, lo, dest, lo, hi - lo + 1);
            return;
        }
        // Merge the two sorted halves, switching the roles of src and dest.
        merge(src, dest, lo, mid, hi);
    }

    /**
     * Merges two sorted slices into a single sorted slice.
     *
     * @param src  The source array containing the slices to be merged.
     * @param dest The destination array where the merged result will be stored.
     * @param lo   The starting index of the first slice.
     * @param mid  The ending index of the first slice and the starting index of the second slice.
     * @param hi   The ending index of the second slice.
     * @param <T>  The type of elements in the array, which must implement Comparable.
     */
    private static <T extends Comparable<T>> void merge(T[] src, T[] dest, int lo, int mid, int hi) {
        // Initialize pointers for the left and right slices.
        int i = lo;
        int j = mid + 1;

        // Merge the two slices into the destination array.
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                // If the left slice is exhausted, take elements from the right slice.
                System.arraycopy(src, j, dest, k, j - k + 1);
                return;
            }
            if (j > hi) {
                // If the right slice is exhausted, take elements from the left slice.
                System.arraycopy(src, i, dest, k, i - k + 1);
                return;
            }

            if (src[j].compareTo(src[i]) < 0) {
                // If the current element in the right slice is smaller, take it.
                dest[k] = src[j++];
            } else {
                // Otherwise, take the current element from the left slice.
                dest[k] = src[i++];
            }
        }
    }
}