package cts.common.sort;

/**
 * Compares: ~ n^2 / 2
 * Exchanges: ~ n^2 / 2
 */
public class BubbleSort {
    public static <T extends Comparable<T>> void sort(T[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (Sort.less(arr[j + 1], arr[j])) {
                    Sort.swap(arr, j, j + 1);
                }
            }
        }
    }
}
