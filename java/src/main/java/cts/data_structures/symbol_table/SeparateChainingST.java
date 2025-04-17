package cts.data_structures.symbol_table;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SeparateChainingST<Key, Value> implements UnorderedST<Key, Value> {
    private static final int INIT_CAPACITY = 4;
    private static final double LOAD_FACTOR_THRESHOLD = 2.0;

    private int n; // number of key-value pairs
    private int m; // hash table size
    private List<Entry>[] chains; // array of resizable arrays

    // Helper Entry class
    private class Entry {
        Key key;
        Value val;

        public Entry(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
    }

    // Constructor
    public SeparateChainingST() {
        this(INIT_CAPACITY);
    }

    // Constructor with initial capacity
    @SuppressWarnings("unchecked")
    public SeparateChainingST(int capacity) {
        this.m = capacity;
        chains = (List<Entry>[]) new LinkedList[m];
        for (int i = 0; i < m; i++) {
            chains[i] = new LinkedList<>();
        }
    }

    // Hash function
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    // Returns the number of key-value pairs
    public int size() {
        return n;
    }

    // Checks if the symbol table is empty
    public boolean isEmpty() {
        return size() == 0;
    }

    // Checks if the key exists in the symbol table
    public boolean contains(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        return get(key) != null;
    }

    // Returns the value associated with the key
    public Value get(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");

        int i = hash(key);
        for (Entry e : chains[i]) {
            if (key.equals(e.key)) {
                return e.val;
            }
        }
        return null;
    }

    // Inserts the key-value pair into the symbol table
    public void put(Key key, Value val) {
        Objects.requireNonNull(key, "Key cannot be null");
        if (val == null) {
            delete(key);
            return;
        }

        // Check load factor and resize if needed
        if ((double) n / m >= LOAD_FACTOR_THRESHOLD) {
            resize(2 * m);
        }

        int i = hash(key);
        // Check if key already exists
        for (Entry e : chains[i]) {
            if (key.equals(e.key)) {
                e.val = val;
                return;
            }
        }

        // Add new entry
        chains[i].add(new Entry(key, val));
        n++;
    }

    // Deletes the key from the symbol table
    public void delete(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");

        int i = hash(key);
        for (int j = 0; j < chains[i].size(); j++) {
            if (key.equals(chains[i].get(j).key)) {
                chains[i].remove(j);
                n--;

                // Halve table size if average length of list <= 2
                if (m > INIT_CAPACITY && (double) n / m <= 1 / LOAD_FACTOR_THRESHOLD) {
                    resize(m / 2);
                }
                return;
            }
        }
    }

    // Resizes the hash table to the given capacity
    private void resize(int capacity) {
        SeparateChainingST<Key, Value> temp = new SeparateChainingST<>(capacity);

        for (int i = 0; i < m; i++) {
            for (Entry e : chains[i]) {
                temp.put(e.key, e.val);
            }
        }

        this.m = temp.m;
        this.n = temp.n;
        this.chains = temp.chains;
    }

    // Returns all keys in the symbol table
    public Iterable<Key> keys() {
        List<Key> keys = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (Entry e : chains[i]) {
                keys.add(e.key);
            }
        }
        return keys;
    }
}