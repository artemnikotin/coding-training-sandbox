package cts.common.sort;

/**
 * SelectionSort is a simple sorting algorithm that works by repeatedly finding the minimum element
 * from the unsorted portion of the array and swapping it with the first element of the unsorted portion.
 * This process is repeated until the entire array is sorted.
 * Characteristics:
 *  - not stable
 *  - in place
 * Time Complexity:
 * - Comparisons: ~ n^2 / 2 (always, as it compares every element with every other element in the worst case)
 * - Exchanges: n (each element is swapped once to its correct position)
 */
public class SelectionSort {

    /**
     * Sorts the given array using the Selection Sort algorithm.
     *
     * @param arr The array to be sorted.
     * @param <T> The type of elements in the array, which must implement Comparable.
     */
    public static <T extends Comparable<T>> void sort(T[] arr) {
        int n = arr.length;

        // Outer loop: Iterate over the array from the first element to the second-to-last element.
        // After each iteration, the smallest element in the unsorted portion is moved to its correct position.
        for (int i = 0; i < n - 1; i++) {

            // Assume the current index (i) is the index of the minimum element in the unsorted portion.
            int minIndex = i;

            // Inner loop: Find the index of the minimum element in the unsorted portion of the array.
            for (int j = i + 1; j < n; j++) {

                // If the current element is smaller than the assumed minimum, update the minimum index.
                if (Sort.less(arr[j], arr[minIndex])) {
                    minIndex = j;
                }
            }

            // Swap the minimum element with the first element of the unsorted portion.
            Sort.swap(arr, i, minIndex);
        }
    }
}