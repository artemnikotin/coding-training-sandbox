export class PriorityQueue<T> {
  private readonly items: T[] = [];

  /**
   * Constructs a priority queue with the given comparator.
   * @param compareFn the comparator to determine priority order
   */
  constructor(private compareFn: (a: T, b: T) => number) {
  }

  /**
   * Adds an element to the priority queue.
   * @param x the element to add
   */
  enqueue(x: T) {
    this.items.push(x); // Add to the end
    this.swim(this.size() - 1); // Restore heap order by swimming the new element up
  }

  /**
   * Removes and returns the highest priority element.
   * @returns the highest priority element or undefined if queue is empty
   */
  dequeue() {
    if (this.isEmpty()) {
      return undefined;
    }
    const peak = this.items[0]; // Get the root (highest priority element)
    const last = this.items.pop()!; // Remove the last element
    if (this.size() > 0) {
      this.items[0] = last; // Move last element to root
      this.sink(0); // Restore heap order by sinking the new root down
    }
    return peak;
  }

  /**
   * Returns (without removing) the highest priority element.
   * @returns the highest priority element or undefined if queue is empty
   */
  peek() {
    return this.items[0];
  }

  remove(index: number) {
    if (index < 0 || index >= this.size()) {
      return undefined;
    }
    if (index === this.size() - 1) {
      return this.items.pop();
    }
    const value = this.items[index];
    const last = this.items.pop()!;
    this.items[index] = last; // Replace removed element with last element

    // Restore heap order by moving the replacement element up or down as needed
    if (this.isLowerPriority(last, value)) {
      this.sink(index);
    } else {
      this.swim(index);
    }

    return value;
  }

  /**
   * Get the element at the specified index.
   * @param index the index of the element
   * @returns the element
   */
  get(index: number) {
    return this.items[index];
  }

  /**
   * Checks if the queue is empty.
   * @returns true if empty, false otherwise
   */
  isEmpty() {
    return this.items.length === 0;
  }

  /**
   * Returns the number of elements in the queue.
   * @returns the number of elements
   */
  size() {
    return this.items.length;
  }

  /**
   * Reorders the entire heap (heapify operation).
   */
  reorder(): void;

  /**
   * Reorders the heap after the element at the specified index may have changed.
   * @param index the index of the element that may have changed
   */
  reorder(index: number): void;

  /**
   * Reorder implementation
   */
  reorder(...args: any[]): void {
    if (args.length === 0) {
      this.heapyfy();
      return;
    }
    const index = args[0];
    this.sink(index);
    this.swim(index)
  }

  /**
   * Get iterator
   */
  *[Symbol.iterator]() {
    yield* this.items;
  }

  /**
   * Moves the element at the specified index up the heap to restore heap order.
   * @param current the index of the element to swim up
   */
  private swim(current: number) {
    while (current > 0) {
      const parent = (current - 1) >> 1; // Calculate parent index
      if (!this.isLowerPriorityByIndex(parent, current)) { // If parent has higher priority, stop
        break;
      }
      this.exchange(parent, current);
      current = parent;
    }
  }

  /**
   * Moves the element at the specified index down the heap to restore heap order.
   * @param current the index of the element to sink down
   */
  private sink(current: number) {
    const half = this.size() >> 1;
    while (current < half) { // Only need to sink non-leaf nodes
      let left = 2 * current + 1;
      let right = left + 1;

      // Choose the child with higher priority
      if (right < this.size() && this.isLowerPriorityByIndex(left, right)) {
        left = right;
      }

      if (!this.isLowerPriorityByIndex(current, left)) { // If current has higher priority than children, stop
        break;
      }

      this.exchange(current, left); // Swap with higher priority child
      current = left; // Move to child position
    }
  }

  /**
   * Builds a heap from the unordered array (heapify operation).
   */
  private heapyfy() {
    let i = this.size() >> 1 - 1; // Start from the last non-leaf node
    while (i >= 0) {
      this.sink(i); // Sink each non-leaf node
      i--;
    }
  }

  /**
   * Compares two elements. Higher number has lower priority.
   * @param first first element
   * @param second second element
   * @return true if first element has lower priority than second one
   */
  private isLowerPriority(first: T, second: T) {
    return this.compareFn(first, second) > 0;
  }

  /**
   * Compares two elements at the given indices. Higher number has lower priority.
   * @param i first element index
   * @param j second element index
   * @return true if element at i has lower priority than element at j
   */
  private isLowerPriorityByIndex(i: number, j: number) {
    return this.isLowerPriority(this.items[i], this.items[j]);
  }

  /**
   * Swaps two elements in the heap.
   * @param i first element index
   * @param j second element index
   */
  private exchange(i: number, j: number) {
    const swap = this.items[i];
    this.items[i] = this.items[j];
    this.items[j] = swap;
  }
}