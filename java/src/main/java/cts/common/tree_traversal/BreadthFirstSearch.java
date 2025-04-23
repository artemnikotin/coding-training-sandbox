package cts.common.tree_traversal;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * This class implements Breadth-First Search (BFS) traversal for binary trees,
 * also known as level-order traversal. It visits nodes level by level,
 * starting from the root node and moving downward through each level.
 */
public class BreadthFirstSearch {

    /**
     * Performs level-order traversal of a binary tree using a queue.
     * Visits nodes level by level, from left to right within each level.
     *
     * @param <V> The type of values stored in the tree nodes
     * @param node The root node of the tree/subtree to traverse
     * @param visitFn A consumer function to process each node's value when visited
     */
    static <V> void levelOrder(TraversalNode<V> node, Consumer<V> visitFn) {
        // Handle empty tree case
        if (node == null) {
            return;
        }

        // Initialize a queue to keep track of nodes to visit
        // ArrayDeque is used for its efficient FIFO operations
        Queue<TraversalNode<V>> queue = new ArrayDeque<>();

        // Start with the root node
        queue.add(node);

        // Process nodes while the queue is not empty
        while (!queue.isEmpty()) {
            // Remove the node at the front of the queue
            node = queue.remove();

            // Process the current node's value
            visitFn.accept(node.value);

            // Add left child to queue if it exists
            // This ensures left nodes are processed before right nodes at the same level
            if (node.left != null) {
                queue.add(node.left);
            }

            // Add right child to queue if it exists
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }
}