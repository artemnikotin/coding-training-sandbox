package cts.coursera;
import java.util.Arrays;

/**
 * Coursera | Algorithms, Part I | Week 02
 * Dutch national flag.
 * Given an array of nn buckets, each containing a red, white, or blue pebble, sort them by color. The allowed operations are:
 *  - swap(i,j): swap the pebble in bucket ii with the pebble in bucket j.
 *  - color(i): determine the color of the pebble in bucket i.
 * The performance requirements are as follows:
 *  - At most n calls to color().
 *  - At most n calls to swap().
 *  - Constant extra space.
 */
public class DutchFlag {
    public final static int RED = 0;
    public final static int WHITE = 1;
    public final static int BLUE = 2;

    private int colorCount = 0;
    private int swapCount = 0;
    private final int[] arr;

    public DutchFlag(int[] arr) {
        this.arr = arr;
    }

    public void sort() {
        if (arr.length < 2) {
            return;
        }
        int left = 0;
        int right = arr.length - 1;
        int i = 0;

        while (i <= right) {
            int color = color(arr, i);
            if (color == RED) {
                if (i != left) {
                    swap(arr, i, left);
                }
                left++;
                i++;
            } else if (color == BLUE) {
                swap(arr, i, right);
                right--;
            } else {
                i++;
            }
        }
    }

    public int getColorCount() {
        return colorCount;
    }

    public int getSwapCount() {
        return swapCount;
    }

    public int[] getBug() {
        return arr;
    }

    private int color(int[] arr, int i) {
        colorCount++;
        return arr[i];
    }

    private void swap(int[] arr, int i, int j) {
        swapCount++;
        int swap = arr[i];
        arr[i] = arr[j];
        arr[j] = swap;
    }

    public static void main(String[] args) {
        int[][] bags = {
                {RED, RED, RED},
                {WHITE, WHITE, WHITE},
                {BLUE, BLUE, BLUE},
                {RED, WHITE, BLUE},
                {BLUE, WHITE, WHITE, RED},
                {WHITE, BLUE, RED, WHITE},
                {RED, WHITE, BLUE, BLUE, RED, WHITE, WHITE, WHITE, BLUE, RED, RED},
        };

        for (int[] bag : bags) {
            var flag = new DutchFlag(bag);
            flag.sort();
            System.out.println(Arrays.toString(flag.getBug()));
            System.out.printf("Size: %s, color calls: %s, swap calls: %s", flag.getBug().length, flag.getColorCount(), flag.getSwapCount());
            System.out.println();
            System.out.println();
        }
    }
}
