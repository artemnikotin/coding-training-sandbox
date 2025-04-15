package cts.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArray<T> implements Iterable<T> {
    private static final int DEFAULT_INITIAL_CAPACITY = 8;

    private T[] items;  // Array to store elements
    private int size = 0;  // Number of elements in the array
    private int first = 0; // Index of the first element
    private int last = -1;  // Index of the last element

    // Constructor to initialize the array with a default capacity
    public ResizingArray() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    // Constructor to initialize the array with passed capacity
    @SuppressWarnings("unchecked")
    public ResizingArray(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }
        items = (T[]) new Object[initialCapacity];
    }

    // Check if the array is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Return the number of elements in the array
    public int size() {
        return size;
    }

    // Add an item to the beginning of the array
    public void addFirst(T item) {
        // Resize the array if it's full
        if (size == items.length) {
            resize(2 * items.length);
        }
        first--;
        if (first < 0) {
            first = items.length - 1;
        }
        items[first] = item;
        size++;
    }

    // Add an item to the end of the array
    public void addLast(T item) {
        // Resize the array if it's full
        if (size == items.length) {
            resize(2 * items.length);
        }
        last++;
        if (last >= items.length) {
            last = 0;
        }
        items[last] = item;
        size++;
    }

    // Remove and return the first item in the array
    public T removeFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Array is empty");
        }

        T item = items[first];
        items[first] = null;
        first++;
        if (first >= items.length) {
            first = 0;
        }
        size--;
        // Resize the array if it's only 25% full
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    // Remove and return the last item in the array
    public T removeLast() {
        if (isEmpty()) {
            throw new IllegalStateException("Array is empty");
        }

        T item = items[last];
        items[last] = null;
        last--;
        if (last < 0) {
            last = items.length - 1;
        }
        size--;
        // Resize the array if it's only 25% full
        if (size > DEFAULT_INITIAL_CAPACITY && size == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    // Get the item at the specified index
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
        return items[index(index)];
    }

    // Set the item at the specified index
    public void set(int index, T item) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
        items[index(index)] = item;
    }

    // Insert the item at the specified index
    public void insert(int index, T item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
        addLast(null);
        for (int i = size() - 1; i > index; i--) {
            items[index(i)] = items[index(i - 1)];
        }
        items[index(index)] = item;
    }

    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
        T item = get(index);
        for (; index < size() - 1; index++) {
            items[index(index)] = items[index(index + 1)];
        }
        removeLast();
        return item;
    }

    // Return an iterator for the array
    public Iterator<T> iterator() {
        return new CyclicArrayIterator();
    }

    // Return the current capacity of the array
    public int capacity() {
        return items.length;
    }

    // Resize the array to the specified capacity
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        T[] copy = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = items[index(i)];
        }
        items = copy;
        first = 0;
        last = size - 1;
    }

    // Real array index
    private int index(int i) {
        return (first + i) % items.length;
    }

    // Iterator implementation for the array
    private class CyclicArrayIterator implements Iterator<T> {
        private int i = 0;

        public boolean hasNext() {
            return i < size;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported");
        }

        public T next() {
            if (i >= size) {
                throw new NoSuchElementException("Out of bound");
            }
            T item = items[index(i)];
            i++;
            return item;
        }
    }
}