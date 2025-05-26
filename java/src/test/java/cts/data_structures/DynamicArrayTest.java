package cts.data_structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {

    private DynamicArray<Integer> array;

    @BeforeEach
    void setUp() {
        array = new DynamicArray<>();
    }

    @Test
    void testIsEmpty() {
        assertTrue(array.isEmpty());
        array.addFirst(1);
        assertFalse(array.isEmpty());
    }

    @Test
    void testSize() {
        assertEquals(0, array.size());
        array.addFirst(1);
        assertEquals(1, array.size());
        array.addLast(2);
        assertEquals(2, array.size());
    }

    @Test
    void testAddFirst() {
        array.addFirst(1);
        assertEquals(1, array.get(0));
        array.addFirst(0);
        assertEquals(0, array.get(0));
        assertEquals(1, array.get(1));
    }

    @Test
    void testAddLast() {
        array.addLast(1);
        assertEquals(1, array.get(0));
        array.addLast(2);
        assertEquals(1, array.get(0));
        assertEquals(2, array.get(1));
    }

    @Test
    void testRemoveFirst() {
        array.addFirst(1);
        array.addLast(2);
        assertEquals(1, array.removeFirst());
        assertEquals(2, array.removeFirst());
        assertTrue(array.isEmpty());
    }

    @Test
    void testRemoveLast() {
        array.addFirst(1);
        array.addLast(2);
        assertEquals(2, array.removeLast());
        assertEquals(1, array.removeLast());
        assertTrue(array.isEmpty());
    }

    @Test
    void testGet() {
        array.addFirst(1);
        array.addLast(2);
        array.addLast(3);
        assertEquals(1, array.get(0));
        assertEquals(2, array.get(1));
        assertEquals(3, array.get(2));
    }

    @Test
    void testSet() {
        array.addFirst(1);
        array.addLast(2);
        array.set(1, 5);
        assertEquals(1, array.get(0));
        assertEquals(5, array.get(1));
    }

    @Test
    void testInsert() {
        array.addFirst(1);
        array.addLast(2);
        array.insert(1, 5);
        array.insert(0, 6);
        array.insert(4, 10);
        assertEquals(6, array.get(0));
        assertEquals(5, array.get(2));
        assertEquals(10, array.get(4));
    }

    @Test
    void testRemove() {
        array.addFirst(1);
        array.addLast(2);
        array.addLast(3);
        array.addLast(4);
        assertEquals(1, array.remove(0));
        assertEquals(2, array.get(0));

        assertEquals(4, array.remove(2));
        assertEquals(3, array.get(1));
    }

    @Test
    void testResize() {
        // Add elements to trigger resizing
        for (int i = 0; i < 10; i++) {
            array.addLast(i);
        }
        assertEquals(10, array.size());
        // Remove elements to trigger downsizing
        for (int i = 0; i < 7; i++) {
            array.removeLast();
        }
        assertEquals(3, array.size());
    }

    @Test
    void testCapacity() {
        array = new DynamicArray<>(1);
        array.addLast(1);
        assertEquals(1, array.capacity());

        array.addLast(2);
        assertEquals(2, array.capacity());

        array.addFirst(3);
        array.addFirst(4);
        assertEquals(4, array.capacity());

        array.addLast(5);
        assertEquals(8, array.capacity());

        array.removeFirst();
        array.removeFirst();
        assertEquals(8, array.capacity());

        array.removeFirst();
        assertEquals(4, array.capacity());
    }

    @Test
    void testIterator() {
        array.addFirst(1);
        array.addLast(2);
        array.addLast(3);

        int[] expected = {1, 2, 3};
        int index = 0;
        for (Integer item : array) {
            assertEquals(expected[index], item);
            index++;
        }
    }

    @Test
    void testRemoveFirstOnEmptyArray() {
        assertThrows(IllegalStateException.class, () -> array.removeFirst());
    }

    @Test
    void testRemoveLastOnEmptyArray() {
        assertThrows(IllegalStateException.class, () -> array.removeLast());
    }

    @Test
    void testGetOutOfBounds() {
        array.addFirst(1);
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(1));
    }

    @Test
    void testSetOutOfBounds() {
        array.addFirst(1);
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(1, 2));
    }
}