package cts.data_structures.queue;

public interface Queue<T> extends Iterable<T> {
    void enqueue(T item);

    T peek();

    T dequeue();

    boolean isEmpty();

    int size();
}
