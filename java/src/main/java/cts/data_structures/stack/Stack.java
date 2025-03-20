package cts.data_structures.stack;

public interface Stack<T> {
    void push(T item);

    T pop();

    T peek();

    boolean isEmpty();

    int size();
}
