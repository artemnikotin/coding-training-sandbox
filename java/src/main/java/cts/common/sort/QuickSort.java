package cts.common.sort;

/**
 * QuickSort is a highly efficient sorting algorithm that uses a divide-and-conquer approach.
 * It works by selecting a "pivot" element from the array and partitioning the other elements
 * into two slices: one with elements less than the pivot and one with elements greater than the pivot.
 * The slices are then recursively sorted.
 * Characteristics:
 *  - not stable
 *  - in place
 * Time Complexity:
 * - Best/Average Case: O(n log n)
 * - Worst Case: O(n^2) (occurs when the pivot selection is poor, e.g., already sorted or reverse-sorted arrays)
 * Space Complexity: O(log n) (due to recursion stack)
 */
public class QuickSort {

    /**
     * Public method to initiate the QuickSort process.
     *
     * @param arr The array to be sorted.
     * @param <T> The type of elements in the array, which must implement Comparable.
     */
    public static <T extends Comparable<T>> void sort(T[] arr) {
        Sort.shuffle(arr);
        sort(arr, 0, arr.length - 1);
    }

    /**
     * Recursive method to perform QuickSort on a slice.
     *
     * @param array The array to be sorted.
     * @param low   The starting index of the slice.
     * @param high  The ending index of the slice.
     * @param <T>   The type of elements in the array, which must implement Comparable.
     */
    private static <T extends Comparable<T>> void sort(T[] array, int low, int high) {
        // Base case: If the slice has one or zero elements, it is already sorted.
        if (low < high) {
            // Partition the array and get the pivot index.
            int pivotIndex = partition(array, low, high);
            // Recursively sort the left slice (elements less than the pivot).
            sort(array, low, pivotIndex - 1);
            // Recursively sort the right slice (elements greater than the pivot).
            sort(array, pivotIndex + 1, high);
        }
    }

    /**
     * Partitions the array around a pivot element.
     * Elements less than the pivot are moved to the left of the pivot,
     * and elements greater than the pivot are moved to the right of the pivot.
     *
     * @param array The array to be partitioned.
     * @param low   The starting index of the slice to be partitioned.
     * @param high  The ending index of the slice to be partitioned.
     * @param <T>   The type of elements in the array, which must implement Comparable.
     * @return The index of the pivot element after partitioning.
     */
    private static <T extends Comparable<T>> int partition(T[] array, int low, int high) {
        // Choose the pivot element (in this implementation, the last element is used as the pivot).
        T pivot = array[high];
        // Initialize the index for the smaller element.
        int i = low;

        // Iterate over the slice from low to high - 1.
        for (int j = low; j < high; j++) {
            // If the current element is less than the pivot, swap it with the element at index i.
            if (Sort.less(array[j], pivot)) {
                Sort.swap(array, i++, j);
            }
        }
        // Swap the pivot element with the element at index i.
        // This places the pivot in its correct sorted position.
        Sort.swap(array, i, high);
        // Return the index of the pivot.
        return i;
    }
}