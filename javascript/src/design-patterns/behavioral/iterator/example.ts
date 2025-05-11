import { SimpleIterable, SimpleIterator } from "./iterator";

export class Collection<T> implements SimpleIterable<T> {
  private data: T[] = [];

  add(item: T) {
    this.data.push(item);
  }

  iterator() {
    return new ArrayIterator(this.data);
  }
}

class ArrayIterator<T> implements SimpleIterator<T> {
  private i = 0;
  constructor(private array: T[]) {
  }

  next(): T | null {
    if (this.hasNext()) {
      return this.array[this.i++];
    }
    return null;
  }

  hasNext(): boolean {
    return this.i < this.array.length;
  }
}