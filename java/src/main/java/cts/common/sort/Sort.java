package cts.common.sort;

import java.util.Random;

public class Sort {

    private static final Random rand = new Random();

    public static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static <T extends Comparable<T>> boolean less(T a, T b) {
        return a.compareTo(b) < 0;
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] arr) {
        return isSorted(arr, 0, arr.length - 1);
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] arr, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(arr[i], arr[i - 1])) {
                return false;
            }
        }
        return true;
    }

    public static void shuffle(Object[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int r = rand.nextInt(i + 1);
            swap(arr, i, r);
        }
    }

}
