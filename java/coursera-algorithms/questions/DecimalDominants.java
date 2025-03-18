import java.util.ArrayList;

/**
 * Coursera | Algorithms, Part I | Week 03
 * Decimal dominants.
 * Given an array with n keys, design an algorithm to find all values that occur more than n/10 times. The expected
 * running time of your algorithm should be linear.
 */
public class DecimalDominants {
    private final int[] arr;
    private final ArrayList<Integer> dd;

    DecimalDominants(int[] arr) {
        this.arr = arr.clone();
        this.dd = new ArrayList<>();
        count(0, arr.length - 1);
    }

    public ArrayList<Integer> getDd() {
        return dd;
    }

    private void count(int lo, int hi) {
        // we get linear time on average due to the fact that it makes no sense
        // to search for slices shorter than N / 10
        if (hi - lo < arr.length / 10) {
            return;
        }

        int lt = lo, gt = hi;
        int v = arr[lo];
        int i = lo;
        while (i <= gt) {
            if (arr[i] < arr[lo]) {
                swap(arr, lt++, i++);
            } else if (arr[i] > arr[lo]) {
                swap(arr, i, gt--);
            } else {
                i++;
            }
        }
        int occurrences = gt - lt + 1;
        if (occurrences > arr.length / 10) {
            dd.add(v);
        }
        count(lo, lt - 1);
        count(gt + 1, hi);
    }

    private static void swap(int[] arr, int i, int j) {
        int swap = arr[i];
        arr[i] = arr[j];
        arr[j] = swap;
    }

    public static void main(String[] args) {
        int[] arr = {
                1, 2, 1, 2, 1, 2, 1, 4, 3, 2, 4, 2, 3, 2, 6, 7, 8, 9,
                7, 6, 2, 1, 1, 1, 5, 5, 6, 3, 2, 4, 5, 2, 3, 6, 7, 8,
                3, 2, 5, 1, 1, 2, 2, 2, 7, 6, 5, 2, 1, 2, 4, 5, 6, 8,
        };
        var dd = new DecimalDominants(arr);
        System.out.println(dd.getDd()); // Output: [1, 2, 5]
    }
}
