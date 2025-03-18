import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

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
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size = 0;
    private int first = 0;
    private int last = 0;

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item must not to be a null");
        }

        if (size == items.length) {
            resize(2 * items.length);
        }
        last++;
        size++;
        if (last >= items.length) {
            last = 0;
        }
        int randomIndex = (first + StdRandom.uniform(size)) % items.length;
        if (randomIndex != last) {
            Item tmp = item;
            item = items[randomIndex];
            items[randomIndex] = tmp;
        }
        items[last] = item;
    }

    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }

        var item = items[first];
        items[first] = null;
        first++;
        if (first >= items.length) {
            first = 0;
        }
        size--;
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        return items[(first + StdRandom.uniform(size)) % items.length];
    }

    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = items[(first + i) % items.length];
        }
        items = copy;
        first = 0;
        last = size - 1;
    }

    private class RandomIterator implements Iterator<Item> {
        private int current = 0;
        private final Item[] copy;

        public RandomIterator() {
            copy = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                copy[i] = items[(first + i) % items.length];
            }
        }

        public boolean hasNext() {
            return current < copy.length;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported");
        }

        public Item next() {
            int rest = copy.length - current;
            if (rest == 0) {
                throw new NoSuchElementException("Out of bound");
            }
            if (rest > 0) {
                int randomIndex = current + StdRandom.uniform(rest);
                if (current != randomIndex) {
                    Item temp = copy[current];
                    copy[current] = copy[randomIndex];
                    copy[randomIndex] = temp;
                }
            }

            Item item = copy[current];
            current++;
            return item;
        }
    }

    public static void main(String[] args) {
        var queue = new RandomizedQueue<Integer>();
        debugPrint(queue);

        debugEnqueue(queue, 1);
        debugDequeue(queue);
        debugPrint(queue);

        debugEnqueue(queue, 1);
        debugEnqueue(queue, 2);
        debugEnqueue(queue, 3);
        debugEnqueue(queue, 4);
        debugPrint(queue);

        debugDequeue(queue);
        debugPrint(queue);

        debugDequeue(queue);
        debugDequeue(queue);
        debugDequeue(queue);
        debugPrint(queue);

        debugEnqueue(queue, 100);
        debugEnqueue(queue, 101);
        debugEnqueue(queue, 102);
        debugEnqueue(queue, 103);
        debugEnqueue(queue, 104);
        debugEnqueue(queue, 105);
        debugEnqueue(queue, 106);
        debugEnqueue(queue, 107);
        debugEnqueue(queue, 108);
        debugEnqueue(queue, 109);

        StdOut.println("Sample: " + queue.sample());

        debugPrint(queue);
        debugPrint(queue);
        debugPrint(queue);

        debugDequeue(queue);
        debugDequeue(queue);
        debugDequeue(queue);
        debugDequeue(queue);
        debugPrint(queue);
    }

    private static void debugPrint(RandomizedQueue<Integer> queue) {
        StdOut.print("Queue: ");
        if (queue.isEmpty()) {
            StdOut.print("<empty> ");
        }
        for (Integer i : queue) {
            StdOut.print(i);
            StdOut.print(" ");
        }
        StdOut.print("(size: " + queue.size() + ")");
        StdOut.println();
    }

    private static void debugEnqueue(RandomizedQueue<Integer> queue, int value) {
        StdOut.println("Adding: " + value);
        queue.enqueue(value);
    }

    private static void debugDequeue(RandomizedQueue<Integer> queue) {
        StdOut.println("Removing: " + queue.dequeue());
    }
}
