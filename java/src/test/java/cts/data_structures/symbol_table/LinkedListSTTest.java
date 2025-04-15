package cts.data_structures.symbol_table;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

class LinkedListSTTest {
    private LinkedListST<String, Integer> st;

    @BeforeEach
    void setUp() {
        st = new LinkedListST<>();
    }

    @Test
    void testEmptySymbolTable() {
        assertTrue(st.isEmpty());
        assertEquals(0, st.size());
        assertNull(st.get("key"));
        assertFalse(st.contains("key"));
    }

    @Test
    void testPutAndGet() {
        st.put("one", 1);
        st.put("two", 2);
        st.put("three", 3);

        assertEquals(1, st.get("one"));
        assertEquals(2, st.get("two"));
        assertEquals(3, st.get("three"));
        assertNull(st.get("four"));
    }

    @Test
    void testPutDuplicateKey() {
        st.put("key", 1);
        assertEquals(1, st.get("key"));
        assertEquals(1, st.size());

        st.put("key", 2);
        assertEquals(2, st.get("key"));
        assertEquals(1, st.size());
    }

    @Test
    void testDelete() {
        st.put("a", 1);
        st.put("b", 2);
        st.put("c", 3);

        st.delete("b");
        assertNull(st.get("b"));
        assertEquals(2, st.size());
        assertTrue(st.contains("a"));
        assertTrue(st.contains("c"));

        st.delete("a");
        st.delete("c");
        assertTrue(st.isEmpty());
    }

    @Test
    void testDeleteNonExistentKey() {
        st.put("a", 1);
        st.delete("b"); // Should do nothing
        assertEquals(1, st.size());
    }

    @Test
    void testDeleteFromEmptyTable() {
        assertDoesNotThrow(() -> st.delete("nonexistent"));
    }

    @Test
    void testDeleteAllKeys() {
        st.put("a", 1);
        st.put("b", 2);
        st.put("c", 3);

        st.delete("a");
        st.delete("b");
        st.delete("c");

        assertTrue(st.isEmpty());
        assertEquals(0, st.size());
    }

    @Test
    void testContains() {
        assertFalse(st.contains("key"));
        st.put("key", 1);
        assertTrue(st.contains("key"));
        assertFalse(st.contains("nonexistent"));
    }

    @Test
    void testSize() {
        assertEquals(0, st.size());
        st.put("a", 1);
        assertEquals(1, st.size());
        st.put("b", 2);
        assertEquals(2, st.size());
        st.delete("a");
        assertEquals(1, st.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(st.isEmpty());
        st.put("key", 1);
        assertFalse(st.isEmpty());
        st.delete("key");
        assertTrue(st.isEmpty());
    }

    @Test
    void testKeysIterator() {
        st.put("c", 3);
        st.put("a", 1);
        st.put("b", 2);

        List<String> keys = new ArrayList<>();
        st.keys().forEach(keys::add);

        assertEquals(3, keys.size());
        assertTrue(keys.contains("a"));
        assertTrue(keys.contains("b"));
        assertTrue(keys.contains("c"));
    }

    @Test
    void testKeysIteratorEmptyTable() {
        Iterator<String> iterator = st.keys().iterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testNullKeyOperations() {
        assertThrows(NullPointerException.class, () -> st.put(null, 1));
        assertThrows(NullPointerException.class, () -> st.get(null));
        assertThrows(NullPointerException.class, () -> st.delete(null));
        assertThrows(NullPointerException.class, () -> st.contains(null));
    }

    @Test
    void testNullValue() {
        st.put("key", 1);
        st.put("key", null); // Should delete the key
        assertFalse(st.contains("key"));
        assertEquals(0, st.size());
    }

    @Test
    void testMultipleOperations() {
        assertTrue(st.isEmpty());

        st.put("a", 1);
        st.put("b", 2);
        st.put("c", 3);
        assertEquals(3, st.size());

        assertEquals(2, st.get("b"));
        st.delete("b");
        assertEquals(2, st.size());
        assertNull(st.get("b"));

        st.put("d", 4);
        assertEquals(3, st.size());
        assertEquals(4, st.get("d"));

        st.delete("a");
        st.delete("c");
        st.delete("d");
        assertTrue(st.isEmpty());
    }

    @Test
    void testKeysOrder() {
        // Insertion order should be preserved in iteration
        st.put("first", 1);
        st.put("second", 2);
        st.put("third", 3);

        List<String> keys = new ArrayList<>();
        st.keys().forEach(keys::add);

        assertEquals(List.of("third", "second", "first"), keys);
    }
}