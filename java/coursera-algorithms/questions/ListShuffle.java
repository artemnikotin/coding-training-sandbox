import java.util.Random;

/**
 * Coursera | Algorithms, Part I | Week 03
 * Shuffling a linked list.
 * Given a singly-linked list containing nn items, rearrange the items uniformly at random. Your algorithm should
 * consume a logarithmic (or constant) amount of extra memory and run in time proportional to n log(n) in the worst case.
 */
public class ListShuffle {
    private static final Random rand = new Random();

    public static Node fromArr(int[] arr) {
        if (arr.length == 0) {
            return null;
        }

        var head = new Node();
        head.value = arr[0];
        Node current = head;

        for (int i = 1; i < arr.length; i++) {
            current.next = new Node();
            current = current.next;
            current.value = arr[i];
        }
        return head;
    }

    public static Node shuffle(Node head) {
        if (head.next == null) {
            return head;
        }
        Node[] heads = split(head);

        heads[0] = shuffle(heads[0]);
        heads[1] = shuffle(heads[1]);

        return merge(heads[0], heads[1]);
    }

    public static void print(Node head) {
        System.out.print("List:");
        while (head != null) {
            System.out.print(" " + head.value);
            head = head.next;
        }
        System.out.println();
    }

    private static Node[] split(Node head) {
        Node head1 = head;
        Node curr1 = head1;
        head = head.next;
        Node head2 = head;
        Node curr2 = head2;
        boolean sw = true;
        while (head.next != null) {
            head = head.next;
            if (sw) {
                curr1.next = head;
                curr1 = head;
            } else {
                curr2.next = head;
                curr2 = head;
            }
            sw = !sw;
        }
        curr1.next = null;
        curr2.next = null;
        return new Node[]{head1, head2};
    }

    private static Node merge(Node head1, Node head2) {
        Node head = new Node();
        Node curr = head;
        while (head1 != null && head2 != null) {
            if (rand.nextBoolean()) {
                curr.next = head1;
                head1 = head1.next;
            } else {
                curr.next = head2;
                head2 = head2.next;
            }
            curr = curr.next;
        }
        while (head1 != null) {
            curr.next = head1;
            head1 = head1.next;
            curr = curr.next;
        }
        while (head2 != null) {
            curr.next = head2;
            head2 = head2.next;
            curr = curr.next;
        }

        return head.next;
    }

    public static class Node {
        int value;
        Node next;
    }

    public static void main(String[] args) {
        var list = ListShuffle.fromArr(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20});
        assert list != null;
        print(list);
        list = shuffle(list);
        print(list);
        list = shuffle(list);
        print(list);
        list = shuffle(list);
        print(list);
    }
}
