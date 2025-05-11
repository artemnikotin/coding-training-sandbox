export interface SimpleIterator<T> {
  next(): T | null;
  hasNext(): boolean;
}

export interface SimpleIterable<T> {
  iterator(): SimpleIterator<T>;
}