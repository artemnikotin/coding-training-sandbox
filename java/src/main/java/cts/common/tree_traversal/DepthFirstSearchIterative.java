package cts.common.tree_traversal;

import java.util.Stack;
import java.util.function.Consumer;

/**
 * This class implements iterative depth-first search (DFS) traversal algorithms for binary trees
 * using an explicit stack instead of recursion. It provides three variants:
 * pre-order, in-order, and post-order traversal.
 */
public class DepthFirstSearchIterative {

    /**
     * Performs iterative pre-order traversal of a binary tree.
     * Visits nodes in the order: root -> left subtree -> right subtree.
     *
     * @param <V> The type of values stored in the tree nodes
     * @param node The root node of the tree/subtree to traverse
     * @param visitFn A function to apply to each node's value when visited
     */
    static <V> void preOrder(TraversalNode<V> node, Consumer<V> visitFn) {
        if (node == null) {
            return;  // Handle empty tree case
        }

        // Use a stack to simulate the call stack of recursive approach
        var stack = new Stack<TraversalNode<V>>();
        stack.push(node);  // Start with the root node

        while (!stack.isEmpty()) {
            node = stack.pop();  // Get the next node to process
            visitFn.accept(node.value);  // Visit the node first (pre-order)

            // Push right child first so left is processed next (LIFO order)
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    /**
     * Performs iterative in-order traversal of a binary tree.
     * Visits nodes in the order: left subtree -> root -> right subtree.
     *
     * @param <V> The type of values stored in the tree nodes
     * @param node The root node of the tree/subtree to traverse
     * @param visitFn A function to apply to each node's value when visited
     */
    static <V> void inOrder(TraversalNode<V> node, Consumer<V> visitFn) {
        var stack = new Stack<TraversalNode<V>>();

        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                // Keep going left and pushing nodes onto stack
                stack.push(node);
                node = node.left;
            } else {
                // When no more left, pop, visit, and go right
                node = stack.pop();
                visitFn.accept(node.value);  // Visit node after left (in-order)
                node = node.right;
            }
        }
    }

    /**
     * Performs iterative post-order traversal of a binary tree.
     * Visits nodes in the order: left subtree -> right subtree -> root.
     *
     * @param <V> The type of values stored in the tree nodes
     * @param node The root node of the tree/subtree to traverse
     * @param visitFn A function to apply to each node's value when visited
     */
    static <V> void postOrder(TraversalNode<V> node, Consumer<V> visitFn) {
        if (node == null) {
            return;  // Handle empty tree case
        }

        var stack = new Stack<TraversalNode<V>>();
        TraversalNode<V> lastVisitedNode = null;  // Tracks the last visited node

        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                // Keep going left and pushing nodes onto stack
                stack.push(node);
                node = node.left;
            } else {
                TraversalNode<V> peekNode = stack.peek();

                // If right child exists and we haven't come from right child
                if (peekNode.right != null && lastVisitedNode != peekNode.right) {
                    node = peekNode.right;  // Move to right child
                } else {
                    // Visit node only after both subtrees are processed (post-order)
                    visitFn.accept(peekNode.value);
                    lastVisitedNode = stack.pop();  // Track last visited node
                }
            }
        }
    }
}