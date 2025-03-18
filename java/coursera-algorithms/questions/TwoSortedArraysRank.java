/**
 * Coursera | Algorithms, Part I | Week 03
 * Selection in two sorted arrays.
 * Given two sorted arrays a[] and b[], of lengths n1 and n2 and an integer 0 ≤ k < n1 + n2, design an algorithm
 * to find a key of rank k. The order of growth of the worst case running time of your algorithm should be n log(n),
 * where n = n1 + n2.
 */
public class TwoSortedArraysRank {
    public static int find(int[] a, int[] b, int k) {
        return find(a, 0, b, 0, k);
    }

    private static int find(int[] a, int aStart, int[] b, int bStart, int k) {
        int aSize = a.length - aStart;
        int bSize = b.length - bStart;

        // Swap slices so the a[] slice is smaller than the b[] slice
        if (aSize > bSize) {
            return find(b, bStart, a, aStart, k);
        }

        if (k == 1) {
            return Math.min(a[aStart], b[bStart]);
        }
        if (aSize == 0) {
            return b[bStart + k - 1];
        }

        int aPart = Math.min(k / 2, aSize);
        int bPart = k - aPart;

        int aLast = a[aStart + aPart - 1];
        int bLast = b[bStart + bPart - 1];

        if (aLast < bLast) {
            // Discard the partA from a[]
            return find(a, aStart + aPart, b, bStart, k - aPart);
        } else if (aLast > bLast) {
            // Discard the partB from b[]
            return find(a, aStart, b, bStart + bPart, k - bPart);
        } else {
            return aLast;
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 5, 7};
        int[] b = {2, 4, 6, 8};
        System.out.println(find(a, b, 4)); // Output: 4
    }
}
