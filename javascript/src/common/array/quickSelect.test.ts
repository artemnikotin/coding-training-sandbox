import { describe, it, expect, test } from 'vitest';
import { quickSelectInPlace } from './quickSelect';

describe('Common/Array | QuickSelect', () => {
  test('quickSelectInPlace returns the smallest element when k = 0', () => {
    const arr = [5, 3, 8, 1, 2];
    const result = quickSelectInPlace(arr, 0, (a, b) => a - b);
    expect(result).toBe(1);
  });

  test('quickSelectInPlace returns the largest element when k = arr.length - 1', () => {
    const arr = [5, 3, 8, 1, 2];
    const result = quickSelectInPlace(arr, arr.length - 1, (a, b) => a - b);
    expect(result).toBe(8);
  });

  test('quickSelectInPlace returns the median when k = Math.floor(arr.length / 2)', () => {
    const arr = [5, 3, 8, 1, 2];
    const result = quickSelectInPlace(arr, 2, (a, b) => a - b);
    expect(result).toBe(3);
  });

  test('quickSelectInPlace works with duplicate values', () => {
    const arr = [4, 2, 5, 2, 3, 2];
    const result = quickSelectInPlace(arr, 2, (a, b) => a - b);
    expect(result).toBe(2);
  });

  test('quickSelectInPlace modify array so all elements less than the arr[k] are to its left', () => {
    const arr = [4, 2, 5, 2, 3, 2];
    const k = 2;
    const result = quickSelectInPlace(arr, k, (a, b) => a - b);
    for (let i = 0; i < k; i++) {
      expect(arr[i] <= result).toBe(true);
    }
  });
});
