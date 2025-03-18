/**
 * Coursera | Algorithms, Part I | Week 04
 * Check if a binary tree is a BST.
 * Given a binary tree where each Node contains a key, determine whether it is a binary search tree.
 * Use extra space proportional to the height of the tree.
 */
public class BTSCheck {
    public static <K extends Comparable<K>> boolean check(Node<K> node) {
        return check(node, null, null);
    }

    public static <K extends Comparable<K>> boolean check(Node<K> node, K max, K min) {
        if (node == null) {
            return true;
        }
        if (max != null && node.key.compareTo(max) >= 0) {
            return false;
        }
        if (min != null && node.key.compareTo(min) <= 0) {
            return false;
        }
        return check(node.left, node.key, min) && check(node.right, max, node.key);
    }

    public static class Node<K extends Comparable<K>> {
        K key;
        Node<K> left = null;
        Node<K> right = null;

        Node(K key) {
            this.key = key;
        }
    }

    public static void main(String[] args) {
        var bst = new Node<>(20);
        bst.left = new Node<>(10);
        bst.right = new Node<>(30);
        bst.left.left = new Node<>(9);
        bst.left.right = new Node<>(19);

        System.out.println(check(bst));
    }
}
