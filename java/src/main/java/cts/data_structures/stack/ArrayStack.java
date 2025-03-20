package cts.data_structures.stack;

import cts.data_structures.ResizingArray;

public class ArrayStack<T> implements Stack<T> {
    private final ResizingArray<T> array;

    public ArrayStack() {
        array = new ResizingArray<>();
    }

    @Override
    public void push(T item) {
        array.addLast(item);
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return array.removeLast();
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return array.get(array.size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public int size() {
        return array.size();
    }
}
