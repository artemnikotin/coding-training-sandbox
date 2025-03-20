package cts.common.sort;

import static cts.common.sort.Sort.less;
import static cts.common.sort.Sort.swap;

public class QuickSort {
    public static <T extends Comparable<T>> void sort(T[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static <T extends Comparable<T>> void quickSort(T[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    private static <T extends Comparable<T>> int partition(T[] array, int low, int high) {
        T pivot = array[high];
        int i = low;
        for (int j = low; j < high; j++) {
            if (less(array[j], pivot)) {
                swap(array, i++, j);
            }
        }
        // Swap pivot
        swap(array, i, high);
        return i;
    }
}
