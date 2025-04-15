package cts.data_structures.symbol_table;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedListST<Key, Value> implements UnorderedST<Key, Value> {
    private Node head;
    private int size;

    @Override
    public void put(Key key, Value val) {
        Objects.requireNonNull(key, "Key cannot be null");

        if (val == null) {
            delete(key);
            return;
        }

        // Update existing key if found
        for (Node current = head; current != null; current = current.next) {
            if (key.equals(current.key)) {
                current.value = val;
                return;
            }
        }

        // Add new node at the beginning (O(1) operation)
        head = new Node(key, val, head);
        size++;
    }

    @Override
    public Value get(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");

        for (Node current = head; current != null; current = current.next) {
            if (key.equals(current.key)) {
                return current.value;
            }
        }
        return null;
    }

    @Override
    public void delete(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");

        Node dummy = new Node(null, null, head);
        Node prev = dummy;
        Node current = head;

        while (current != null) {
            if (key.equals(current.key)) {
                prev.next = current.next;
                size--;
                break;
            }
            prev = current;
            current = current.next;
        }

        head = dummy.next;
    }

    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterable<Key> keys() {
        return () -> new Iterator<>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Key next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Key key = current.key;
                current = current.next;
                return key;
            }
        };
    }

    private class Node {
        final Key key;
        Value value;
        Node next;

        Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}