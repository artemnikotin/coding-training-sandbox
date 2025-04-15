package cts.data_structures.symbol_table;

import java.util.NoSuchElementException;

/**
 * An ordered symbol table interface that extends the basic unordered symbol table
 * with operations that depend on key ordering. Keys must be {@link Comparable}.
 *
 * @param <Key> the type of keys maintained by this symbol table, must implement Comparable
 * @param <Value> the type of mapped values
 */
public interface OrderedST<Key extends Comparable<Key>, Value> extends UnorderedST<Key, Value> {
    /**
     * Returns the smallest key in this symbol table.
     *
     * @return the smallest key in the symbol table,
     *         or {@code null} if the symbol table is empty
     */
    Key min();

    /**
     * Returns the largest key in this symbol table.
     *
     * @return the largest key in the symbol table,
     *         or {@code null} if the symbol table is empty
     */
    Key max();

    /**
     * Returns the largest key in this symbol table less than or equal to the given key.
     *
     * @param key the key to compare against
     * @return the largest key less than or equal to {@code key},
     *         or {@code null} if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    Key floor(Key key);

    /**
     * Returns the smallest key in this symbol table greater than or equal to the given key.
     *
     * @param key the key to compare against
     * @return the smallest key greater than or equal to {@code key},
     *         or {@code null} if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    Key ceiling(Key key);

    /**
     * Returns the number of keys in this symbol table strictly less than {@code key}.
     *
     * @param key the key to compare against
     * @return the number of keys strictly less than {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    int rank(Key key);

    /**
     * Returns the key in this symbol table of the given rank.
     * This key has the property that there are exactly {@code k} keys
     * in the symbol table that are smaller.
     *
     * @param k the rank (0-based index)
     * @return the key of rank {@code k},
     *         or {@code null} if {@code k} is out of bounds
     */
    Key select(int k);

    /**
     * Removes the smallest key and its associated value from this symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    void deleteMin();

    /**
     * Removes the largest key and its associated value from this symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    void deleteMax();

    /**
     * Returns the number of keys in this symbol table in the specified range [lo..hi].
     *
     * @param lo the lower bound (inclusive)
     * @param hi the upper bound (inclusive)
     * @return the number of keys between {@code lo} (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi} is {@code null}
     */
    int size(Key lo, Key hi);

    /**
     * Returns all keys in this symbol table in the given range [lo..hi], in sorted order.
     *
     * @param lo the lower bound (inclusive)
     * @param hi the upper bound (inclusive)
     * @return all keys between {@code lo} (inclusive) and {@code hi} (inclusive),
     *         in ascending order
     * @throws IllegalArgumentException if either {@code lo} or {@code hi} is {@code null}
     */
    Iterable<Key> keys(Key lo, Key hi);
}