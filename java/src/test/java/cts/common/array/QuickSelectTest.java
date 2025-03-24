package cts.common.array;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class QuickSelectTest {

    @Test
    public void testSelectWithNaturalOrder() {
        Integer[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        int k = 5;
        Integer result = QuickSelect.select(arr, k);
        assertEquals(Integer.valueOf(4), result);
    }

    @Test
    public void testSelectWithCustomComparator() {
        String[] arr = {"apple", "bananas", "cherry", "date", "elderberry"};
        int k = 2;
        Comparator<String> lengthComparator = Comparator.comparingInt(String::length);
        String result = QuickSelect.select(arr, k, lengthComparator);
        assertEquals("cherry", result);
    }

    @Test
    public void testSelectWithInvalidK() {
        Integer[] arr = {1, 2, 3, 4, 5};
        int k = 10; // Out of bounds
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(arr, k));
    }

    @Test
    public void testSelectWithSingleElementArray() {
        Integer[] arr = {42};
        int k = 0;
        Integer result = QuickSelect.select(arr, k);
        assertEquals(Integer.valueOf(42), result);
    }

    @Test
    public void testSelectWithDuplicateElements() {
        Integer[] arr = {5, 5, 5, 5, 5};
        int k = 2;
        Integer result = QuickSelect.select(arr, k);
        assertEquals(Integer.valueOf(5), result);
    }

    @Test
    public void testSelectWithSortedArray() {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int k = 3;
        Integer result = QuickSelect.select(arr, k);
        assertEquals(Integer.valueOf(4), result);
    }

    @Test
    public void testSelectWithReverseSortedArray() {
        Integer[] arr = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int k = 3;
        Integer result = QuickSelect.select(arr, k);
        assertEquals(Integer.valueOf(4), result);
    }
}