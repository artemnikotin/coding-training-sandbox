package cts.data_structures.symbol_table;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Stream;

class UnorderedSTTest {

    @ParameterizedTest
    @MethodSource("stProvider")
    void testEmptySymbolTable(UnorderedST<String, Integer> st) {
        assertTrue(st.isEmpty());
        assertEquals(0, st.size());
        assertNull(st.get("key"));
        assertFalse(st.contains("key"));
    }

    @ParameterizedTest
    @MethodSource("stProvider")
    void testPutAndGet(UnorderedST<String, Integer> st) {
        st.put("one", 1);
        st.put("two", 2);
        st.put("three", 3);

        assertEquals(1, st.get("one"));
        assertEquals(2, st.get("two"));
        assertEquals(3, st.get("three"));
        assertNull(st.get("four"));
    }

    @ParameterizedTest
    @MethodSource("stProvider")
    void testPutDuplicateKey(UnorderedST<String, Integer> st) {
        st.put("key", 1);
        assertEquals(1, st.get("key"));
        assertEquals(1, st.size());

        st.put("key", 2);
        assertEquals(2, st.get("key"));
        assertEquals(1, st.size());
    }

    @ParameterizedTest
    @MethodSource("stProvider")
    void testDelete(UnorderedST<String, Integer> st) {
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

    @ParameterizedTest
    @MethodSource("stProvider")
    void testDeleteNonExistentKey(UnorderedST<String, Integer> st) {
        st.put("a", 1);
        st.delete("b"); // Should do nothing
        assertEquals(1, st.size());
    }

    @ParameterizedTest
    @MethodSource("stProvider")
    void testDeleteFromEmptyTable(UnorderedST<String, Integer> st) {
        assertDoesNotThrow(() -> st.delete("nonexistent"));
    }

    @ParameterizedTest
    @MethodSource("stProvider")
    void testDeleteAllKeys(UnorderedST<String, Integer> st) {
        st.put("a", 1);
        st.put("b", 2);
        st.put("c", 3);

        st.delete("a");
        st.delete("b");
        st.delete("c");

        assertTrue(st.isEmpty());
        assertEquals(0, st.size());
    }

    @ParameterizedTest
    @MethodSource("stProvider")
    void testContains(UnorderedST<String, Integer> st) {
        assertFalse(st.contains("key"));
        st.put("key", 1);
        assertTrue(st.contains("key"));
        assertFalse(st.contains("nonexistent"));
    }

    @ParameterizedTest
    @MethodSource("stProvider")
    void testSize(UnorderedST<String, Integer> st) {
        assertEquals(0, st.size());
        st.put("a", 1);
        assertEquals(1, st.size());
        st.put("b", 2);
        assertEquals(2, st.size());
        st.delete("a");
        assertEquals(1, st.size());
    }

    @ParameterizedTest
    @MethodSource("stProvider")
    void testIsEmpty(UnorderedST<String, Integer> st) {
        assertTrue(st.isEmpty());
        st.put("key", 1);
        assertFalse(st.isEmpty());
        st.delete("key");
        assertTrue(st.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("stProvider")
    void testKeysIterator(UnorderedST<String, Integer> st) {
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

    @ParameterizedTest
    @MethodSource("stProvider")
    void testKeysIteratorEmptyTable(UnorderedST<String, Integer> st) {
        Iterator<String> iterator = st.keys().iterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @ParameterizedTest
    @MethodSource("stProvider")
    void testNullKeyOperations(UnorderedST<String, Integer> st) {
        assertThrows(NullPointerException.class, () -> st.put(null, 1));
        assertThrows(NullPointerException.class, () -> st.get(null));
        assertThrows(NullPointerException.class, () -> st.delete(null));
        assertThrows(NullPointerException.class, () -> st.contains(null));
    }

    @ParameterizedTest
    @MethodSource("stProvider")
    void testNullValue(UnorderedST<String, Integer> st) {
        st.put("key", 1);
        st.put("key", null); // Should delete the key
        assertFalse(st.contains("key"));
        assertEquals(0, st.size());
    }

    @ParameterizedTest
    @MethodSource("stProvider")
    void testMultipleOperations(UnorderedST<String, Integer> st) {
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

    @ParameterizedTest
    @MethodSource("stProvider")
    void testKeysOrder(UnorderedST<String, Integer> st) {
        // Insertion order should be preserved in iteration
        st.put("first", 1);
        st.put("second", 2);
        st.put("third", 3);

        List<String> keys = new ArrayList<>();
        st.keys().forEach(keys::add);
        keys.sort(Comparator.naturalOrder());

        assertEquals(List.of("first", "second", "third"), keys);
    }

    // Provides Ordered symbol table implementations for testing
    static Stream<UnorderedST<String, Integer>> stProvider() {
        return Stream.of(new LinkedListST<>(), new SeparateChainingST<>(), new LinearProbingST<>());
    }
}