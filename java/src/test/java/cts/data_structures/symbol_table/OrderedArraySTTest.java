package cts.data_structures.symbol_table;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;

class OrderedArraySTTest {
    private OrderedArrayST<Integer, String> st;

    @BeforeEach
    void setUp() {
        st = new OrderedArrayST<>();
    }

    @Test
    void testEmptySymbolTable() {
        assertTrue(st.isEmpty());
        assertEquals(0, st.size());
        assertNull(st.get(1));
        assertNull(st.min());
        assertNull(st.max());
    }

    @Test
    void testPutAndGet() {
        st.put(3, "three");
        st.put(1, "one");
        st.put(2, "two");

        assertEquals("one", st.get(1));
        assertEquals("two", st.get(2));
        assertEquals("three", st.get(3));
        assertNull(st.get(4));
    }

    @Test
    void testPutDuplicateKey() {
        st.put(1, "first");
        st.put(1, "second");
        assertEquals("second", st.get(1));
        assertEquals(1, st.size());
    }

    @Test
    void testDelete() {
        st.put(1, "one");
        st.put(2, "two");
        st.put(3, "three");

        st.delete(2);
        assertNull(st.get(2));
        assertEquals(2, st.size());
        assertEquals("one", st.get(1));
        assertEquals("three", st.get(3));

        st.delete(1);
        st.delete(3);
        assertTrue(st.isEmpty());
    }

    @Test
    void testDeleteNonExistentKey() {
        st.put(1, "one");
        st.delete(2); // Should do nothing
        assertEquals(1, st.size());
    }

    @Test
    void testContains() {
        assertFalse(st.contains(1));
        st.put(1, "one");
        assertTrue(st.contains(1));
        assertFalse(st.contains(2));
    }

    @Test
    void testMin() {
        assertNull(st.min());
        st.put(3, "three");
        assertEquals(3, st.min());
        st.put(1, "one");
        assertEquals(1, st.min());
    }

    @Test
    void testMax() {
        assertNull(st.max());
        st.put(1, "one");
        assertEquals(1, st.max());
        st.put(3, "three");
        assertEquals(3, st.max());
    }

    @Test
    void testFloor() {
        st.put(1, "one");
        st.put(3, "three");
        st.put(5, "five");

        assertEquals(1, st.floor(1));
        assertEquals(1, st.floor(2));
        assertEquals(3, st.floor(3));
        assertEquals(3, st.floor(4));
        assertEquals(5, st.floor(5));
        assertEquals(5, st.floor(6));
        assertNull(st.floor(0));
    }

    @Test
    void testCeiling() {
        st.put(1, "one");
        st.put(3, "three");
        st.put(5, "five");

        assertEquals(1, st.ceiling(0));
        assertEquals(1, st.ceiling(1));
        assertEquals(3, st.ceiling(2));
        assertEquals(3, st.ceiling(3));
        assertEquals(5, st.ceiling(4));
        assertEquals(5, st.ceiling(5));
        assertNull(st.ceiling(6));
    }

    @Test
    void testRank() {
        assertEquals(0, st.rank(1));

        st.put(1, "one");
        st.put(3, "three");
        st.put(5, "five");

        assertEquals(0, st.rank(0));
        assertEquals(0, st.rank(1));
        assertEquals(1, st.rank(2));
        assertEquals(1, st.rank(3));
        assertEquals(2, st.rank(4));
        assertEquals(2, st.rank(5));
        assertEquals(3, st.rank(6));
    }

    @Test
    void testSelect() {
        st.put(1, "one");
        st.put(3, "three");
        st.put(5, "five");

        assertEquals(1, st.select(0));
        assertEquals(3, st.select(1));
        assertEquals(5, st.select(2));

        assertThrows(IllegalArgumentException.class, () -> st.select(-1));
        assertThrows(IllegalArgumentException.class, () -> st.select(3));
    }

    @Test
    void testDeleteMin() {
        st.put(2, "two");
        st.put(1, "one");
        st.put(3, "three");

        st.deleteMin();
        assertEquals(2, st.size());
        assertEquals(2, st.min());
        assertNull(st.get(1));
    }

    @Test
    void testDeleteMinEmpty() {
        assertThrows(NoSuchElementException.class, () -> st.deleteMin());
    }

    @Test
    void testDeleteMax() {
        st.put(2, "two");
        st.put(1, "one");
        st.put(3, "three");

        st.deleteMax();
        assertEquals(2, st.size());
        assertEquals(2, st.max());
        assertNull(st.get(3));
    }

    @Test
    void testDeleteMaxEmpty() {
        assertThrows(NoSuchElementException.class, () -> st.deleteMax());
    }

    @Test
    void testSizeRange() {
        st.put(1, "one");
        st.put(2, "two");
        st.put(3, "three");
        st.put(4, "four");
        st.put(5, "five");

        assertEquals(5, st.size(1, 5));
        assertEquals(3, st.size(2, 4));
        assertEquals(0, st.size(6, 10));
        assertEquals(0, st.size(5, 1));
    }

    @Test
    void testKeysRange() {
        st.put(1, "one");
        st.put(2, "two");
        st.put(3, "three");
        st.put(4, "four");
        st.put(5, "five");

        assertIterableEquals(List.of(2, 3, 4), st.keys(2, 4));
        assertIterableEquals(List.of(1, 2, 3, 4, 5), st.keys(1, 5));
        assertIterableEquals(List.of(), st.keys(6, 10));
        assertIterableEquals(List.of(), st.keys(5, 1));
    }

    @Test
    void testKeys() {
        st.put(3, "three");
        st.put(1, "one");
        st.put(2, "two");

        assertIterableEquals(List.of(1, 2, 3), st.keys());
    }

    @Test
    void testNullKeyOperations() {
        assertThrows(NullPointerException.class, () -> st.put(null, "value"));
        assertThrows(NullPointerException.class, () -> st.get(null));
        assertThrows(NullPointerException.class, () -> st.delete(null));
        assertThrows(NullPointerException.class, () -> st.contains(null));
        assertThrows(NullPointerException.class, () -> st.floor(null));
        assertThrows(NullPointerException.class, () -> st.ceiling(null));
        assertThrows(NullPointerException.class, () -> st.rank(null));
        assertThrows(NullPointerException.class, () -> st.size(null, 1));
        assertThrows(NullPointerException.class, () -> st.keys(null, 1));
    }
}