package cts.data_structures.queue;

import cts.data_structures.DynamicArray;

import java.util.Iterator;

public class ArrayQueue<T> implements Queue<T> {
    private final DynamicArray<T> array;

    public ArrayQueue() {
        array = new DynamicArray<>();
    }

    @Override
    public void enqueue(T item) {
        array.addLast(item);
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return array.removeFirst();
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return array.get(0);
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public Iterator<T> iterator() {
        return array.iterator();
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append('[');
        for (T s : array) {
            builder.append(s);
            builder.append(",");
        }
        if (builder.length() > 1) {
            builder.setLength(builder.length() - 1);
        }
        builder.append(']');
        return builder.toString();
    }
}