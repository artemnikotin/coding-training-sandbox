import { describe, expect, test, vi, beforeEach, afterEach } from 'vitest';
import { memoizeMap, memoizeTrie } from './memoize-ii';

describe.each([
  ['memoizeTrie', memoizeTrie],
  ['memoizeMap', memoizeMap],
])('Leetcode | memoize-ii | %s', (_, memoize) => {
  test('memoize no arguments functions', () => {
    const fn1 = vi.fn(() => 5);
    const fn2 = vi.fn(() => 6);

    const wrappedFn1 = memoize(fn1);
    const wrappedFn2 = memoize(fn2);

    expect(wrappedFn1()).toBe(5);
    expect(wrappedFn1()).toBe(5);
    expect(wrappedFn2()).toBe(6);
    expect(wrappedFn2()).toBe(6);

    expect(fn1).toHaveBeenCalledTimes(1);
    expect(fn2).toHaveBeenCalledTimes(1);
  });

  test('memoize multi arguments function', () => {
    const originalFn = vi.fn((...params) => params.map(param => param.toString()).join());
    const wrappedFn = memoize(originalFn);

    const objArg = {};
    const arrArg: string[] = [];

    expect(wrappedFn(1, objArg, arrArg)).toBe('1,[object Object],');
    expect(wrappedFn(1, objArg, arrArg)).toBe('1,[object Object],');
    expect(originalFn).toHaveBeenCalledTimes(1);

    expect(wrappedFn(1, arrArg, objArg)).toBe('1,,[object Object]');
    expect(wrappedFn(1, arrArg, objArg)).toBe('1,,[object Object]');
    expect(originalFn).toHaveBeenCalledTimes(2);
  });
});