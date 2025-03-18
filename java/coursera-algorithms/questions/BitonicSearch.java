/**
 * Coursera | Algorithms, Part I | Week 01
 * Search in a bitonic array.
 * An array is bitonic if it comprises an increasing sequence of integers followed immediately by a decreasing
 * sequence of integers. Write a program that, given a bitonic array of nn distinct integer values, determines
 * whether a given integer is in the array.
 */
public class BitonicSearch {
    public static int bitonicSearch(int[] arr, int el) {
        int index = findBitonicIndex(arr);
        int ascIndex = ascendingBinarySearch(arr, 0, index, el);
        return ascIndex != -1
                ? ascIndex
                : descendingBinarySearch(arr, index + 1, arr.length, el);
    }

    public static int fastBitonicSearch(int[] arr, int el) {
        int l = 0;
        int r = arr.length;
        while (l < r) {
            int mid = (l + r) / 2;
            if (el == arr[mid]) {
                return mid;
            }
            if (el < arr[mid]) {
                int ascIndex = ascendingBinarySearch(arr, l, mid, el);
                return ascIndex != -1
                        ? ascIndex
                        : descendingBinarySearch(arr, mid + 1, r, el);
            }
            if(arr[mid] > arr[mid-1]) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return -1;
    }

    private static int ascendingBinarySearch(int[] arr, int l, int r, int el) {
        while (l < r) {
            int mid = (l + r) / 2;
            if (el > arr[mid]) {
                l = mid + 1;
                continue;
            } else if (el < arr[mid]) {
                r = mid;
                continue;
            }
            return mid;
        }
        return -1;
    }

    private static int descendingBinarySearch(int[] arr, int l, int r, int el) {
        while (l < r) {
            int mid = (l + r) / 2;
            if (el > arr[mid]) {
                r = mid;
                continue;
            } else if (el < arr[mid]) {
                l = mid + 1;
                continue;
            }
            return mid;
        }
        return -1;
    }

    private static int findBitonicIndex(int[] nums) {
        int i = 0;
        int j = nums.length - 1;

        while (i < j) {
            int mid = (i + j) / 2;
            if (nums[mid - 1] < nums[mid] && nums[mid] < nums[mid + 1]) {
                i = mid + 1;
                continue;
            } else if (nums[mid + 1] < nums[mid] && nums[mid] < nums[mid - 1]) {
                j = mid - 1;
                continue;
            }
            return mid;
        }
        return i;
    }

    public static void main(String[] args) {
        int[] arr = {-3, 9, 18, 20, 17, 5, 1};
        System.out.println(BitonicSearch.bitonicSearch(arr, 17)); // Output 4
        System.out.println(BitonicSearch.fastBitonicSearch(arr, 17)); // Output 4
    }
}
