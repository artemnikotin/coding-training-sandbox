package cts.data_structures.stack;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class StackTest {

    // Test push and peek
    @ParameterizedTest
    @MethodSource("stackProvider")
    void testPushAndPeek(Stack<Integer> stack) {
        stack.push(10);
        assertEquals(10, stack.peek());
        assertEquals(1, stack.size());
    }

    // Test push and pop
    @ParameterizedTest
    @MethodSource("stackProvider")
    void testPushAndPop(Stack<Integer> stack) {
        stack.push(10);
        stack.push(20);
        assertEquals(20, stack.pop());
        assertEquals(10, stack.peek());
        assertEquals(1, stack.size());
    }

    // Test isEmpty
    @ParameterizedTest
    @MethodSource("stackProvider")
    void testIsEmpty(Stack<Integer> stack) {
        assertTrue(stack.isEmpty());
        stack.push(10);
        assertFalse(stack.isEmpty());
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    // Test the stack iterator
    @ParameterizedTest
    @MethodSource("stackProvider")
    void testStackIterator(Stack<Integer> stack) {
        stack.push(10);
        stack.push(20);
        stack.push(30);

        Iterator<Integer> iterator = stack.iterator();

        // Verify the elements are returned in LIFO order
        assertTrue(iterator.hasNext());
        assertEquals(30, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(20, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(10, iterator.next());
        assertFalse(iterator.hasNext());

        // Verify NoSuchElementException is thrown when there are no more elements
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    // Test pop on empty stack
    @ParameterizedTest
    @MethodSource("stackProvider")
    void testPopOnEmptyStack(Stack<Integer> stack) {
        assertThrows(IllegalStateException.class, stack::pop);
    }

    // Test peek on empty stack
    @ParameterizedTest
    @MethodSource("stackProvider")
    void testPeekOnEmptyStack(Stack<Integer> stack) {
        assertThrows(IllegalStateException.class, stack::peek);
    }

    // Provides Stack implementations for testing
    static Stream<Stack<Integer>> stackProvider() {
        return Stream.of(new ArrayStack<>(), new LinkedStack<>());
    }
}