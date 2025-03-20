package cts.coursera;
import java.util.ArrayList;
import java.util.Random;

/**
 * Coursera | Algorithms, Part I | Week 04
 * Randomized priority queue.
 * Describe how to add the methods sample() and delRandom() to our binary heap implementation. The two methods return
 * a key that is chosen uniformly at random among the remaining keys, with the latter method also removing that key.
 * The sample() method should take constant time; the delRandom() method should take logarithmic time.
 * Do not worry about resizing the underlying array.
 */
public class RandomizedPriorityQueue<Key extends Comparable<Key>> {
    private static final Random rand = new Random();
    private final ArrayList<Key> items = new ArrayList<>();
    private int n = 0;

    RandomizedPriorityQueue() {
        items.add(null);
    }

    /**
     * Add node at end, then swim it up.
     */
    public void insert(Key x) {
        n++;
        if (n >= items.size()) {
            items.add(x);
        } else {
            items.add(n, x);
        }
        swim(n);
    }

    public Key delMax() {
        Key max = items.get(1);
        exchange(1, n--);
        sink(1);
        items.remove(n + 1);
        return max;
    }

    public Key delRandom() {
        int k = rand.nextInt(n) + 1;
        return remove(k);
    }

    public Key sample() {
        return items.get(rand.nextInt(n) + 1);
    }

    public boolean isEmpty() {
        return n == 0;
    }


    /**
     * Child's key becomes larger key than its parent's key.
     */
    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exchange(k, k / 2);
            k = k / 2;
        }
    }

    /**
     * Parent's key becomes smaller than one (or both) of its children's.
     */
    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && less(j, j + 1)) {
                j++;
            }
            if (!less(k, j)) {
                break;
            }
            exchange(k, j);
            k = j;
        }
    }

    /**
     * Remove an arbitrary item.
     */
    private Key remove(int k) {
        Key value = items.get(k);

        if (n == k) {
            items.remove(n--);
            return value;
        }

        exchange(n, k);
        boolean becomeLess = items.get(k).compareTo(items.get(n)) < 0;
        items.remove(n--);
        if (becomeLess) {
            sink(k);
        } else {
            swim(k);
        }

        return value;
    }

    private boolean less(int i, int j) {
        return items.get(i).compareTo(items.get(j)) < 0;
    }

    private void exchange(int i, int j) {
        Key swap = items.get(i);
        items.set(i, items.get(j));
        items.set(j, swap);
    }

    public static void main(String[] args) {
        RandomizedPriorityQueue<Integer> pq = new RandomizedPriorityQueue<>();
        pq.insert(1);
        pq.insert(6);
        pq.insert(5);
        System.out.println(pq.delMax()); // Output: 6
        System.out.println(pq.sample()); // Output: 1 | 5
        System.out.println(pq.delRandom()); // Output: 1 | 5
        System.out.println(pq.delRandom()); // Output: 5 | 1
        System.out.println(pq.isEmpty()); // Output: true
    }
}
