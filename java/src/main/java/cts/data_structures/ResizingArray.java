package cts.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArray<Item> implements Iterable<Item> {
    private Item[] items;  // Array to store elements
    private int size = 0;  // Number of elements in the array
    private int first = 0; // Index of the first element
    private int last = 0;  // Index of the last element

    // Constructor to initialize the array with a default capacity of 1
    public ResizingArray() {
        items = (Item[]) new Object[1];
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
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item must not be null");
        }

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
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item must not be null");
        }

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
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Array is empty");
        }

        Item item = items[first];
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
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Array is empty");
        }

        Item item = items[last];
        items[last] = null;
        last--;
        if (last < 0) {
            last = items.length - 1;
        }
        size--;
        // Resize the array if it's only 25% full
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    // Get the item at the specified index
    public Item get(int index) {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException("Out of bound");
        }
        return items[(first + index) % items.length];
    }

    // Set the item at the specified index
    public void set(int index, Item item) {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException("Out of bound");
        }
        items[(first + index) % items.length] = item;
    }

    // Return an iterator for the array
    public Iterator<Item> iterator() {
        return new CyclicArrayIterator();
    }

    // Resize the array to the specified capacity
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = items[(first + i) % items.length];
        }
        items = copy;
        first = 0;
        last = size - 1;
    }

    // Return the current capacity of the array
    public int capacity() {
        return items.length;
    }

    // Iterator implementation for the array
    private class CyclicArrayIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < size;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported");
        }

        public Item next() {
            if (i >= size) {
                throw new NoSuchElementException("Out of bound");
            }
            Item item = items[(first + i) % items.length];
            i++;
            return item;
        }
    }
}