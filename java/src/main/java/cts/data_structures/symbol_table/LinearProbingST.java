package cts.data_structures.symbol_table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LinearProbingST<Key, Value> implements UnorderedST<Key, Value> {
    private static final int INIT_CAPACITY = 4;
    private static final double LOAD_FACTOR_THRESHOLD = 0.5;

    private int n;           // number of key-value pairs
    private int m;           // size of hash table
    private Key[] keys;      // array of keys
    private Value[] vals;    // array of values

    // Constructor
    public LinearProbingST() {
        this(INIT_CAPACITY);
    }

    // Constructor with initial capacity
    @SuppressWarnings("unchecked")
    public LinearProbingST(int capacity) {
        m = capacity;
        n = 0;
        keys = (Key[]) new Object[m];
        vals = (Value[]) new Object[m];
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

    // Resizes the hash table to the given capacity
    private void resize(int capacity) {
        LinearProbingST<Key, Value> temp = new LinearProbingST<>(capacity);

        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }

        this.m = temp.m;
        this.n = temp.n;
        this.keys = temp.keys;
        this.vals = temp.vals;
    }

    // Inserts the key-value pair into the symbol table
    public void put(Key key, Value val) {
        Objects.requireNonNull(key, "Key cannot be null");
        if (val == null) {
            delete(key);
            return;
        }

        // Double table size if load factor >= 0.5
        if (n >= m * LOAD_FACTOR_THRESHOLD) {
            resize(2 * m);
        }

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }

        keys[i] = key;
        vals[i] = val;
        n++;
    }

    // Returns the value associated with the key
    public Value get(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        for (int i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                return vals[i];
            }
        }
        return null;
    }

    // Deletes the key from the symbol table
    public void delete(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        if (!contains(key)) {
            return;
        }

        // Find position of key
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }

        // Delete key and value
        keys[i] = null;
        vals[i] = null;

        // Rehash all keys in same cluster
        i = (i + 1) % m;
        while (keys[i] != null) {
            Key keyToRehash = keys[i];
            Value valToRehash = vals[i];
            keys[i] = null;
            vals[i] = null;
            n--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % m;
        }

        n--;

        // Halve table size if load factor <= 1/8
        if (n > 0 && n <= m / 8) {
            resize(m / 2);
        }
    }

    // Returns all keys in the symbol table
    public Iterable<Key> keys() {
        List<Key> queue = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                queue.add(keys[i]);
            }
        }
        return queue;
    }
}