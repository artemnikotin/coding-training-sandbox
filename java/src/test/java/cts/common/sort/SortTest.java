package cts.common.sort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SortTest {

    // Test data
    static Integer[][] testData() {
        return new Integer[][]{
                {1, 2, 3, 4, 5}, // Already sorted
                {5, 4, 3, 2, 1}, // Reverse sorted
                {3, 16, 4, 1, 15, 9, 2, 6, 5, 1, 12, 10, 11, 0, 13, 14, 5}, // Random
                {}, // Empty array
                {1} // Single element
        };
    }

    // Sorting algorithms to test with their names
    static Stream<Arguments> sortingAlgorithms() {
        return Stream.of(
                Arguments.of("BubbleSort", (Consumer<Integer[]>) BubbleSort::sort),
                Arguments.of("SelectionSort", (Consumer<Integer[]>) SelectionSort::sort),
                Arguments.of("InsertionSort", (Consumer<Integer[]>) InsertionSort::sort),
                Arguments.of("ShellSort", (Consumer<Integer[]>) ShellSort::sort),
                Arguments.of("MergeSort", (Consumer<Integer[]>) MergeSort::sort),
                Arguments.of("MergeSortOptimized", (Consumer<Integer[]>) MergeSortOptimized::sort),
                Arguments.of("QuickSort", (Consumer<Integer[]>) QuickSort::sort)
        );
    }

    @Test
    void testIsSorted() {
        assertTrue(Sort.isSorted(new Integer[]{}));
        assertTrue(Sort.isSorted(new Integer[]{1}));
        assertTrue(Sort.isSorted(new Integer[]{1, 2, 3, 4, 5}));
        assertFalse(Sort.isSorted(new Integer[]{1, 3, 2, 4, 5}));
    }

    @Test
    void testLess() {
        assertTrue(Sort.less(1, 2));
        assertFalse(Sort.less(1, 1));
        assertFalse(Sort.less(2, 1));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("sortingAlgorithms")
    void testSort(String algorithmName, Consumer<Integer[]> sortingAlgorithm) {
        for (Integer[] original : testData()) {
            // Create a copy of the original array
            Integer[] toBeSorted = Arrays.copyOf(original, original.length);
            // Sort the array using the provided algorithm
            sortingAlgorithm.accept(toBeSorted);
            // Verify that the array is sorted
            assertTrue(Sort.isSorted(toBeSorted), () -> String.format(
                    "Algorithm: %s, Before: %s, After: %s",
                    algorithmName,
                    Arrays.toString(original),
                    Arrays.toString(toBeSorted)
            ));
        }
    }
}