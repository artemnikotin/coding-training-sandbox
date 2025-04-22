package cts.common.tree_traversal;

public class TraversalNode<V> {
    public TraversalNode<V> right = null;
    public TraversalNode<V> left = null;
    public V value;

    public TraversalNode() {
        value = null;
    }

    public TraversalNode(V v) {
        value = v;
    }
}
