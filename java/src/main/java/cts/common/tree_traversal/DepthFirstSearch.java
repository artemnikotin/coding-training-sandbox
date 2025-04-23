package cts.common.tree_traversal;

import java.util.function.Consumer;

/**
 * This class implements depth-first search (DFS) traversal algorithms for binary trees.
 * It provides three variants: pre-order, in-order, and post-order traversal.
 */
public class DepthFirstSearch {

    /**
     * Performs a pre-order traversal of a binary tree.
     * Visits nodes in the order: root -> left subtree -> right subtree.
     *
     * @param <V> The type of values stored in the tree nodes
     * @param node The current node being processed
     * @param visitFn A function to apply to each node's value when visited
     */
    static <V> void preOrder(TraversalNode<V> node, Consumer<V> visitFn) {
        if (node == null) {
            return;  // Base case: stop when we reach a null node
        }
        visitFn.accept(node.value);  // Visit the current node first
        preOrder(node.left, visitFn);  // Then recursively traverse left subtree
        preOrder(node.right, visitFn); // Finally recursively traverse right subtree
    }

    /**
     * Performs an in-order traversal of a binary tree.
     * Visits nodes in the order: left subtree -> root -> right subtree.
     *
     * @param <V> The type of values stored in the tree nodes
     * @param node The current node being processed
     * @param visitFn A function to apply to each node's value when visited
     */
    static <V> void inOrder(TraversalNode<V> node, Consumer<V> visitFn) {
        if (node == null) {
            return;  // Base case: stop when we reach a null node
        }
        inOrder(node.left, visitFn);  // First recursively traverse left subtree
        visitFn.accept(node.value);   // Then visit the current node
        inOrder(node.right, visitFn);  // Finally recursively traverse right subtree
    }

    /**
     * Performs a post-order traversal of a binary tree.
     * Visits nodes in the order: left subtree -> right subtree -> root.
     *
     * @param <V> The type of values stored in the tree nodes
     * @param node The current node being processed
     * @param visitFn A function to apply to each node's value when visited
     */
    static <V> void postOrder(TraversalNode<V> node, Consumer<V> visitFn) {
        if (node == null) {
            return;  // Base case: stop when we reach a null node
        }
        postOrder(node.left, visitFn);  // First recursively traverse left subtree
        postOrder(node.right, visitFn); // Then recursively traverse right subtree
        visitFn.accept(node.value);     // Finally visit the current node
    }
}