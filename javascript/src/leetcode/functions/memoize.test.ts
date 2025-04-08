import { describe, expect, test, vi } from 'vitest';

import { memoize } from './memoize';

describe("Leetcode | 2623. Memoize", () => {
  test('memoize no arguments function', () => {
    const getFive = vi.fn(() => 5);
    const wrappedFn = memoize(getFive);
    expect(wrappedFn()).toBe(5);
    expect(wrappedFn()).toBe(5);
    expect(getFive).toHaveBeenCalledTimes(1);
  });

  test('memoize sum function', () => {
    const sum = vi.fn((a: number, b: number) => a + b);
    const wrappedFn = memoize(sum);
    expect(wrappedFn(1, 3)).toBe(4);
    expect(wrappedFn(3, 1)).toBe(4);
    expect(wrappedFn(1, 3)).toBe(4);
    expect(wrappedFn(3, 1)).toBe(4);
    expect(sum).toHaveBeenCalledTimes(2);
  });
});