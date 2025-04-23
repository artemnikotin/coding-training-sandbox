package cts.coursera;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NoExtraMemoryRedBlackBSTTest {

    private NoExtraMemoryRedBlackBST<Integer, String> tree;

    @BeforeEach
    public void setup() {
        tree = new NoExtraMemoryRedBlackBST<>();
    }

    @Test
    public void testPutAndGet() {
        tree.put(5, "five");
        tree.put(3, "three");
        tree.put(7, "seven");

        assertEquals("five", tree.get(5));
        assertEquals("three", tree.get(3));
        assertEquals("seven", tree.get(7));
        assertNull(tree.get(100)); // not inserted
    }

    @Test
    public void testSizeAndIsEmpty() {
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());

        tree.put(1, "one");
        assertFalse(tree.isEmpty());
        assertEquals(1, tree.size());

        tree.put(2, "two");
        assertEquals(2, tree.size());
    }

    @Test
    public void testOverwriteValue() {
        tree.put(42, "forty-two");
        assertEquals("forty-two", tree.get(42));

        tree.put(42, "new");
        assertEquals("new", tree.get(42));
        assertEquals(1, tree.size());
    }

    @Test
    public void testDelete() {
        tree.put(10, "ten");
        tree.put(5, "five");
        tree.put(15, "fifteen");

        assertEquals(3, tree.size());

        tree.delete(5);
        assertNull(tree.get(5));
        assertEquals(2, tree.size());

        tree.delete(10);
        tree.delete(15);
        assertTrue(tree.isEmpty());
    }

    @Test
    public void testDeleteMin() {
        tree.put(20, "twenty");
        tree.put(10, "ten");
        tree.put(30, "thirty");
        tree.put(5, "five");

        assertEquals("five", tree.get(5));
        tree.deleteMin();
        assertNull(tree.get(5));
        assertEquals(3, tree.size());
    }

    @Test
    public void testStressInsertAndDelete() {
        for (int i = 0; i < 1000; i++) {
            tree.put(i, "val" + i);
        }

        assertEquals(1000, tree.size());

        for (int i = 0; i < 500; i++) {
            tree.delete(i);
        }

        assertEquals(500, tree.size());

        for (int i = 500; i < 1000; i++) {
            assertEquals("val" + i, tree.get(i));
        }
    }
}
