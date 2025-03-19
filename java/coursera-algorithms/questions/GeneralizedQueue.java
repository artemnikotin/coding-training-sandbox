import java.util.NoSuchElementException;

/**
 * Coursera | Algorithms, Part I | Week 05
 * Generalized queue.
 * Design a generalized queue data type that supports all of the following operations in logarithmic time (or better) in the worst case.
 *  - Create an empty data structure.
 *  - Append an item to the end of the queue.
 *  - Remove an item from the front of the queue.
 *  - Return the ith item in the queue.
 *  - Remove the ith item from the queue.
 */
class GeneralizedQueue<T> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node {
        int key;
        T value;
        Node left, right;
        boolean color;
        int size; // Size of the subtree rooted at this node

        Node(int key, T value, boolean color, int size) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.size = size;
        }
    }

    public void append(T item) {
        int nextKey = (root == null) ? 1 : root.size + 1;
        root = put(root, nextKey, item);
    }

    public T removeFront() {
        if (root == null) {
            throw new NoSuchElementException("Queue is empty");
        }
        T minValue = get(root, 1); // internally indexing starts from 1
        root = deleteMin(root);
        if (root != null) {
            root.color = BLACK;
        }
        return minValue;
    }

    public T get(int i) {
        i++;
        if (i < 1 || i > size(root)) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        return get(root, i);
    }

    public T remove(int i) {
        i++;
        if (i < 1 || i > size(root)) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        T value = get(root, i);
        root = delete(root, i);
        if (root != null) {
            root.color = BLACK;
        }
        return value;
    }


    private boolean isRed(Node x) {
        if (x == null) {
            return false;
        }
        return x.color == RED;
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.size;
    }

    private void updateSize(Node x) {
        x.size = 1 + size(x.left) + size(x.right);
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.size = h.size;
        updateSize(h);
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.size = h.size;
        updateSize(h);
        return x;
    }

    private void flipColors(Node h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    private Node put(Node h, int key, T value) {
        if (h == null) {
            return new Node(key, value, RED, 1);
        }

        if (key < h.key) {
            h.left = put(h.left, key, value);
        } else if (key > h.key) {
            h.right = put(h.right, key, value);
        } else {
            h.value = value;
        }

        if (isRed(h.right) && !isRed(h.left)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }

        updateSize(h);
        return h;
    }

    private Node deleteMin(Node h) {
        if (h.left == null) {
            return null;
        }
        if (!isRed(h.left) && !isRed(h.left.left)) {
            h = moveRedLeft(h);
        }
        h.left = deleteMin(h.left);
        return balance(h);
    }

    private T get(Node x, int i) {
        int leftSize = size(x.left);
        if (i <= leftSize) {
            return get(x.left, i);
        } else if (i > leftSize + 1) {
            return get(x.right, i - leftSize - 1);
        } else {
            return x.value;
        }
    }

    private Node delete(Node h, int i) {
        int leftSize = size(h.left);
        if (i <= leftSize) {
            h.left = delete(h.left, i);
        } else if (i > leftSize + 1) {
            h.right = delete(h.right, i - leftSize - 1);
        } else {
            if (h.right == null) {
                return h.left;
            }
            if (h.left == null) {
                return h.right;
            }
            Node t = h;
            h = min(t.right);
            h.right = deleteMin(t.right);
            h.left = t.left;
        }
        updateSize(h);
        return balance(h);
    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    private Node balance(Node h) {
        if (isRed(h.right)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }
        updateSize(h);
        return h;
    }

    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    public static void main(String[] args) {
        GeneralizedQueue<String> queue = new GeneralizedQueue<>();
        queue.append("A");
        queue.append("B");
        queue.append("C");
        queue.append("D");
        queue.append("E");
        queue.append("F");

        System.out.println("Removing front: " + queue.removeFront());
        System.out.println("Getting 1nd item: " + queue.get(1));
        System.out.println("Removing 1nd item: " + queue.remove(1));
        System.out.println("Getting 1nd item: " + queue.get(1));
        System.out.println("Removing 1nd item: " + queue.remove(1));
        System.out.println("Removing front: " + queue.removeFront());
    }
}
