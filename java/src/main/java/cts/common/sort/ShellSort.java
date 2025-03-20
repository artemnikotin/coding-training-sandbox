package cts.common.sort;

/**
 * Fast unless array size is huge
 * Compares: (n^1.289; 2.5n * lg(n))
 */
public class ShellSort {
    public static <T extends Comparable<T>> void sort(T[] arr) {
        int n = arr.length;

        int h = 1;
        while (h < n / 3) {
            h = 3 * h + 1; // 1, 4, 13, 40, 121, 364
        }

        while (h >= 1) {
            // h-sort the array
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h; j -= h) {
                    if (Sort.less(arr[j], arr[j - h])) {
                        Sort.swap(arr, j, j - h);
                    } else {
                        break;
                    }
                }
            }
            h = h / 3;
        }
    }
}
