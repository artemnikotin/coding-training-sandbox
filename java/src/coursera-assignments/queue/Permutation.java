import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Coursera | Algorithms, Part I | Week 02 Assignment
 * ASSESSMENT SUMMARY
 * Compilation:  PASSED (0 errors, 3 warnings)
 * API:          PASSED
 * SpotBugs:     PASSED
 * PMD:          PASSED
 * Checkstyle:   PASSED
 * Correctness:  49/49 tests passed
 * Memory:       124/123 tests passed
 * Timing:       193/193 tests passed
 * Aggregate score: 100.08%
 */
public class Permutation {
    public static void main(String[] args) {
        var k = Integer.parseInt(args[0]);

        var queue = new RandomizedQueue<String>();
        int n = 0;
        while (!StdIn.isEmpty()) {
            n++;
            var symbol = StdIn.readString();
            if (queue.size() >= k) {
                if (StdRandom.uniform() > (double) k/n) {
                    continue;
                }
                queue.dequeue();
            }
            queue.enqueue(symbol);
        }
        for (String s : queue) {
            StdOut.println(s);
        }
    }
}
