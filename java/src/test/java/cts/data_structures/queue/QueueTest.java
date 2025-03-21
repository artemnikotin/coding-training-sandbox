package cts.data_structures.queue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class QueueTest {

    // Test enqueue and peek
    @ParameterizedTest
    @MethodSource("queueProvider")
    void testEnqueueAndPeek(Queue<Integer> queue) {
        queue.enqueue(10);
        assertEquals(10, queue.peek());
        assertEquals(1, queue.size());
    }

    // Test enqueue and dequeue
    @ParameterizedTest
    @MethodSource("queueProvider")
    void testEnqueueAndDequeue(Queue<Integer> queue) {
        queue.enqueue(10);
        queue.enqueue(20);
        assertEquals(10, queue.dequeue());
        assertEquals(20, queue.peek());
        assertEquals(1, queue.size());
    }

    // Test isEmpty
    @ParameterizedTest
    @MethodSource("queueProvider")
    void testIsEmpty(Queue<Integer> queue) {
        assertTrue(queue.isEmpty());
        queue.enqueue(10);
        assertFalse(queue.isEmpty());
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    // Test the queue iterator
    @ParameterizedTest
    @MethodSource("queueProvider")
    void testStackIterator(Queue<Integer> queue) {
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        Iterator<Integer> iterator = queue.iterator();

        // Verify the elements are returned in LIFO order
        assertTrue(iterator.hasNext());
        assertEquals(10, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(20, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(30, iterator.next());
        assertFalse(iterator.hasNext());

        // Verify NoSuchElementException is thrown when there are no more elements
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    // Test dequeue on empty queue
    @ParameterizedTest
    @MethodSource("queueProvider")
    void testDequeueOnEmptyQueue(Queue<Integer> queue) {
        assertThrows(IllegalStateException.class, queue::dequeue);
    }

    // Test peek on empty queue
    @ParameterizedTest
    @MethodSource("queueProvider")
    void testPeekOnEmptyQueue(Queue<Integer> queue) {
        assertThrows(IllegalStateException.class, queue::peek);
    }

    // Provides Queue implementations for testing
    static Stream<Queue<Integer>> queueProvider() {
        return Stream.of(new ArrayQueue<>(), new LinkedQueue<>());
    }
}