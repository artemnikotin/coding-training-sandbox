package cts.common.sort;

import java.util.Comparator;
import java.util.Random;

public class Sort {

    private static final Random rand = new Random();

    public static void swap(Object[] arr, int i, int j) {
        Object temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static <T extends Comparable<T>> boolean less(T a, T b) {
        return a.compareTo(b) < 0;
    }

    public static <T> boolean less(Comparator<T> comparator, T a, T b) {
        return comparator.compare(a, b) < 0;
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] arr) {
        return isSorted(arr, 0, arr.length - 1);
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] arr, Comparator<T> comparator) {
        return isSorted(arr, 0, arr.length - 1, comparator);
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] arr, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(arr[i], arr[i - 1])) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean isSorted(T[] arr, int lo, int hi, Comparator<T> comparator) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(comparator, arr[i], arr[i - 1])) {
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

    public static class NaturalOrderComparator<T extends Comparable<T>> implements Comparator<T> {
        @Override
        public int compare(T o1, T o2) {
            return o1.compareTo(o2);
        }
    }
}
