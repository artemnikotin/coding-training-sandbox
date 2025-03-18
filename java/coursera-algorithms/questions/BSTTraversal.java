/**
 * Coursera | Algorithms, Part I | Week 04
 * Inorder traversal with constant extra space.
 * Design an algorithm to perform an inorder traversal of a binary search tree using only a constant amount of extra space.
 */
public class BSTTraversal {
    /**
     * Morris Traversal
     */
    public static <K> void inOrder(Node<K> root) {
        Node<K> current = root;

        while (current != null) {
            if (current.left == null) {
                System.out.print(current.key + " ");
                current = current.right;
            } else {
                Node<K> predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }

                if (predecessor.right == null) {
                    predecessor.right = current;
                    current = current.left;
                } else {
                    predecessor.right = null;
                    System.out.print(current.key + " ");
                    current = current.right;
                }
            }
        }
    }

    public static class Node<K> {
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

        System.out.println("In-order traversal:");
        inOrder(bst);
    }
}
