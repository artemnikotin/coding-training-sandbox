package cts.common.tree_traversal;

import java.util.function.Consumer;

public class DepthFirstSearch {
    static <V> void preOrder(TraversalNode<V> node, Consumer<V> visitFn) {
        if (node == null) {
            return;
        }
        visitFn.accept(node.value);
        preOrder(node.left, visitFn);
        preOrder(node.right, visitFn);
    }

    static <V> void inOrder(TraversalNode<V> node, Consumer<V> visitFn) {
        if (node == null) {
            return;
        }
        inOrder(node.left, visitFn);
        visitFn.accept(node.value);
        inOrder(node.right, visitFn);
    }

    static <V> void postOrder(TraversalNode<V> node, Consumer<V> visitFn) {
        if (node == null) {
            return;
        }
        postOrder(node.left, visitFn);
        postOrder(node.right, visitFn);
        visitFn.accept(node.value);
    }
}