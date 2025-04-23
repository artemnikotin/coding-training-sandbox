package cts.common.tree_traversal;

import java.util.function.Consumer;

/**
 * A class implementing Morris Traversal algorithms for binary trees
 * (Pre-order, In-order, Post-order) with O(1) space complexity.
 */
public class MorrisTraversal {

    /**
     * Performs Morris Pre-order traversal (Root → Left → Right).
     * @param node The root of the tree/subtree
     * @param visitFn Consumer function to process each node's value
     * @param <V> Type of the node values
     */
    static <V> void preOrder(TraversalNode<V> node, Consumer<V> visitFn) {
        while (node != null) {
            if (node.left == null) {
                // Case 1: No left child - visit current node and move right
                visitFn.accept(node.value);
                node = node.right;
            } else {
                // Case 2: Has left child - find rightmost node in left subtree
                TraversalNode<V> predecessor = node.left;
                while (predecessor.right != null && predecessor.right != node) {
                    predecessor = predecessor.right;
                }

                if (predecessor.right == null) {
                    // Case 2a: Create thread and visit node (pre-order visit point)
                    predecessor.right = node;
                    visitFn.accept(node.value);
                    node = node.left;
                } else {
                    // Case 2b: Thread already exists - remove it and move right
                    predecessor.right = null;
                    node = node.right;
                }
            }
        }
    }

    /**
     * Performs Morris In-order traversal (Left → Root → Right).
     * @param node The root of the tree/subtree
     * @param visitFn Consumer function to process each node's value
     * @param <V> Type of the node values
     */
    static <V> void inOrder(TraversalNode<V> node, Consumer<V> visitFn) {
        while (node != null) {
            if (node.left == null) {
                // Case 1: No left child - visit current node and move right
                visitFn.accept(node.value);
                node = node.right;
            } else {
                // Case 2: Has left child - find rightmost node in left subtree
                TraversalNode<V> predecessor = node.left;
                while (predecessor.right != null && predecessor.right != node) {
                    predecessor = predecessor.right;
                }

                if (predecessor.right == null) {
                    // Case 2a: Create thread (don't visit yet)
                    predecessor.right = node;
                    node = node.left;
                } else {
                    // Case 2b: Thread exists - visit node and move right
                    predecessor.right = null;
                    visitFn.accept(node.value);
                    node = node.right;
                }
            }
        }
    }

    /**
     * Performs Morris Post-order traversal (Left → Right → Root).
     * @param node The root of the tree/subtree
     * @param visitFn Consumer function to process each node's value
     * @param <V> Type of the node values
     */
    static <V> void postOrder(TraversalNode<V> node, Consumer<V> visitFn) {
        // Use dummy node to handle root uniformly
        TraversalNode<V> dummy = new TraversalNode<>();
        dummy.left = node;
        TraversalNode<V> curr = dummy;

        while (curr != null) {
            if (curr.left == null) {
                // Case 1: No left child - just move right
                curr = curr.right;
            } else {
                // Case 2: Has left child - find rightmost node in left subtree
                TraversalNode<V> predecessor = curr.left;
                while (predecessor.right != null && predecessor.right != curr) {
                    predecessor = predecessor.right;
                }

                if (predecessor.right == null) {
                    // Case 2a: Create thread and move left
                    predecessor.right = curr;
                    curr = curr.left;
                } else {
                    // Case 2b: Thread exists - process nodes in reverse
                    predecessor.right = null;
                    reverseAddNodes(curr.left, visitFn);
                    curr = curr.right;
                }
            }
        }
    }

    /**
     * Helper function to reverse right pointers and visit nodes.
     * @param node Starting node of the subtree to process
     * @param visitFn Consumer function to process each node's value
     * @param <V> Type of the node values
     */
    private static <V> void reverseAddNodes(TraversalNode<V> node, Consumer<V> visitFn) {
        // Reverse the right pointers in the subtree
        TraversalNode<V> start = reverseRightNodes(node);
        // Visit all nodes in the reversed chain
        TraversalNode<V> curr = start;
        while (curr != null) {
            visitFn.accept(curr.value);
            curr = curr.right;
        }
        // Restore the original structure
        reverseRightNodes(start);
    }

    /**
     * Helper function to reverse right pointers in a subtree.
     * @param node Starting node of the subtree to reverse
     * @param <V> Type of the node values
     * @return The new starting node (originally the rightmost node)
     */
    private static <V> TraversalNode<V> reverseRightNodes(TraversalNode<V> node) {
        TraversalNode<V> prev = null;
        TraversalNode<V> curr = node;
        while (curr != null) {
            TraversalNode<V> next = curr.right;
            curr.right = prev;  // Reverse the pointer
            prev = curr;
            curr = next;
        }
        return prev;  // Return the new head of the reversed list
    }
}