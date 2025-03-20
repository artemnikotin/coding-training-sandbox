package cts.common.sort;

/**
 * Compares: ~ n^2 / 2
 * Exchanges: n
 */
public class SelectionSort {
    public static <T extends Comparable<T>> void sort(T[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (Sort.less(arr[j], arr[minIndex])) {
                    minIndex = j;
                }
            }
            Sort.swap(arr, i, minIndex);
        }
    }
}
