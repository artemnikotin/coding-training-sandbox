import java.util.HashMap;

/**
 * Coursera | Algorithms, Part I | Week 06
 * 4-SUM.
 * Given an array a[] of n integers, the 4-SUM problem is to determine if there exist distinct indices i, j, k, and l
 * such that a[i] + a[j] = a[k] + a[l]. Design an algorithm for the 4-SUM problem that takes time proportional to n^2.
 */
public class FourSum {
    public static boolean fourSumExists(int[] a) {
        int n = a.length;
        if (n < 4) {
            return false;
        }

        // Create a hash map to store the sum of pairs and their indices
        HashMap<Integer, Pair> sumMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int sum = a[i] + a[j];

                if (sumMap.containsKey(sum)) {
                    Pair pair = sumMap.get(sum);

                    if (pair.i != i && pair.i != j && pair.j != i && pair.j != j) {
                        return true;
                    }
                } else {
                    sumMap.put(sum, new Pair(i, j));
                }
            }
        }

        return false;
    }

    private static class Pair {
        final int i, j;

        Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4};
        System.out.println(fourSumExists(a)); // Output: true

        int[] b = {1, 2, 3, 5};
        System.out.println(fourSumExists(b)); // Output: false
    }
}
