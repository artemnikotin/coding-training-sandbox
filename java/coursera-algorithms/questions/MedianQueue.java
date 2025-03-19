import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Coursera | Algorithms, Part I | Week 04
 * Dynamic median.
 * Design a data type that supports insert in logarithmic time, find-the-median in constant time, and remove-the-median
 * in logarithmic time. If the number of keys in the data type is even, find/remove the lower median.
 */
public class MedianQueue {
    private final PriorityQueue<Integer> smaller = new PriorityQueue<>(new DescendingComparator());
    private final PriorityQueue<Integer> bigger = new PriorityQueue<>();

    public void add(Integer key) {
        if (smaller.isEmpty()) {
            smaller.add(key);
            return;
        }
        if (key < smaller.peek()) {
            smaller.add(key);
        } else {
            bigger.add(key);
        }
        rebalance();
    }

    public Integer remove() {
        if (smaller.isEmpty()) {
            return null;
        }
        var median = smaller.remove();
        rebalance();
        return median;
    }

    public boolean isEmpty() {
        return bigger.isEmpty() && smaller.isEmpty();
    }

    public Integer peak() {
        if (smaller.isEmpty()) {
            return null;
        }
        return smaller.peek();
    }

    public int size() {
        return bigger.size() + smaller.size();
    }

    public String toString() {
        return smaller + " " + bigger + " (median: " + peak() + ", size " + size() + ")";
    }

    private void rebalance() {
        while (bigger.size() - smaller.size() > 0) {
            smaller.add(bigger.remove());
        }
        while (smaller.size() - bigger.size() > 1) {
            bigger.add(smaller.remove());
        }
    }

    private static class DescendingComparator implements Comparator<Integer> {
        public int compare(Integer o1, Integer o2) {
            return -1 * o1.compareTo(o2);
        }
    }

    public static void main(String[] args) {
        var mq = new MedianQueue();

        mq.add(10);
        System.out.println(mq);
        System.out.println(mq.remove()); // Output: 10
        System.out.println(mq.isEmpty()); // Output: true
        System.out.println(mq);

        mq.add(5);
        mq.add(10);
        System.out.println(mq);

        mq.add(2);
        System.out.println(mq);

        mq.add(6);
        mq.add(8);
        System.out.println(mq);
    }
}
