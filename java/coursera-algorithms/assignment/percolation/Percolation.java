import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Coursera | Algorithms, Part I | Week 01 Assignment
 * ASSESSMENT SUMMARY
 * Compilation:  PASSED
 * API:          PASSED
 * SpotBugs:     PASSED
 * PMD:          PASSED
 * Checkstyle:   PASSED
 * Correctness:  38/38 tests passed
 * Memory:       8/8 tests passed
 * Timing:       20/20 tests passed
 * Aggregate score: 100.00%
 */
public class Percolation {
    private final int n;
    private final boolean[] opened;
    private final WeightedQuickUnionUF fullWQU;
    private final WeightedQuickUnionUF inWQU; // backwash-problem
    private int openedAmount = 0;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("The grid size must be greater than zero");
        }
        this.n = n;
        opened = new boolean[n * n];
        fullWQU = new WeightedQuickUnionUF(n * n + 2);
        inWQU = new WeightedQuickUnionUF(n * n + 1);
    }

    public void open(int row, int col) {
        var index = getIndex(row, col);
        if (opened[index]) {
            return;
        }
        opened[index] = true;
        openedAmount++;

        if (row == 1) {
            fullWQU.union(getInIndex(), index);
            inWQU.union(getInIndex(), index);
        } else if (opened[getIndex(row - 1, col)]) {
            fullWQU.union(getIndex(row - 1, col), index);
            inWQU.union(getIndex(row - 1, col), index);
        }
        if (row == n) {
            fullWQU.union(getOutIndex(), index);
        } else if (opened[getIndex(row + 1, col)]) {
            fullWQU.union(getIndex(row + 1, col), index);
            inWQU.union(getIndex(row + 1, col), index);
        }
        if (col != 1 && opened[getIndex(row, col - 1)]) {
            fullWQU.union(getIndex(row, col - 1), index);
            inWQU.union(getIndex(row, col - 1), index);
        }
        if (col != n && opened[getIndex(row, col + 1)]) {
            fullWQU.union(getIndex(row, col + 1), index);
            inWQU.union(getIndex(row, col + 1), index);
        }
    }

    public boolean isOpen(int row, int col) {
        return opened[getIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        return inWQU.find(getIndex(row, col)) == inWQU.find(getInIndex());
    }

    public int numberOfOpenSites() {
        return openedAmount;
    }

    public boolean percolates() {
        return fullWQU.find(getOutIndex()) == fullWQU.find(getInIndex());
    }

    private int getIndex(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("the index must be between 1 and " + n + " inclusive, got (" + row + ", " + col + ")");
        }
        return n * (row - 1) + col - 1;
    }

    private int getInIndex() {
        return n * n;
    }

    private int getOutIndex() {
        return n * n + 1;
    }
}
