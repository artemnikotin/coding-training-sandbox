package cts.data_structures.queue;

public interface Queue<T> {
    void enqueue(T item);
    T peek();
    T dequeue();
    boolean isEmpty();
    int size();
}
