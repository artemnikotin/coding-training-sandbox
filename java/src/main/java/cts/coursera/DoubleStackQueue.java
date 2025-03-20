package cts.coursera;
import java.util.Stack;

/**
 * Coursera | Algorithms, Part I | Week 02
 * Implement a queue with two stacks so that each queue operations takes a constant amortized number of stack operations.
 */
public class DoubleStackQueue<Item> {
    private final Stack<Item> in = new Stack<>();
    private final Stack<Item> out = new Stack<>();

    public boolean isEmpty() {
        return in.isEmpty() && out.isEmpty();
    }

    public int size() {
        return in.size() + out.size();
    }

    public void enqueue(Item item) {
        in.push(item);
    }

    public Item dequeue() {
        if (out.isEmpty()) {
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }
        return out.pop();
    }

    public static void main(String[] args) {
        DoubleStackQueue<Integer> queue = new DoubleStackQueue<>();

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        System.out.println(queue.dequeue()); // Output: 1
        System.out.println(queue.dequeue()); // Output: 2
        System.out.println(queue.size()); // Output: 1
        System.out.println(queue.isEmpty()); // Output: false
    }
}
