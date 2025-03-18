import edu.princeton.cs.algs4.StdOut;

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
public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size = 0;

    public Deque() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item must not to be a null");
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        if (isEmpty()) {
            last = first;
        } else {
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item must not to be a null");
        }

        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
            last.prev = oldLast;
        }
        size++;
    }

    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }

        size--;
        Node oldFirst = first;
        first = first.next;
        oldFirst.next = null;
        if (isEmpty()) {
            last = null;
        } else {
            first.prev = null;
        }
        return oldFirst.item;
    }

    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }

        size--;
        Node oldLast = last;
        last = last.prev;
        oldLast.prev = null;
        if (isEmpty()) {
            first = null;
        } else {
            last.next = null;
        }
        return oldLast.item;
    }

    public Iterator<Item> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported");
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException("Out of bound");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public static void main(String[] args) {
        var queue = new Deque<Integer>();
        debugPrint(queue);

        debugAddFirst(queue, 1);
        debugPrint(queue);

        debugRemoveFirst(queue);
        debugPrint(queue);

        debugAddLast(queue, 1);
        debugRemoveFirst(queue);
        debugPrint(queue);

        debugAddFirst(queue, 1);
        debugAddFirst(queue, 2);
        debugAddFirst(queue, 3);
        debugAddFirst(queue, 4);
        debugPrint(queue);

        debugRemoveLast(queue);
        debugPrint(queue);

        debugAddLast(queue, 10);
        debugRemoveFirst(queue);
        debugAddLast(queue, 11);
        debugRemoveFirst(queue);
        debugAddLast(queue, 12);
        debugRemoveFirst(queue);
        debugPrint(queue);

        debugAddFirst(queue, 101);
        debugAddLast(queue, 102);
        debugAddFirst(queue, 201);
        debugAddLast(queue, 202);
        debugAddFirst(queue, 301);
        debugAddLast(queue, 302);
        debugPrint(queue);

        debugRemoveFirst(queue);
        debugRemoveFirst(queue);
        debugRemoveFirst(queue);
        debugPrint(queue);

        debugRemoveLast(queue);
        debugRemoveLast(queue);
        debugRemoveLast(queue);
        debugPrint(queue);
    }

    private static void debugPrint(Deque<Integer> queue) {
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

    private static void debugAddFirst(Deque<Integer> queue, int value) {
        StdOut.println("Adding first: " + value);
        queue.addFirst(value);
    }

    private static void debugAddLast(Deque<Integer> queue, int value) {
        StdOut.println("Adding last: " + value);
        queue.addLast(value);
    }

    private static void debugRemoveFirst(Deque<Integer> queue) {
        StdOut.println("Removing first: " + queue.removeFirst());
    }

    private static void debugRemoveLast(Deque<Integer> queue) {
        StdOut.println("Removing last: " + queue.removeLast());
    }
}
