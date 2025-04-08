import { describe, expect, test } from 'vitest';
import { jsonToMatrix } from './jsonToMatrix';

describe("Leetcode | 2675. Array of Objects to Matrix", () => {
  test('objects with different keys order', () => {
    const input = [
      { a: 1, b: 1 },
      { b: 2, a: 2 },
    ];

    const output = [
      ['a', 'b'],
      [1, 1],
      [2, 2],
    ];

    expect(jsonToMatrix(input)).toEqual(output);
  });

  test('object with different keys', () => {
    const input = [
      { a: 1, b: 1 },
      { c: 2, d: 2 },
      {},
    ];

    const output = [
      ['a', 'b', 'c', 'd'],
      [1, 1, '', ''],
      ['', '', 2, 2],
      ['', '', '', ''],
    ];

    expect(jsonToMatrix(input)).toEqual(output);
  });

  test('arrays', () => {
    const input = [
      [1, 2, 3],
      [4, 5, 6],
    ];

    const output = [
      ['0', '1', '2'],
      [1, 2, 3],
      [4, 5, 6],
    ];

    expect(jsonToMatrix(input)).toEqual(output);
  });

  test('arrays of objects', () => {
    const input = [
      [{ a: 1, b: 1, c: 1, d: 1 }],
      [{ a: 2, b: 2, c: 2, d: 2 }],
      [],
    ];

    const output = [
      ['0.a', '0.b', '0.c', '0.d'],
      [1, 1, 1, 1],
      [2, 2, 2, 2],
      ['', '', '', ''],
    ];

    expect(jsonToMatrix(input)).toEqual(output);
  });
});