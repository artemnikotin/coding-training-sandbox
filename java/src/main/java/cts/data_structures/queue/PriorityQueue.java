package cts.data_structures.queue;

import cts.data_structures.ResizingArray;
import java.util.Comparator;
import java.util.Iterator;

/**
 * A priority queue implementation using a binary heap with a resizing array.
 * The priority order is determined by the provided Comparator.
 *
 * @param <T> the type of elements in this priority queue
 */
public class PriorityQueue<T> implements Queue<T> {
    // Underlying resizable array to store heap elements
    private final ResizingArray<T> items;
    // Comparator to determine the priority order
    private final Comparator<T> comparator;

    /**
     * Constructs a priority queue with the given comparator.
     * @param comparator the comparator to determine priority order
     */
    PriorityQueue(Comparator<T> comparator) {
        items = new ResizingArray<>();
        this.comparator = comparator;
    }

    /**
     * Adds an element to the priority queue.
     * @param x the element to add
     */
    @Override
    public void enqueue(T x) {
        items.addLast(x);  // Add to the end
        swim(size() - 1);  // Restore heap order by swimming the new element up
    }

    /**
     * Removes and returns the highest priority element.
     * @return the highest priority element
     * @throws IllegalStateException if the queue is empty
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        T peak = items.get(0);  // Get the root (highest priority element)
        T last = items.removeLast();  // Remove the last element
        if (size() > 0) {
            items.set(0, last);  // Move last element to root
            sink(0);  // Restore heap order by sinking the new root down
        }
        return peak;
    }

    /**
     * Returns (without removing) the highest priority element.
     * @return the highest priority element
     * @throws IllegalStateException if the queue is empty
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return items.get(0);  // Root is always the highest priority element
    }

    /**
     * Checks if the queue is empty.
     * @return true if empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Returns the number of elements in the queue.
     * @return the number of elements
     */
    @Override
    public int size() {
        return items.size();
    }

    /**
     * Returns an iterator over the elements in the queue.
     * Note: The iterator does not guarantee any particular order.
     * @return an iterator
     */
    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }

    /**
     * Removes the element at the specified index.
     * @param index the index of the element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public T remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for queue length " + size());
        }
        if (index == size() - 1) {
            return items.removeLast();
        }
        T value = items.get(index);
        T last = items.removeLast();

        items.set(index, last);  // Replace removed element with last element
        // Restore heap order by moving the replacement element up or down as needed
        if (comparator.compare(last, value) < 0) {
            sink(index);
        } else {
            swim(index);
        }

        return value;
    }

    /**
     * Get the element at the specified index.
     * @param index the index of the element to remove
     * @return the element
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for queue length " + size());
        }
        return items.get(index);
    }

    /**
     * Reorders the heap after the element at the specified index may have changed.
     * @param index the index of the element that may have changed
     */
    public void doOnOrderChanged(int index) {
        sink(index);  // Try to sink the element
        swim(index);  // Try to swim the element
    }

    /**
     * Reorders the entire heap (heapify operation).
     */
    public void doOnOrderChanged() {
        heapify();
    }

    /**
     * Moves the element at the specified index up the heap to restore heap order.
     * @param current the index of the element to swim up
     */
    private void swim(int current) {
        while (current > 0) {
            int parent = (current - 1) / 2;  // Calculate parent index
            if (!less(parent, current)) {  // If parent has higher priority, stop
                break;
            }
            exchange(parent, current);  // Swap with parent
            current = parent;  // Move to parent position
        }
    }

    /**
     * Moves the element at the specified index down the heap to restore heap order.
     * @param current the index of the element to sink down
     */
    private void sink(int current) {
        int half = size() / 2;  // Only need to sink non-leaf nodes
        while (current < half) {
            int left = (2 * current) + 1;  // Left child index
            int right = left + 1;           // Right child index
            // Choose the child with higher priority
            if (right < size() && less(left, right)) {
                left = right;
            }
            if (!less(current, left)) {  // If current has higher priority than children, stop
                break;
            }
            exchange(current, left);  // Swap with higher priority child
            current = left;  // Move to child position
        }
    }

    /**
     * Builds a heap from the unordered array (heapify operation).
     */
    private void heapify() {
        int n = size(), i = (n >>> 1) - 1;  // Start from the last non-leaf node
        while (i >= 0) {
            sink(i);  // Sink each non-leaf node
            i--;
        }
    }

    /**
     * Compares two elements at the given indices.
     * @param i first element index
     * @param j second element index
     * @return true if element at i has higher priority than element at j
     */
    private boolean less(int i, int j) {
        return this.comparator.compare(items.get(i), items.get(j)) < 0;
    }

    /**
     * Swaps two elements in the heap.
     * @param i first element index
     * @param j second element index
     */
    private void exchange(int i, int j) {
        T swap = items.get(i);
        items.set(i, items.get(j));
        items.set(j, swap);
    }
}