package cts.common.sort;

/**
 * ShellSort is an efficient sorting algorithm that generalizes insertion sort by allowing the exchange of elements
 * that are far apart. It works by sorting elements at specific intervals (gaps) and gradually reducing the gap
 * until it becomes 1, at which point the algorithm performs a standard insertion sort.
 * Characteristics:
 *  - not stable
 *  - in place
 * Time Complexity:
 * - Comparisons: Depends on the gap sequence used. For the 3x+1 sequence, it is approximately in
 *   n^1.289 and ~2.5n * lg(n) in practice.
 * - Exchanges: Depends on the gap sequence and the input array.
 * ShellSort is fast for medium-sized arrays but less efficient for very large arrays.
 */
public class ShellSort {

    /**
     * Sorts the given array using the Shell Sort algorithm.
     *
     * @param arr The array to be sorted.
     * @param <T> The type of elements in the array, which must implement Comparable.
     */
    public static <T extends Comparable<T>> void sort(T[] arr) {
        int n = arr.length; // Get the length of the array.

        // Determine the initial gap using the 3x + 1 sequence (1, 4, 13, 40, 121, 364, ...).
        int h = 1;
        while (h < n / 3) {
            h = 3 * h + 1; // Generate the next gap in the sequence.
        }

        // Perform Shell Sort with decreasing gaps.
        while (h >= 1) {
            // h-sort the array: Perform insertion sort for elements spaced h apart.
            for (int i = h; i < n; i++) {
                // Insert arr[i] into the sorted sequence of elements spaced h apart.
                for (int j = i; j >= h; j -= h) {
                    // If the current element is smaller than the element h positions behind it, swap them.
                    if (Sort.less(arr[j], arr[j - h])) {
                        Sort.swap(arr, j, j - h);
                    } else {
                        // If no swap is needed, break out of the inner loop.
                        break;
                    }
                }
            }
            // Reduce the gap for the next iteration.
            h = h / 3;
        }
    }
}