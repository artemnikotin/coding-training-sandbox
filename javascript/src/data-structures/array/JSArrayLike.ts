export interface JSArrayLike<T> {
  /**
   * Adds an item to the end of the array.
   * @param item The item to add.
   */
  push(item: T | undefined): void;

  /**
   * Adds an item to the front of the array.
   * @param item The item to add.
   */
  unshift(item: T | undefined): void;

  /**
   * Removes and returns the item from the end of the array.
   * @returns The item removed, or undefined if the array is empty.
   */
  pop(): T | undefined;

  /**
   * Removes and returns the item from the front of the array.
   * @returns The item removed, or undefined if the array is empty.
   */
  shift(): T | undefined;

  /**
   * Returns the item at the specified index without modifying the array.
   * @param index The index of the item to retrieve.
   * @returns The item at the given index, or undefined if out of bounds.
   */
  get(index: number): T | undefined;

  /**
   * Sets the value at a specific index. If the index is beyond current size,
   * resizes the array accordingly.
   * @param index Index to set the value at.
   * @param item The item to set.
   */
  set(index: number, item: T): void;

  /**
   * Alias for `get(index)`.
   * @param index Index of the item.
   * @returns The item at the given index or undefined.
   */
  at(index: number): T | undefined;

  /**
   * Returns the number of elements in the array.
   */
  length: number;
}