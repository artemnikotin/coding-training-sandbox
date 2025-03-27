package cts.data_structures.queue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.Iterator;

class PriorityQueueTest {
    private PriorityQueue<Integer> maxHeap;
    private PriorityQueue<Integer> minHeap;

    @BeforeEach
    void setUp() {
        // Max heap (higher numbers have higher priority)
        maxHeap = new PriorityQueue<Integer>(Comparator.naturalOrder());

        // Min heap (lower numbers have higher priority)
        minHeap = new PriorityQueue<Integer>(Comparator.reverseOrder());
    }

    @Test
    void testEmptyQueue() {
        assertTrue(maxHeap.isEmpty());
        assertEquals(0, maxHeap.size());
        assertThrows(IllegalStateException.class, () -> maxHeap.dequeue());
        assertThrows(IllegalStateException.class, () -> maxHeap.peek());
    }

    @Test
    void testEnqueueDequeueMaxHeap() {
        maxHeap.enqueue(5);
        maxHeap.enqueue(3);
        maxHeap.enqueue(7);
        maxHeap.enqueue(1);

        assertEquals(4, maxHeap.size());

        assertEquals(7, maxHeap.dequeue());
        assertEquals(5, maxHeap.dequeue());
        assertEquals(3, maxHeap.dequeue());
        assertEquals(1, maxHeap.dequeue());

        assertTrue(maxHeap.isEmpty());
    }

    @Test
    void testEnqueueDequeueMinHeap() {
        minHeap.enqueue(5);
        minHeap.enqueue(3);
        minHeap.enqueue(7);
        minHeap.enqueue(1);

        assertEquals(4, minHeap.size());

        assertEquals(1, minHeap.dequeue());
        assertEquals(3, minHeap.dequeue());
        assertEquals(5, minHeap.dequeue());
        assertEquals(7, minHeap.dequeue());
    }

    @Test
    void testPeek() {
        maxHeap.enqueue(10);
        maxHeap.enqueue(20);

        assertEquals(20, maxHeap.peek());
        assertEquals(2, maxHeap.size()); // peek shouldn't remove element

        minHeap.enqueue(10);
        minHeap.enqueue(20);

        assertEquals(10, minHeap.peek());
        assertEquals(2, minHeap.size());
    }

    @Test
    void testRemoveByIndex() {
        maxHeap.enqueue(10);
        maxHeap.enqueue(30);
        maxHeap.enqueue(20);
        maxHeap.enqueue(40);

        // Remove middle element
        maxHeap.remove(1);
        assertEquals(40, maxHeap.peek());
        assertEquals(3, maxHeap.size());

        // Remove last element
        maxHeap.remove(2);
        assertEquals(40, maxHeap.peek());
        assertEquals(2, maxHeap.size());

        // Remove first element
        assertEquals(40, maxHeap.remove(0));
        assertEquals(1, maxHeap.size());
    }

    @Test
    void testRemoveInvalidIndex() {
        maxHeap.enqueue(1);
        assertThrows(IndexOutOfBoundsException.class, () -> maxHeap.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> maxHeap.remove(2));
    }

    @Test
    void testOrderChanged() {
        FlexibleComparator<Integer> comparator = new FlexibleComparator<>();
        PriorityQueue<Integer> heap = new PriorityQueue<>(comparator);

        heap.enqueue(0);
        heap.enqueue(1);
        heap.enqueue(2);
        heap.enqueue(3);
        heap.enqueue(4);

        assertEquals(4, heap.peek());

        Integer item = heap.get(1);
        comparator.setTheHighestPriority(item);

        heap.doOnOrderChanged(1);

        assertEquals(item, heap.peek());
    }

    @Test
    void testHeapify() {
        FlexibleComparator<Integer> comparator = new FlexibleComparator<>();
        PriorityQueue<Integer> heap = new PriorityQueue<>(comparator);
        // Test with max heap
        heap.enqueue(1);
        heap.enqueue(2);
        heap.enqueue(3);
        heap.enqueue(4);

        assertEquals(4, heap.peek());

        comparator.switchNatural();
        heap.doOnOrderChanged(); // Should reorganize the heap

        assertEquals(1, heap.peek());
    }

    @Test
    void testIterator() {
        maxHeap.enqueue(1);
        maxHeap.enqueue(2);
        maxHeap.enqueue(3);

        Iterator<Integer> it = maxHeap.iterator();
        assertTrue(it.hasNext());

        // Note: Iterator order is not guaranteed to be in priority order
        int count = 0;
        while (it.hasNext()) {
            it.next();
            count++;
        }
        assertEquals(3, count);
    }

    @Test
    void testMixedOperations() {
        // Test a sequence of mixed operations
        maxHeap.enqueue(5);
        maxHeap.enqueue(3);
        assertEquals(5, maxHeap.dequeue());

        maxHeap.enqueue(7);
        maxHeap.enqueue(1);
        assertEquals(7, maxHeap.peek());

        maxHeap.enqueue(9);
        assertEquals(9, maxHeap.dequeue());
        assertEquals(7, maxHeap.dequeue());

        maxHeap.enqueue(4);
        assertEquals(4, maxHeap.dequeue());
        assertEquals(3, maxHeap.dequeue());
        assertEquals(1, maxHeap.dequeue());

        assertTrue(maxHeap.isEmpty());
    }

    private static class FlexibleComparator<T extends Comparable<T>> implements Comparator<T> {
        private final Comparator<T> naturalOrder = Comparator.naturalOrder();
        private final Comparator<T> reverseOrder = Comparator.reverseOrder();
        private boolean isNatural = true;
        private T highest = null;

        @Override
        public int compare(T o1, T o2) {
            if (highest != null) {
                if (o1 == highest) {
                    return 1;
                } else if (o2 == highest) {
                    return -1;
                }
            }
            return isNatural ? naturalOrder.compare(o1, o2) : reverseOrder.compare(o1, o2);
        }

        public void switchNatural() {
            isNatural = !isNatural;
        }

        public void setTheHighestPriority(T item) {
            highest = item;
        }
    }
}