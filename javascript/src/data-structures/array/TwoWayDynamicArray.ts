import { DynamicArray } from "./DynamicArray";

/**
 * TODO
 *  - TESTS
 *  - insert
 *  - remove
 */
export class TwoWayDinamicArray<T> implements DynamicArray<T> {
  private frontItems: (T | undefined)[] = [];
  private backItems: (T | undefined)[] = [];
  private offset: number = 0;

  constructor(private minimumAllowedOffset = 10) {
  }

  /**
   * Gets the length of the array. This is a number one higher than the highest index in the array.
   */
  get length() {
    return this.backItems.length + this.frontItems.length - Math.abs(this.offset);
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
    if (this.frontItems.length) {
      return this.frontItems.pop();
    }
    const backOffset = Math.abs(this.offset);
    if (this.backItems.length === backOffset) {
      return undefined;
    }
    const item = this.backItems[backOffset - 1];
    this.backItems[backOffset - 1] = undefined;
    this.offset--;
    return item;
  }

  /**
   * Appends new elements to the end of an array, and returns the new length of the array.
   * @param items New elements to add to the array.
   */
  push(...items: T[]): number {
    items.forEach(item => this.addLast(item));
    return this.length;
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
  reverse(): TwoWayDinamicArray<T> {
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
    if (this.backItems.length) {
      return this.backItems.pop();
    }
    if (this.backItems.length === this.offset) {
      return undefined;
    }
    const item = this.backItems[this.offset];
    this.backItems[this.offset] = undefined;
    this.offset++;
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
    if (start < 0 || start >= this.length) {
      throw new Error("Start is out of bound");
    }
    if (this.offset <= 0 && (this.backItems.length + this.offset)) {
      return this.backItems.splice(start - this.offset, deleteCount || 0, ...items.reverse()) as T[];
    }
    return this.frontItems.splice(start + this.backItems.length + this.offset, deleteCount || 0, ...items) as T[];
  }

  /**
   * Inserts new elements at the start of an array, and returns the new length of the array.
   * @param items Elements to insert at the start of the array.
   */
  unshift(...items: T[]): number {
    for (let i = items.length - 1; i >= 0; i--) {
      this.addFirst(items[i]);
    }
    return this.length;
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
   * TODO
   * @returns Iterator
   */
  [Symbol.iterator](): Iterator<T> {
    let current = 0;
    const that = this;

    return {
      next(): IteratorResult<T> {
        if (current >= 0) {
          return { done: true, value: undefined as any };
        }

        return { done: false, value: that.get(0)! };
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
    return index <= this.backItems.length
      ? this.backItems[this.backItems.length - index - 1 + this.offset]
      : this.frontItems[this.backItems.length + this.offset];
  }

  /**
   * Sets the value at a specific index. If the index is beyond current size,
   * resizes the array accordingly.
   * 
   * @param index Index to set the value at.
   * @param item The item to set.
   */
  set(index: number, item: T) {
    if (index <= this.backItems.length) {
      this.backItems[this.backItems.length - index - 1 + this.offset] = item;
    } else {
      this.frontItems[this.backItems.length + this.offset] = item;
    }
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
    throw new Error("Method not implemented.");
  }

  /**
   * Removes and returns the item at the specified index, shifting elements.
   * 
   * @param index The index of the item to remove.
   * @returns The removed item.
   */
  remove(index: number): T | undefined {
    throw new Error("Method not implemented.");
  }

  /**
   * Adds an item to the end of the array.
   * @param item The item to add.
   */
  private addLast(item: T | undefined) {
    if (this.offset >= 0) {
      this.frontItems.push(item);
    } else {
      this.backItems[Math.abs(++this.offset)] = item;
    }
  }

  /**
   * Adds an item to the front of the array.
   * @param item The item to add.
   */
  private addFirst(item: T) {
    if (this.offset <= 0) {
      this.backItems.push(item);
    } else {
      this.frontItems[--this.offset] = item;
    }
  }

  private tryNormalize() {
    const absOffset = Math.abs(this.offset);
    if (absOffset < this.minimumAllowedOffset) {
      return;
    }

    if (absOffset >= this.length / 2) {
      this.normalize();
    }
  }

  private normalize() {
    if (this.offset === 0) {
      throw new Error("Not need to normalize fullfilled array");
    }

    const backCopy = [];
    const frontCopy = [];
    if (this.offset < 0) {
      const backSize = Math.floor((this.backItems.length + this.offset) / 2);
      const frontSize = this.backItems.length - backSize;
      for (let i = this.backItems.length - 1; i >= this.offset; i--) {
        let j = i - frontSize;
        if (j >= 0) {
          backCopy[j] = this.backItems[i];
        } else {
          frontCopy.push(this.backItems[i])
        }
      }
    } else {
      const backSize = Math.floor((this.frontItems.length - this.offset) / 2);
      for (let i = this.offset; i < this.frontItems.length; i++) {
        let j = backSize - 1 + i;
        if (j >= 0) {
          backCopy[j] = this.backItems[i];
        } else {
          frontCopy.push(this.backItems[i])
        }
      }
    }
    this.backItems = backCopy;
    this.frontItems = frontCopy;
    this.offset = 0;
  }
}