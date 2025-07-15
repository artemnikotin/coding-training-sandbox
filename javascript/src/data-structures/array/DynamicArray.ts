import { RemoveIndexSignature } from "../../utils/utilityTypes";

export interface DynamicArray<T> extends Omit<
  RemoveIndexSignature<Array<T>>,
  'concat' | 'reverse' | typeof Symbol.unscopables | typeof Symbol.iterator | 'sort' | // TODO
  'every' | 'some' | 'forEach' | 'map' | 'filter' | 'reduce' | 'reduceRight' | // TODO
  'find' | 'findIndex' | 'fill' | 'copyWithin' | 'entries' | 'keys' | 'values' | // TODO
  'includes' | 'flatMap' | 'flat' | 'upperBound' // TODO
> {

  /**
   * Reverses the elements in an array in place.
   * This method mutates the array and returns a reference to the same array.
   */
  reverse(): DynamicArray<T>;

  /** Iterator */
  [Symbol.iterator](): Iterator<T>;

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
   * Inserts an item at the specified index, shifting elements as necessary.
   * @param index The index to insert the item at.
   * @param item The item to insert.
   */
  insert(index: number, item: T): void;

  /**
   * Removes and returns the item at the specified index, shifting elements.
   * @param index The index of the item to remove.
   * @returns The removed item.
   */
  remove(index: number): T | undefined;
}