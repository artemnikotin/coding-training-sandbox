import java.util.Arrays;

/**
 * Coursera | Algorithms, Part I | Week 02
 * Permutation.
 * Given two integer arrays of size n, design a subquadratic algorithm to determine whether one is a permutation
 * of the other. That is, do they contain exactly the same entries but, possibly, in a different order.
 */
public class Permutation {
    public static boolean isPermutation(int[] a, int[] b) {
        if (a.length != b.length) {
            return false;
        }

        Arrays.sort(a);
        Arrays.sort(b);
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4};
        int[] b = {4, 2, 1, 3};
        System.out.println(Permutation.isPermutation(a, b)); // Output: true
    }
}
