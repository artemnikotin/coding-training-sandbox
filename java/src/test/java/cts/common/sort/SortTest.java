package cts.common.sort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SortTest {

    private static final DescendingOrderComparator<Integer> descendingOrder = new DescendingOrderComparator<>();

    static Integer[][] testData() {
        return new Integer[][]{
                {1, 2, 3, 4, 5}, // Already sorted
                {5, 4, 3, 2, 1}, // Reverse sorted
                {3, 16, 4, 1, 15, 9, 2, 6, 5, 1, 12, 10, 11, 0, 13, 14, 5}, // Random
                {}, // Empty array
                {1} // Single element
        };
    }

    static Integer[][] rangeTestData() {
        return new Integer[][]{
                {1, 2, 3, 4, 5}, // Already sorted
                {5, 4, 3, 2, 1}, // Reverse sorted
                {3, 16, 4, 1, 15, 9, 2, 6, 5, 1, 12, 10, 11, 0, 13, 14, 5}, // Random
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
                Arguments.of("MergeSort(iterative)", (Consumer<Integer[]>) MergeSort::sortIterative),
                Arguments.of("MergeSort(optimized)", (Consumer<Integer[]>) MergeSortOptimized::sort),
                Arguments.of("QuickSort", (Consumer<Integer[]>) QuickSort::sort)
        );
    }

    // Sorting algorithms with comparator implementation
    static Stream<Arguments> sortingComparatorAlgorithms() {
        return Stream.of(
                Arguments.of("InsertionSort", (BiConsumer<Integer[], Comparator<Integer>>) InsertionSort::sort),
                Arguments.of("MergeSort(optimized)", (BiConsumer<Integer[], Comparator<Integer>>) MergeSortOptimized::sort)
        );
    }

    // Sorting algorithms with range implementation
    static Stream<Arguments> sortingRangeAlgorithms() {
        return Stream.of(
                Arguments.of("InsertionSort", (RangeSort) InsertionSort::sort),
                Arguments.of("MergeSort(optimized)", (RangeSort) MergeSortOptimized::sort)
        );
    }

    @Test
    void testIsSorted() {
        assertTrue(Sort.isSorted(new Integer[]{}));
        assertTrue(Sort.isSorted(new Integer[]{1}));
        assertTrue(Sort.isSorted(new Integer[]{1, 2, 3, 4, 5}));
        assertFalse(Sort.isSorted(new Integer[]{1, 3, 2, 4, 5}));

        // Slice test
        assertTrue(Sort.isSorted(new Integer[]{1, 3, 2, 4, 5}, 2, 4));

        // With comparator test
        assertTrue(Sort.isSorted(new Integer[]{5, 4, 3, 2, 1}, 2, 4, descendingOrder));

        // With comparator slice test
        assertTrue(Sort.isSorted(new Integer[]{1, 4, 3, 2, 5}, 1, 3, descendingOrder));
    }

    @Test
    void testLess() {
        // Natural order
        assertTrue(Sort.less(1, 2));
        assertFalse(Sort.less(1, 1));
        assertFalse(Sort.less(2, 1));

        // Descending order
        assertFalse(Sort.less(descendingOrder, 1, 2));
        assertFalse(Sort.less(descendingOrder, 1, 1));
        assertTrue(Sort.less(descendingOrder, 2, 1));
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

    @ParameterizedTest(name = "{0}")
    @MethodSource("sortingComparatorAlgorithms")
    void testComparatorSort(String algorithmName, BiConsumer<Integer[], Comparator<Integer>> sortingAlgorithm) {
        for (Integer[] original : testData()) {
            // Create a copy of the original array
            Integer[] toBeSorted = Arrays.copyOf(original, original.length);
            // Sort the array using the provided algorithm
            sortingAlgorithm.accept(toBeSorted, descendingOrder);
            // Verify that the array is sorted
            assertTrue(Sort.isSorted(toBeSorted, descendingOrder), () -> String.format(
                    "Algorithm: %s, Before: %s, After: %s",
                    algorithmName,
                    Arrays.toString(original),
                    Arrays.toString(toBeSorted)
            ));
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("sortingRangeAlgorithms")
    void testRangeSort(String algorithmName, RangeSort sortingAlgorithm) {
        for (Integer[] original : rangeTestData()) {
            // Create a copy of the original array
            Integer[] toBeSorted = Arrays.copyOf(original, original.length);
            // Sort the array slice using the provided algorithm
            sortingAlgorithm.accept(toBeSorted, 1, original.length - 2);
            // Verify that the array slice is sorted
            assertTrue(Sort.isSorted(toBeSorted, 1, original.length - 2), () -> String.format(
                    "Slice is not sorted - Algorithm: %s, Before: %s, After: %s",
                    algorithmName,
                    Arrays.toString(original),
                    Arrays.toString(toBeSorted)
            ));
            assertEquals(original[0], toBeSorted[0], () -> String.format(
                    "First element changed = Algorithm: %s, Before: %s, After: %s",
                    algorithmName,
                    Arrays.toString(original),
                    Arrays.toString(toBeSorted)
            ));
            assertEquals(original[original.length - 1], toBeSorted[original.length - 1], () -> String.format(
                    "Last element changed = Algorithm: %s, Before: %s, After: %s",
                    algorithmName,
                    Arrays.toString(original),
                    Arrays.toString(toBeSorted)
            ));
        }
    }

    @FunctionalInterface
    private interface RangeSort {
        void accept(Integer[] arr, int lo, int hi);
    }

    private static class DescendingOrderComparator<T extends Comparable<T>> implements Comparator<T> {

        @Override
        public int compare(T o1, T o2) {
            return o2.compareTo(o1);
        }
    }
}