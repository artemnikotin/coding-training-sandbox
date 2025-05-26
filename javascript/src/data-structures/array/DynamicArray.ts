import { JSArrayLike } from "./JSArrayLike";

export class DynamicArray<T> implements JSArrayLike<T> {
  private static DEFAULT_INITIAL_CAPACITY = 8;
  private static GROWTH_FACTOR = 2;

  private items: (T | undefined)[];
  private size: number = 0;
  private first: number = 0;
  private last: number = -1;

  /**
   * Constructs a new DynamicArray with an optional initial capacity.
   * The capacity must be a positive power of two.
   * 
   * @param initialCapacity Initial capacity of the array.
   */
  constructor(initialCapacity: number = DynamicArray.DEFAULT_INITIAL_CAPACITY) {
    if (!this.isPositivePowerOfTwo(initialCapacity)) {
      throw new Error("Capacity can only be a positive power of two");
    }
    this.items = Array(initialCapacity);
  }

  /**
   * Adds an item to the end of the array.
   * 
   * @param item The item to add.
   */
  push(item: T | undefined) {
    this.tryIncreaseCapacity();

    this.last++;
    if (this.last >= this.items.length) {
      this.last = 0;
    }
    this.items[this.last] = item;
    this.size++;
  }

  /**
   * Adds an item to the front of the array.
   * 
   * @param item The item to add.
   */
  unshift(item: T | undefined) {
    this.tryIncreaseCapacity();

    this.first--;
    if (this.first < 0) {
      this.first = this.items.length - 1;
    }
    this.items[this.first] = item;
    this.size++;
  }

  /**
   * Removes and returns the item from the end of the array.
   * 
   * @returns The item removed, or undefined if the array is empty.
   */
  pop(): T | undefined {
    if (this.isEmpty()) {
      return undefined;
    }

    const item = this.items[this.last];
    this.items[this.last] = undefined;
    this.last--;
    if (this.last < 0) {
      this.last = this.items.length - 1;
    }
    this.size--;
    this.tryDecreaseCapacity();
    return item;
  }

  /**
   * Removes and returns the item from the front of the array.
   * 
   * @returns The item removed, or undefined if the array is empty.
   */
  shift(): T | undefined {
    if (this.isEmpty()) {
      return undefined;
    }

    const item = this.items[this.first];
    this.items[this.first] = undefined;
    this.first++;
    if (this.first >= this.items.length) {
      this.first = 0;
    }
    this.size--;
    this.tryDecreaseCapacity();
    return item;
  }

  /**
   * Returns the item at the specified index without modifying the array.
   * 
   * @param index The index of the item to retrieve.
   * @returns The item at the given index, or undefined if out of bounds.
   */
  get(index: number): T | undefined {
    if (index < 0 || index >= this.size) {
      return undefined;
    }
    return this.items[this.index(index)];
  }

  /**
   * Sets the value at a specific index. If the index is beyond current size,
   * resizes the array accordingly.
   * 
   * @param index Index to set the value at.
   * @param item The item to set.
   */
  set(index: number, item: T) {
    if (index < 0) {
      return;
    }
    if (index >= this.size) {
      this.resize(Math.ceil(Math.log2(index)));
    }
    this.items[this.index(index)] = item;
  }

  /**
   * Alias for `get(index)`.
   * 
   * @param index Index of the item.
   * @returns The item at the given index or undefined.
   */
  at(index: number): T | undefined {
    return this.get(index);
  }

  /**
   * Returns the number of elements in the array.
   */
  get length(): number {
    return this.size;
  }

  /**
   * Checks if the array is empty.
   * 
   * @returns True if the array is empty, false otherwise.
   */
  isEmpty() {
    return this.size === 0;
  }

  /**
   * Inserts an item at the specified index, shifting elements as necessary.
   * 
   * @param index The index to insert the item at.
   * @param item The item to insert.
   */
  insert(index: number, item: T) {
    if (index < 0 || index > this.size) {
      throw new Error("Index " + index + " out of bounds for length " + this.size);
    }

    this.push(undefined);
    for (let i = this.size - 1; i > index; i--) {
      this.items[this.index(i)] = this.items[this.index(i - 1)];
    }
    this.items[this.index(index)] = item;
  }

  /**
   * Removes and returns the item at the specified index, shifting elements.
   * 
   * @param index The index of the item to remove.
   * @returns The removed item.
   */
  remove(index: number): T | undefined {
    if (index < 0 || index > this.size) {
      throw new Error("Index " + index + " out of bounds for length " + this.size);
    }

    const item = this.get(index);
    for (; index < this.size - 1; index++) {
      this.items[this.index(index)] = this.items[this.index(index + 1)];
    }
    this.pop();
    return item;
  }

  /**
   * Increases the capacity of the array if it is full.
   */
  private tryIncreaseCapacity() {
    if (this.size === this.items.length) {
      this.resize(2 * this.items.length);
    }
  }

  /**
   * Decreases the capacity of the array if it is underutilized.
   */
  private tryDecreaseCapacity() {
    if (this.size > DynamicArray.DEFAULT_INITIAL_CAPACITY && this.size == this.items.length / (DynamicArray.GROWTH_FACTOR * 2)) {
      this.resize(this.items.length / DynamicArray.GROWTH_FACTOR);
    }
  }

  /**
   * Resizes the internal array to a new capacity, re-indexing all items.
   * 
   * @param capacity New capacity for the array.
   */
  private resize(capacity: number) {
    const copy = Array(capacity);
    for (let i = 0; i < this.size; i++) {
      copy[i] = this.items[this.index(i)];
    }
    this.items = copy;
    this.first = 0;
    this.last = this.size - 1;
  }

  /**
   * Translates a logical index to a physical index in the internal array.
   * 
   * @param index Logical index.
   * @returns Physical index.
   */
  private index(index: number) {
    return (this.first + index) % this.items.length;
  }

  /**
   * Checks if a number is a positive power of two.
   * 
   * @param n The number to check.
   * @returns True if n is a positive power of two.
   */
  private isPositivePowerOfTwo(n: number) {
    if (n <= 1) {
      return false;
    }
    return (n & (n - 1)) === 0;
  }
}