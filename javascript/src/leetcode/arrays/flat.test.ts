import { describe, expect, test } from "vitest";
import { flat } from "./flat";

describe("Leetcode | 2625. Flatten Deeply Nested Array", () => {
  const arr = [1, 2, 3, /*2*/[4, 5, 6], /*2*/[7, 8, /*3*/[9, 10, 11], 12], /*2*/[13, 14, 15]];

  test('depth is zero', () => {
    expect(flat(arr, 0)).toEqual(arr);
  });

  test('flat to one level', () => {
    expect(flat(arr, 1)).toEqual(arr.flat(1));
  });

  test('flat to array depth', () => {
    expect(flat(arr, 2)).toEqual(arr.flat(2));
  });

  test('flat to more then array depth', () => {
    expect(flat(arr, 3)).toEqual(arr.flat(3));
  });
});