package cts.data_structures.symbol_table;

/**
 * A basic symbol table (dictionary) interface that associates keys with values.
 * This unordered version does not require keys to be comparable and maintains
 * no specific ordering of keys.
 *
 * @param <Key> the type of keys maintained by this symbol table
 * @param <Value> the type of mapped values
 */
public interface UnorderedST<Key, Value> {
    /**
     * Inserts the specified key-value pair into the symbol table.
     * If the key already exists, its value is updated.
     * If the value is null, the key is removed from the symbol table.
     *
     * @param key the key to be inserted/updated (cannot be null)
     * @param val the value to be associated with the key (null to remove the key)
     * @throws IllegalArgumentException if key is null
     */
    void put(Key key, Value val);

    /**
     * Returns the value associated with the specified key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the key, or null if the key is not present
     * @throws IllegalArgumentException if key is null
     */
    Value get(Key key);

    /**
     * Removes the specified key and its associated value from the symbol table.
     * If the key is not present, the operation has no effect.
     *
     * @param key the key to be removed
     * @throws IllegalArgumentException if key is null
     */
    void delete(Key key);

    /**
     * Checks whether the symbol table contains the specified key.
     *
     * @param key the key whose presence is to be tested
     * @return true if the symbol table contains the key, false otherwise
     * @throws IllegalArgumentException if key is null
     */
    boolean contains(Key key);

    /**
     * Tests whether the symbol table is empty (contains no key-value pairs).
     *
     * @return true if the symbol table is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of key-value pairs in the symbol table.
     *
     * @return the number of key-value pairs in the symbol table
     */
    int size();

    /**
     * Returns an iterable collection of all keys in the symbol table.
     * The iteration order is implementation-dependent.
     *
     * @return an iterable containing all keys in the symbol table
     */
    Iterable<Key> keys();
}