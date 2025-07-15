import { describe, expect, test } from 'vitest';

import { areDeeplyEqual } from './deepEqual';

describe("Leetcode | 2628. JSON Deep Equal", () => {
  test('primetives are equal', () => {
    const primetives = [
      1,
      false,
      'str',
      null,
      undefined,
    ];
    for (let value of primetives) {
      expect(areDeeplyEqual(value, value)).toBeTruthy();
    }
  });

  test('empty objects are equal', () => {
    expect(areDeeplyEqual({}, {})).toBeTruthy();
  });

  test('similar objects but different order are equal', () => {
    expect(areDeeplyEqual({ a: 1, b: 2 }, { b: 2, a: 1 })).toBeTruthy();
  });

  test('nested objects', () => {
    const obj1 = {
      a: {
        b: {
          c: [10, 9]
        }
      }
    };
    const obj2 = {
      a: {
        b: {
          c: [10, 9]
        }
      }
    };
    expect(areDeeplyEqual(obj1, obj2)).toBeTruthy();
  });

  test('empty array and object are not equal', () => {
    expect(areDeeplyEqual([], {})).not.toBeTruthy();
  });
});