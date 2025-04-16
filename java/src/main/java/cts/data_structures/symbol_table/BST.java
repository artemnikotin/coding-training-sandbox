package cts.data_structures.symbol_table;

import cts.data_structures.queue.Queue;
import cts.data_structures.queue.ArrayQueue;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class BST<Key extends Comparable<Key>, Value> implements OrderedST<Key, Value> {
    private Node root;

    @Override
    public Key min() {
        if (isEmpty()) {
            return null;
        }
        Node x = min(root);
        return x.key;
    }

    @Override
    public Key max() {
        if (isEmpty()) {
            return null;
        }
        Node x = max(root);
        return x.key;
    }

    @Override
    public Key floor(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        Node x = floor(root, key);
        return x == null ? null : x.key;

    }

    @Override
    public Key ceiling(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        Node x = ceiling(root, key);
        return x == null ? null : x.key;
    }

    @Override
    public int rank(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        return rank(root, key);
    }

    @Override
    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        return select(root, k);
    }

    @Override
    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow");
        }
        root = deleteMin(root);
    }

    @Override
    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow");
        }
        root = deleteMax(root);
    }

    @Override
    public int size(Key lo, Key hi) {
        Objects.requireNonNull(lo, "First key cannot be null");
        Objects.requireNonNull(hi, "Second key cannot be null");
        AtomicInteger runCount = new AtomicInteger(0);
        inorder(root, lo, hi, _ -> runCount.incrementAndGet());
        return runCount.get();
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        Objects.requireNonNull(lo, "First key cannot be null");
        Objects.requireNonNull(hi, "Second key cannot be null");
        Queue<Key> q = new ArrayQueue<>();
        inorder(root, lo, hi, q::enqueue);
        return q;
    }

    @Override
    public void put(Key key, Value val) {
        Objects.requireNonNull(key, "Key cannot be null");
        root = put(root, key, val);
    }

    @Override
    public Value get(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                return x.value;
            }
        }
        return null;
    }

    @Override
    public void delete(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        root = delete(root, key);
    }

    @Override
    public boolean contains(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return size(root);
    }

    @Override
    public Iterable<Key> keys() {
        Queue<Key> q = new ArrayQueue<>();
        inorder(root, q::enqueue);
        return q;
    }

    private Node put(Node x, Key key, Value value) {
        if (x == null) {
            return new Node(key, value);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, value);
        } else if (cmp > 0) {
            x.right = put(x.right, key, value);
        } else {
            x.value = value;
        }
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    private Node min(Node x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    private Node max(Node x) {
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    private Node floor(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        }
        if (cmp < 0) {
            return floor(x.left, key);
        }
        Node t = floor(x.right, key);
        return t != null ? t : x;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        }
        if (cmp > 0) {
            return ceiling(x.right, key);
        }
        Node t = ceiling(x.left, key);
        return t != null ? t : x;
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.count;
    }

    private int rank(Node x, Key key) {
        if (x == null) {
            return 0;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return rank(x.left, key);
        } else if (cmp > 0) {
            return 1 + size(x.left) + rank(x.right, key);
        } else {
            return size(x.left);
        }
    }

    private Key select(Node x, int rank) {
        if (x == null) {
            return null;
        }
        if (size(x.left) == rank) {
            return x.key;
        }
        if (rank < size(x.left)) {
            return select(x.left, rank);
        }
        return select(x.right, rank - size(x.left) - 1);
    }

    private void inorder(Node x, Consumer<Key> fn) {
        if (x == null) {
            return;
        }
        inorder(x.left, fn);
        fn.accept(x.key);
        inorder(x.right, fn);
    }

    private void inorder(Node x, Key lo, Key hi, Consumer<Key> fn) {
        if (x == null) {
            return;
        }
        if (lo.compareTo(x.key) < 0) {
            inorder(x.left, lo, hi, fn);
        }
        if (lo.compareTo(x.key) <= 0 && hi.compareTo(x.key) >= 0) {
            fn.accept(x.key);
        }
        if (hi.compareTo(x.key) > 0) {
            inorder(x.right, lo, hi, fn);
        }
    }

    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    private Node deleteMax(Node x) {
        if (x.right == null) {
            return x.left;
        }
        x.right = deleteMax(x.right);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * Hibbard deletion
     */
    private Node delete(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            if (x.right == null) {
                return x.left;
            }
            if (x.left == null) {
                return x.right;
            }
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.count = size(x.left) + size(x.right) + 1;
        return x;
    }

    private class Node {
        Key key;
        Value value;
        Node left, right;
        int count;

        Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            count = 1;
        }
    }
}
