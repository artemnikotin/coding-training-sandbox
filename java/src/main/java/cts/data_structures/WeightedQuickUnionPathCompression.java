package cts.data_structures;

/**
 * An implementation of the Union-Find data structure (Disjoint Set Union - DSU)
 * using weighted quick union with path compression optimization.
 * This data structure efficiently handles dynamic connectivity problems.
 */
public class WeightedQuickUnionPathCompression {
    /** Array to store parent of each element */
    private final int[] parents;

    /** Array to store size of each component (tree) */
    private final int[] size;

    /**
     * Initializes the Union-Find data structure with n elements.
     * Each element starts as its own parent with size 1.
     *
     * @param n The number of elements in the data structure
     * @throws IllegalArgumentException if n is less than 0
     */
    public WeightedQuickUnionPathCompression(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Number of elements must be non-negative");
        }
        parents = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i; // Each element is its own parent initially
            size[i] = 1; // Each tree has a size of 1 initially
        }
    }

    /**
     * Connects two elements by linking the root of the smaller tree to the root of the larger tree.
     * Implements union by size to keep trees relatively flat.
     *
     * @param p The first element to connect
     * @param q The second element to connect
     * @throws IllegalArgumentException if p or q are out of bounds
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);

        if (rootP == rootQ) {
            return; // Already connected
        }

        // Weighted union: attach smaller tree to larger tree
        if (size[rootP] < size[rootQ]) {
            parents[rootP] = rootQ;
            size[rootQ] += size[rootP]; // Update the size of the larger tree
        } else {
            parents[rootQ] = rootP;
            size[rootP] += size[rootQ]; // Update the size of the larger tree
        }
    }

    /**
     * Checks if two elements are in the same connected component.
     *
     * @param p The first element
     * @param q The second element
     * @return true if p and q are connected, false otherwise
     * @throws IllegalArgumentException if p or q are out of bounds
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * Finds the root of an element with path compression.
     * Path compression flattens the structure of the tree for faster future queries.
     *
     * @param p The element whose root is to be found
     * @return The root of the component containing p
     * @throws IllegalArgumentException if p is out of bounds
     */
    private int find(int p) {
        validate(p);
        while (p != parents[p]) {
            parents[p] = parents[parents[p]]; // Path compression
            p = parents[p];
        }
        return p;
    }

    /**
     * Validates that p is a valid index.
     *
     * @param p The index to validate
     * @throws IllegalArgumentException if p is out of bounds
     */
    private void validate(int p) {
        if (p < 0 || p >= parents.length) {
            throw new IllegalArgumentException("Index " + p + " is out of bounds");
        }
    }

    /**
     * Main method for testing the Union-Find implementation.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        WeightedQuickUnionPathCompression uf = new WeightedQuickUnionPathCompression(10);

        uf.union(1, 2);
        uf.union(3, 4);
        uf.union(5, 6);
        uf.union(7, 8);
        uf.union(2, 8);
        uf.union(4, 6);

        System.out.println("Are 1 and 8 connected? " + uf.connected(1, 8)); // true
        System.out.println("Are 3 and 5 connected? " + uf.connected(3, 5)); // true
        System.out.println("Are 0 and 9 connected? " + uf.connected(0, 9)); // false
    }
}