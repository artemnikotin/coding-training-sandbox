import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

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
public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double mean;
    private final double stddev;
    private final int trials;

    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("The grid size must be greater than zero");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("The amount if experiments must be greater than zero");
        }

        this.trials = trials;
        var x = new double[trials];
        for (int i = 0; i < trials; i++) {
            x[i] = (double) simulate(n) / (n * n);
        }
        mean = StdStats.mean(x);
        stddev = StdStats.stddev(x);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return mean - CONFIDENCE_95 * stddev / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean + CONFIDENCE_95 * stddev / Math.sqrt(trials);
    }

    private int simulate(int n) {
        var percolation = new Percolation(n);
        while (!percolation.percolates()) {
            percolation.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
        }
        return percolation.numberOfOpenSites();
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("You should pass following arguments: grid size, number of independent experiments");
        }
        var n = Integer.parseInt(args[0]);
        var trails = Integer.parseInt(args[1]);
        var stats = new PercolationStats(n, trails);

        StdOut.printf("%-23s = %f\n", "mean", stats.mean());
        StdOut.printf("%-23s = %f\n", "stddev", stats.stddev());
        StdOut.printf("%-23s = [%f, %f]\n", "95% confidence interval", stats.confidenceLo(), stats.confidenceHi());
    }
}
