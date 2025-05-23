import { describe, expect, test } from "vitest";
import { binarySearch } from "./binarySearch";

const compareFn = (a: number, b: number) => a - b;

describe('Common/Array | Binary Search', () => {
  test("empty array", () => {
    expect(binarySearch([], 5, compareFn)).toBe(-1);
  });

  test("one element array", () => {
    expect(binarySearch([1], 5, compareFn)).toBe(-1);
    expect(binarySearch([5], 5, compareFn)).toBe(0);
  });

  test("multy elements array", () => {
    const array = [0, 1, 2, 3, 4];
    array.forEach(el => {
      expect(binarySearch(array, el, compareFn)).toBe(el);
    })
    expect(binarySearch(array, 5, compareFn)).toBe(-1);
  });
});