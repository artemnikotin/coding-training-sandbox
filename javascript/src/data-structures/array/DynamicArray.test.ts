import { describe, it, expect, beforeEach } from 'vitest';
import { SlidingDynamicArray } from './SlidingDynamicArray'; // Update the path if needed

describe('DynamicArray', () => {
  let arr: SlidingDynamicArray<number>;

  beforeEach(() => {
    arr = new SlidingDynamicArray<number>();
  });

  it('should start empty', () => {
    expect(arr.length).toBe(0);
    expect(arr.length).toBe(0);
  });

  it('should push and pop items', () => {
    arr.push(1);
    arr.push(2);
    expect(arr.length).toBe(2);
    expect(arr.pop()).toBe(2);
    expect(arr.pop()).toBe(1);
    expect(arr.pop()).toBeUndefined();
    expect(arr.length).toBe(0);
  });

  it('should unshift and shift items', () => {
    arr.unshift(1);
    arr.unshift(2);
    expect(arr.length).toBe(2);
    expect(arr.shift()).toBe(2);
    expect(arr.shift()).toBe(1);
    expect(arr.shift()).toBeUndefined();
    expect(arr.length).toBe(0);
  });

  it('should support get and set operations', () => {
    arr.push(10);
    arr.push(20);
    arr.push(30);
    expect(arr.get(1)).toBe(20);
    arr.set(1, 25);
    expect(arr.get(1)).toBe(25);
    expect(arr.at(2)).toBe(30);
  });

  it('should insert and remove items correctly', () => {
    arr.push(1);
    arr.push(2);
    arr.push(3);
    arr.insert(1, 99);
    expect(arr.get(1)).toBe(99);
    expect(arr.length).toBe(4);

    const removed = arr.remove(1);
    expect(removed).toBe(99);
    expect(arr.get(1)).toBe(2);
    expect(arr.length).toBe(3);
  });

  it('should throw on invalid insert/remove index', () => {
    expect(() => arr.insert(-1, 1)).toThrow();
    expect(() => arr.remove(1)).toThrow();
  });

  it('should resize dynamically when capacity is exceeded', () => {
    for (let i = 0; i < 20; i++) {
      arr.push(i);
    }
    expect(arr.length).toBe(20);
    for (let i = 19; i >= 0; i--) {
      expect(arr.pop()).toBe(i);
    }
    expect(arr.length).toBe(0);
  });
});