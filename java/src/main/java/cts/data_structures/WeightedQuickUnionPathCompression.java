package cts.data_structures;

public class WeightedQuickUnionPathCompression {
    private int[] parent;
    private int[] size; // To keep track of the size of each tree

    // Constructor to initialize the parent and size arrays
    public WeightedQuickUnionPathCompression(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i; // Each element is its own parent initially
            size[i] = 1; // Each tree has a size of 1 initially
        }
    }

    // Union two elements by linking the root of the smaller tree to the root of the
    // larger tree
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);

        if (rootP == rootQ) {
            return; // Already connected
        }

        // Attach the smaller tree to the larger tree
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP]; // Update the size of the larger tree
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ]; // Update the size of the larger tree
        }
    }

    // Check if two elements are connected
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // Find the root of the element with path compression
    private int find(int p) {
        while (p != parent[p]) {
            parent[p] = parent[parent[p]]; // Path compression
            p = parent[p];
        }
        return p;
    }

    // Main method for testing
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
