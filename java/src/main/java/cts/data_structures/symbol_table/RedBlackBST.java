package cts.data_structures.symbol_table;

import cts.data_structures.queue.Queue;
import cts.data_structures.queue.ArrayQueue;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class RedBlackBST<Key extends Comparable<Key>, Value> implements OrderedST<Key, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    @Override
    public Key min() {
        if (isEmpty()) {
            return null;
        }
        return min(root).key;
    }

    @Override
    public Key max() {
        if (isEmpty()) {
            return null;
        }
        return max(root).key;
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

        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }

        root = deleteMin(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    @Override
    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow");
        }

        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }

        root = deleteMax(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    @Override
    public int size(Key lo, Key hi) {
        Objects.requireNonNull(lo, "First key cannot be null");
        Objects.requireNonNull(hi, "Second key cannot be null");
        if (lo.compareTo(hi) > 0) {
            return 0;
        }
        if (contains(hi)) {
            return rank(hi) - rank(lo) + 1;
        } else {
            return rank(hi) - rank(lo);
        }
    }

    @Override
    public void put(Key key, Value val) {
        Objects.requireNonNull(key, "Key cannot be null");
        if (val == null) {
            delete(key);
            return;
        }

        root = put(root, key, val);
        root.color = BLACK;
    }

    @Override
    public Value get(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        return get(root, key);
    }

    @Override
    public void delete(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        if (!contains(key)) {
            return;
        }

        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }

        root = delete(root, key);
        if (!isEmpty()) {
            root.color = BLACK;
        }
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
        return keys(min(), max());
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        Objects.requireNonNull(lo, "First key cannot be null");
        Objects.requireNonNull(hi, "Second key cannot be null");
        Queue<Key> queue = new ArrayQueue<>();
        inorder(root, lo, hi, queue::enqueue);
        return queue;
    }

    private Value get(Node x, Key key) {
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

        if (isRed(x.right) && !isRed(x.left)) {
            x = rotateLeft(x);
        }
        if (isRed(x.left) && isRed(x.left.left)) {
            x = rotateRight(x);
        }
        if (isRed(x.left) && isRed(x.right)) {
            flipColors(x);
        }

        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    private Node deleteMin(Node x) {
        if (x.left == null) {
            return null;
        }

        if (!isRed(x.left) && !isRed(x.left.left)) {
            x = moveRedLeft(x);
        }

        x.left = deleteMin(x.left);
        return balance(x);
    }

    private Node deleteMax(Node x) {
        if (isRed(x.left)) {
            x = rotateRight(x);
        }

        if (x.right == null) {
            return null;
        }

        if (!isRed(x.right) && !isRed(x.right.left)) {
            x = moveRedRight(x);
        }

        x.right = deleteMax(x.right);
        return balance(x);
    }

    private Node delete(Node x, Key key) {
        if (key.compareTo(x.key) < 0) {
            if (!isRed(x.left) && !isRed(x.left.left)) {
                x = moveRedLeft(x);
            }
            x.left = delete(x.left, key);
        } else {
            if (isRed(x.left)) {
                x = rotateRight(x);
            }
            if (key.compareTo(x.key) == 0 && (x.right == null)) {
                return null;
            }
            if (!isRed(x.right) && !isRed(x.right.left)) {
                x = moveRedRight(x);
            }
            if (key.compareTo(x.key) == 0) {
                Node h = min(x.right);
                x.key = h.key;
                x.value = h.value;
                x.right = deleteMin(x.right);
            } else {
                x.right = delete(x.right, key);
            }
        }
        return balance(x);
    }

    private Node moveRedLeft(Node x) {
        flipColors(x);
        if (isRed(x.right.left)) {
            x.right = rotateRight(x.right);
            x = rotateLeft(x);
            flipColors(x);
        }
        return x;
    }

    private Node moveRedRight(Node x) {
        flipColors(x);
        if (isRed(x.left.left)) {
            x = rotateRight(x);
            flipColors(x);
        }
        return x;
    }

    private Node balance(Node x) {
        if (isRed(x.right)) {
            x = rotateLeft(x);
        }
        if (isRed(x.left) && isRed(x.left.left)) {
            x = rotateRight(x);
        }
        if (isRed(x.left) && isRed(x.right)) {
            flipColors(x);
        }

        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    private Node max(Node x) {
        if (x.right == null) {
            return x;
        }
        return max(x.right);
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

    private Key select(Node x, int k) {
        if (x == null) {
            return null;
        }
        int leftSize = size(x.left);
        if (k < leftSize) {
            return select(x.left, k);
        } else if (k > leftSize) {
            return select(x.right, k - leftSize - 1);
        } else {
            return x.key;
        }
    }

    private void inorder(Node x, Key lo, Key hi, Consumer<Key> fn) {
        if (x == null) {
            return;
        }
        int cmpLo = lo.compareTo(x.key);
        int cmpHi = hi.compareTo(x.key);
        if (cmpLo < 0) {
            inorder(x.left, lo, hi, fn);
        }
        if (cmpLo <= 0 && cmpHi >= 0) {
            fn.accept(x.key);
        }
        if (cmpHi > 0) {
            inorder(x.right, lo, hi, fn);
        }
    }

    private boolean isRed(Node x) {
        if (x == null) {
            return false;
        }
        return x.color == RED;
    }

    private Node rotateLeft(Node x) {
        Node h = x.right;
        x.right = h.left;
        h.left = x;
        h.color = x.color;
        x.color = RED;
        h.count = x.count;
        x.count = 1 + size(x.left) + size(x.right);
        return h;
    }

    private Node rotateRight(Node x) {
        Node h = x.left;
        x.left = h.right;
        h.right = x;
        h.color = x.color;
        x.color = RED;
        h.count = x.count;
        x.count = 1 + size(x.left) + size(x.right);
        return h;
    }

    private void flipColors(Node x) {
        x.color = !x.color;
        x.left.color = !x.left.color;
        x.right.color = !x.right.color;
    }

    private class Node {
        Key key;
        Value value;
        Node left, right;
        int count;
        boolean color;

        Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            this.count = 1;
            this.color = RED;
        }
    }
}