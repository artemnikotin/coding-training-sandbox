package cts.data_structures.symbol_table;

import cts.data_structures.DynamicArray;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class OrderedArrayST<Key extends Comparable<Key>, Value> implements OrderedST<Key, Value> {
    private final DynamicArray<Key> keys;
    private final DynamicArray<Value> values;

    public OrderedArrayST() {
        this.keys = new DynamicArray<>();
        this.values = new DynamicArray<>();
    }

    @Override
    public Key min() {
        return isEmpty() ? null : keys.get(0);
    }

    @Override
    public Key max() {
        return isEmpty() ? null : keys.get(keys.size() - 1);
    }

    @Override
    public Key floor(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        if (isEmpty()) {
            return null;
        }

        int r = rank(key);
        if (r < keys.size() && keys.get(r).compareTo(key) == 0) {
            return key;
        }
        return r == 0 ? null : keys.get(r - 1);
    }

    @Override
    public Key ceiling(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        if (isEmpty()) {
            return null;
        }

        int r = rank(key);
        return r == keys.size() ? null : keys.get(r);
    }

    @Override
    public int rank(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        int lo = 0, hi = keys.size() - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys.get(mid));
            if (cmp < 0) {
                hi = mid - 1;
            } else if (cmp > 0) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return lo;
    }

    @Override
    public Key select(int k) {
        if (k < 0 || k >= keys.size()) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        return keys.get(k);
    }

    @Override
    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow");
        }
        keys.removeFirst();
        values.removeFirst();
    }

    @Override
    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow");
        }
        keys.removeLast();
        values.removeLast();
    }

    @Override
    public int size(Key lo, Key hi) {
        Objects.requireNonNull(lo, "First key cannot be null");
        Objects.requireNonNull(hi, "Second key cannot be null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) {
            return rank(hi) - rank(lo) + 1;
        }
        return rank(hi) - rank(lo);
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        Objects.requireNonNull(lo, "First key cannot be null");
        Objects.requireNonNull(hi, "Second key cannot be null");

        if (lo.compareTo(hi) > 0) return () -> new KeyRangeIterator(0, 0);

        int from = rank(lo);
        int to = rank(hi);
        if (contains(hi)) to++;

        int finalTo = to;
        return () -> new KeyRangeIterator(from, Math.min(finalTo, keys.size()));
    }

    @Override
    public void put(Key key, Value val) {
        Objects.requireNonNull(key, "Key cannot be null");

        if (val == null) {
            delete(key);
            return;
        }

        int r = rank(key);

        if (r < keys.size() && keys.get(r).compareTo(key) == 0) {
            values.set(r, val);
            return;
        }

        keys.insert(r, key);
        values.insert(r, val);
    }

    @Override
    public Value get(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        if (isEmpty()) {
            return null;
        }

        int r = rank(key);
        if (r < keys.size() && keys.get(r).compareTo(key) == 0) {
            return values.get(r);
        }
        return null;
    }

    @Override
    public void delete(Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        if (isEmpty()) {
            return;
        }

        int r = rank(key);
        if (r == keys.size() || keys.get(r).compareTo(key) != 0) {
            return;
        }

        keys.remove(r);
        values.remove(r);
    }

    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return keys.isEmpty();
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public Iterable<Key> keys() {
        return keys;
    }

    private class KeyRangeIterator implements Iterator<Key> {
        private final int to;
        private int current;

        KeyRangeIterator(int from, int to) {
            this.current = from;
            this.to = to;
        }

        @Override
        public boolean hasNext() {
            return current < to;
        }

        @Override
        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            return keys.get(current++);
        }
    }
}