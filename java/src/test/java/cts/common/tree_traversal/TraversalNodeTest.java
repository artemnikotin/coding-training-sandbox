package cts.common.tree_traversal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class TraversalNodeTest {
    /* Example Tree:
          F
        /   \
       B     G
     /   \    \
    A     D     I
         / \   /
        C   E H
    */
    private static TraversalNode<String> root;

    @BeforeAll
    public static void init() {
        root = new TraversalNode<>("F");
        root.left = new TraversalNode<>("B");
        root.left.left = new TraversalNode<>("A");
        root.left.right = new TraversalNode<>("D");
        root.left.right.left = new TraversalNode<>("C");
        root.left.right.right = new TraversalNode<>("E");
        root.right = new TraversalNode<>("G");
        root.right.right = new TraversalNode<>("I");
        root.right.right.left = new TraversalNode<>("H");
    }

    @ParameterizedTest
    @MethodSource("preOrderAlgorithms")
    void testPreOrder(String algorithmName, BiConsumer<TraversalNode<String>, Consumer<String>> sortingAlgorithm) {
        var expected = Arrays.asList("F", "B", "A", "D", "C", "E", "G", "I", "H");

        List<String> list = new ArrayList<>();
        sortingAlgorithm.accept(root, list::add);

        assertIterableEquals(expected, list, () -> String.format(
                "Algorithm: %s, Expected: %s, Actual: %s",
                algorithmName,
                Arrays.toString(expected.toArray()),
                Arrays.toString(list.toArray())
        ));
    }

    @ParameterizedTest
    @MethodSource("inOrderAlgorithms")
    void testInOrder(String algorithmName, BiConsumer<TraversalNode<String>, Consumer<String>> sortingAlgorithm) {
        var expected = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I");

        List<String> list = new ArrayList<>();
        sortingAlgorithm.accept(root, list::add);

        assertIterableEquals(expected, list, () -> String.format(
                "Algorithm: %s, Expected: %s, Actual: %s",
                algorithmName,
                Arrays.toString(expected.toArray()),
                Arrays.toString(list.toArray())
        ));
    }

    @ParameterizedTest
    @MethodSource("postOrderAlgorithms")
    void testPostOrder(String algorithmName, BiConsumer<TraversalNode<String>, Consumer<String>> sortingAlgorithm) {
        var expected = Arrays.asList("A", "C", "E", "D", "B", "H", "I", "G", "F");

        List<String> list = new ArrayList<>();
        sortingAlgorithm.accept(root, list::add);

        assertIterableEquals(expected, list, () -> String.format(
                "Algorithm: %s, Expected: %s, Actual: %s",
                algorithmName,
                Arrays.toString(expected.toArray()),
                Arrays.toString(list.toArray())
        ));
    }

    @Test
    void testLevelOrder() {
        var expected = Arrays.asList("F", "B", "G", "A", "D", "I", "C", "E", "H");

        List<String> list = new ArrayList<>();
        BreadthFirstSearch.levelOrder(root, list::add);

        assertIterableEquals(expected, list);
    }

    static Stream<Arguments> preOrderAlgorithms() {
        return Stream.of(
                Arguments.of("DSF", (BiConsumer<TraversalNode<String>, Consumer<String>>) DepthFirstSearch::preOrder),
                Arguments.of("DSFIterative", (BiConsumer<TraversalNode<String>, Consumer<String>>) DepthFirstSearchIterative::preOrder),
                Arguments.of("MorrisTraversal", (BiConsumer<TraversalNode<String>, Consumer<String>>) MorrisTraversal::preOrder)
        );
    }

    static Stream<Arguments> inOrderAlgorithms() {
        return Stream.of(
                Arguments.of("DSF", (BiConsumer<TraversalNode<String>, Consumer<String>>) DepthFirstSearch::inOrder),
                Arguments.of("DSFIterative", (BiConsumer<TraversalNode<String>, Consumer<String>>) DepthFirstSearchIterative::inOrder),
                Arguments.of("MorrisTraversal", (BiConsumer<TraversalNode<String>, Consumer<String>>) MorrisTraversal::inOrder)
        );
    }

    static Stream<Arguments> postOrderAlgorithms() {
        return Stream.of(
                Arguments.of("DSF", (BiConsumer<TraversalNode<String>, Consumer<String>>) DepthFirstSearch::postOrder),
                Arguments.of("DSFIterative", (BiConsumer<TraversalNode<String>, Consumer<String>>) DepthFirstSearchIterative::postOrder),
                Arguments.of("MorrisTraversal", (BiConsumer<TraversalNode<String>, Consumer<String>>) MorrisTraversal::postOrder)
        );
    }
}