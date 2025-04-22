package cts.common.tree_traversal;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Consumer;

public class BreadthFirstSearch {
    static <V> void levelOrder(TraversalNode<V> node, Consumer<V> visitFn) {
        if (node == null) {
            return;
        }

        Queue<TraversalNode<V>> queue = new ArrayDeque<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            node = queue.remove();
            visitFn.accept(node.value);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }
}
