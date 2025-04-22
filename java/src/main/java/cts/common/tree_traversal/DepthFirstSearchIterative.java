package cts.common.tree_traversal;

import java.util.Stack;
import java.util.function.Consumer;

public class DepthFirstSearchIterative {
    static <V> void preOrder(TraversalNode<V> node, Consumer<V> visitFn) {
        if (node == null) {
            return;
        }
        var stack = new Stack<TraversalNode<V>>();
        stack.push(node);
        while (!stack.isEmpty()) {
            node = stack.pop();
            visitFn.accept(node.value);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    static <V> void inOrder(TraversalNode<V> node, Consumer<V> visitFn) {
        var stack = new Stack<TraversalNode<V>>();
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                visitFn.accept(node.value);
                node = node.right;
            }
        }
    }

    static <V> void postOrder(TraversalNode<V> node, Consumer<V> visitFn) {
        if (node == null) {
            return;
        }
        var stack = new Stack<TraversalNode<V>>();
        TraversalNode<V> lastVisitedNode = null;
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                TraversalNode<V> peekNode = stack.peek();
                // if right child exists and traversing node from left child, then move right
                if (peekNode.right != null && lastVisitedNode != peekNode.right) {
                    node = peekNode.right;
                } else {
                    visitFn.accept(peekNode.value);
                    lastVisitedNode = stack.pop();
                }
            }
        }
    }
}