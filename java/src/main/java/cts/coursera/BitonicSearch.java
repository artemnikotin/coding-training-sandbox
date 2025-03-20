package cts.coursera;
/**
 * Coursera | Algorithms, Part I | Week 01
 * Search in a bitonic array.
 * An array is bitonic if it comprises an increasing sequence of integers followed immediately by a decreasing
 * sequence of integers. Write a program that, given a bitonic array of nn distinct integer values, determines
 * whether a given integer is in the array.
 */
public class BitonicSearch {
    public static int bitonicSearch(int[] arr, int target) {
        if (arr.length == 0) {
            return -1;
        }

        int index = findPeakIndex(arr);
        int ascIndex = ascendingBinarySearch(arr, index, target);
        return ascIndex != -1
                ? ascIndex
                : descendingBinarySearch(arr, index + 1, target);
    }

    public static int noPeakBitonicSearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid;
            }

            if (mid < arr.length - 1 && arr[mid] < arr[mid + 1]) {
                // mid is in the ascending part
                if (arr[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {
                // mid is in the descending part
                if (arr[mid] < target) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }

        return -1;
    }

    private static int ascendingBinarySearch(int[] arr, int right, int target) {
        int left = 0;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] < target) {
                left = mid + 1;
            } else if (arr[mid] > target) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    private static int descendingBinarySearch(int[] arr, int left, int target) {
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] < target) {
                right = mid - 1;
            } else if (arr[mid] > target) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    private static int findPeakIndex(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int mid = (left + right) / 2;
            if (arr[mid] > arr[mid + 1]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        int[] arr;

        arr = new int[]{};
        System.out.println(BitonicSearch.bitonicSearch(arr, 20)); // Output -1
        System.out.println(BitonicSearch.noPeakBitonicSearch(arr, 20)); // Output -1

        arr = new int[]{10};
        System.out.println(BitonicSearch.bitonicSearch(arr, 20)); // Output -1
        System.out.println(BitonicSearch.noPeakBitonicSearch(arr, 20)); // Output -1
        System.out.println(BitonicSearch.bitonicSearch(arr, 10)); // Output 0
        System.out.println(BitonicSearch.noPeakBitonicSearch(arr, 10)); // Output 0

        arr = new int[]{-3, 9, 18, 20, 19, 14, 7};
        System.out.println(BitonicSearch.bitonicSearch(arr, 19)); // Output 4
        System.out.println(BitonicSearch.noPeakBitonicSearch(arr, 19)); // Output 4
    }
}
