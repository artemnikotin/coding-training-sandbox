package cts.coursera;
import java.util.NoSuchElementException;

/**
 * Coursera | Algorithms, Part I | Week 02
 * Create a data structure that efficiently supports the stack operations (push and pop) and also a return-the-maximum
 * operation. Assume the elements are real numbers so that you can compare them.
 */
public class WithMaxStack {
    private int[] items;
    private int[] maximums;
    private int size = 0;
    private int max = Integer.MIN_VALUE;

    public WithMaxStack() {
        items = new int[1];
        maximums = new int[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void push(int item) {
        if (size == items.length) {
            resize(2 * items.length);
        }
        int i = size++;
        max = Math.max(max, item);
        items[i] = item;
        maximums[i] = max;
    }

    public int pop() {
        if (size == 0) {
            throw new NoSuchElementException("Stack is empty");
        }

        int i = --size;
        int item = items[i];
        max = i > 0 ? maximums[i - 1] : Integer.MIN_VALUE;
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    public int getMax() {
        return max;
    }

    private void resize(int capacity) {
        int[] itemsCopy = new int[capacity];
        int[] maxCopy = new int[capacity];
        for (int i = 0; i < size; i++) {
            itemsCopy[i] = items[i];
            maxCopy[i] = maximums[i];
        }
        items = itemsCopy;
        maximums = maxCopy;
    }

    public static void main(String[] args) {
        WithMaxStack s = new WithMaxStack();
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(10);
        s.push(7);
        System.out.println(s.getMax()); // Output: 10
        System.out.println(s.pop()); // Output: 7
        System.out.println(s.getMax()); // Output: 10
        System.out.println(s.pop()); // Output: 10
        System.out.println(s.getMax()); // Output: 3
        System.out.println(s.isEmpty()); // Output: false
    }
}
