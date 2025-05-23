import { describe, it, expect, test } from 'vitest'
import { PriorityQueue } from './PriorityQueue' // Adjust path as needed

describe('DataStructure/Queue | PriorityQueue', () => {
  /**
   * Higher numbers have higher priority
   */
  const compareFn = (a: number, b: number) => a - b;

  it('should be iterable', () => {
    const pq = new PriorityQueue(compareFn);
    pq.enqueue(5);
    pq.enqueue(1);
    pq.enqueue(3);

    expect([...pq].sort(compareFn)).toEqual([1, 3, 5]);
    expect(pq.size()).toBe(3);
  });

  it('should dequeue elements in priority order', () => {
    const pq = new PriorityQueue(compareFn);
    pq.enqueue(5);
    pq.enqueue(1);
    pq.enqueue(3);

    expect(pq.dequeue()).toBe(1);
    expect(pq.dequeue()).toBe(3);
    expect(pq.dequeue()).toBe(5);
    expect(pq.dequeue()).toBeUndefined();
  });

  it('should peek at the highest priority element without removing', () => {
    const pq = new PriorityQueue(compareFn);
    pq.enqueue(5);
    pq.enqueue(1);
    pq.enqueue(3);

    expect(pq.peek()).toBe(1);
    expect(pq.size()).toBe(3);
  });

  it('should handle removal by index', () => {
    const pq = new PriorityQueue(compareFn);
    pq.enqueue(10);
    pq.enqueue(20);
    pq.enqueue(30);

    pq.remove(1);
    expect(pq.size()).toBe(2);
    expect(pq.peek()).toBe(10);
  });

  it('should return undefined when removing invalid index', () => {
    const pq = new PriorityQueue(compareFn);
    pq.enqueue(10);
    expect(pq.remove(5)).toBeUndefined();
    expect(pq.remove(-1)).toBeUndefined();
  });

  it('should be empty when created', () => {
    const pq = new PriorityQueue(compareFn);
    expect(pq.isEmpty()).toBe(true);
  });

  // TODO: Add reorder(), reorder(i) tests
});