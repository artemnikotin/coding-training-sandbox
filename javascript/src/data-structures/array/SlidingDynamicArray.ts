import { DynamicArray } from "./DynamicArray";

export class SlidingDynamicArray<T> implements DynamicArray<T> {
  private static DEFAULT_INITIAL_CAPACITY = 8;
  private static GROWTH_FACTOR = 2;

  private items: (T | undefined)[];
  private size: number = 0;
  private first: number = 0;
  private last: number = -1;

  /**
   * Constructs a new DynamicArray with an optional initial capacity.
   * @param initialCapacity Initial capacity of the array.
   */
  constructor(initialCapacity?: number);
  /**
   * Constructs a new DynamicArray with an initial array.
   * @param initialArray 
   */
  constructor(initialArray?: T[]);
  /**
   * Constructor implementation
   * @param initialValue Initial capacity or array 
   */
  constructor(initialValue: number | T[] = SlidingDynamicArray.DEFAULT_INITIAL_CAPACITY) {
    if (Array.isArray(initialValue)) {
      this.items = initialValue;
      this.size = initialValue.length;
      this.last = initialValue.length - 1;
      this.items.length = this.nextPowerOfTwo(this.size);
    } else {
      this.items = Array(this.nextPowerOfTwo(this.size));
    }
  }

  /**
   * Gets the length of the array. This is a number one higher than the highest index in the array.
   */
  get length() {
    return this.size;
  }

  /**
   * TODO
   * Returns a string representation of an array.
   */
  toString(): string {
    return '';
  }

  /**
   * TODO
   * Returns a string representation of an array. The elements are converted to string using their toLocaleString methods.
   */
  toLocaleString(): string {
    return '';
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
   * Appends new elements to the end of an array, and returns the new length of the array.
   * @param items New elements to add to the array.
   */
  push(...items: T[]): number {
    items.forEach(item => this.addLast(item));
    return this.size;
  }

  /**
   * TODO
   * Adds all the elements of an array into a string, separated by the specified separator string.
   * @param separator A string used to separate one element of the array from the next in the resulting string. If omitted, the array elements are separated with a comma.
  */
  join(separator: string = ','): string {
    return '';
  }

  /**
   * TODO
   * Reverses the elements in an array in place.
   * This method mutates the array and returns a reference to the same array.
   */
  reverse(): SlidingDynamicArray<T> {
    return this;
  }

  /**
   * TODO
   * Returns a copy of a section of an array.
   * For both start and end, a negative index can be used to indicate an offset from the end of the array.
   * For example, -2 refers to the second to last element of the array.
   * @param start The beginning index of the specified portion of the array.
   * If start is undefined, then the slice begins at index 0.
   * @param end The end index of the specified portion of the array. This is exclusive of the element at the index 'end'.
   * If end is undefined, then the slice extends to the end of the array.
   */
  slice(start?: number, end?: number): T[] {
    return [];
  }

  /**
   * Removes and returns the item from the front of the array.
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
   * TODO
   * Removes elements from an array and, if necessary, inserts new elements in their place, returning the deleted elements.
   * @param start The zero-based location in the array from which to start removing elements.
   * @param deleteCount The number of elements to remove.
   * @param items Elements to insert into the array in place of the deleted elements.
   * @returns An array containing the elements that were deleted.
   */
  splice(start: number, deleteCount?: number | undefined, ...items: T[]): T[] {
    return [];
  }

  /**
   * Inserts new elements at the start of an array, and returns the new length of the array.
   * @param items Elements to insert at the start of the array.
   */
  unshift(...items: T[]): number {
    for (let i = items.length - 1; i >= 0; i--) {
      this.addFirst(items[i]);
    }
    return this.size;
  }

  /**
   * TODO
   * Returns the index of the first occurrence of a value in an array, or -1 if it is not present.
   * @param searchElement The value to locate in the array.
   * @param fromIndex The array index at which to begin the search. If fromIndex is omitted, the search starts at index 0.
   */
  indexOf(searchElement: T, fromIndex?: number): number {
    return 5;
  }

  /**
   * TODO
   * Returns the index of the last occurrence of a specified value in an array, or -1 if it is not present.
   * @param searchElement The value to locate in the array.
   * @param fromIndex The array index at which to begin searching backward. If fromIndex is omitted, the search starts at the last index in the array.
   */
  lastIndexOf(searchElement: T, fromIndex?: number): number {
    return 5;
  }

  /**
   * @returns Iterator
   */
  [Symbol.iterator](): Iterator<T> {
    let current = 0;
    const that = this;

    return {
      next(): IteratorResult<T> {
        if (current >= that.size) {
          return { done: true, value: undefined as any };
        }

        return { done: false, value: that.items[that.index(current++)]! };
      }
    };
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
   * Inserts an item at the specified index, shifting elements as necessary.
   * 
   * @param index The index to insert the item at.
   * @param item The item to insert.
   */
  insert(index: number, item: T) {
    if (index < 0 || index > this.size) {
      throw new Error("Index " + index + " out of bounds for length " + this.size);
    }

    this.addLast(undefined);
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
    if (this.size > SlidingDynamicArray.DEFAULT_INITIAL_CAPACITY && this.size === this.items.length / (SlidingDynamicArray.GROWTH_FACTOR * 2)) {
      this.resize(this.items.length / SlidingDynamicArray.GROWTH_FACTOR);
    }
  }

  /**
   * Resizes the internal array to a new capacity, re-indexing all items.
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
   * @param index Logical index.
   * @returns Physical index.
   */
  private index(index: number) {
    return (this.first + index) % this.items.length;
  }

  /**
   * Get smallest power of two that is greater or equal to a given number
   * @param n given number
   * @returns Smallest power of two
   */
  private nextPowerOfTwo(n: number): number {
    if (n <= SlidingDynamicArray.DEFAULT_INITIAL_CAPACITY) return SlidingDynamicArray.DEFAULT_INITIAL_CAPACITY;

    let power = 1;
    while (power <= n) {
      power <<= 1; // same as power *= 2
    }
    return power;
  }

  /**
   * Adds an item to the end of the array.
   * @param item The item to add.
   */
  private addLast(item: T | undefined) {
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
   * @param item The item to add.
   */
  private addFirst(item: T) {
    this.tryIncreaseCapacity();

    this.first--;
    if (this.first < 0) {
      this.first = this.items.length - 1;
    }
    this.items[this.first] = item;
    this.size++;
  }

  /**
   * Checks if the array is empty.
   * @returns True if the array is empty, false otherwise.
   */
  private isEmpty() {
    return this.size === 0;
  }
}