import { describe, expect, test } from "vitest";

import "./upperBound";

describe("Leetcode | 2774. Array Upper Bound", () => {
  test("target is the first element", () => {
    expect([1, 4, 5].upperBound(1)).toBe(0);
  });

  test("no dublicates", () => {
    expect([3, 4, 5].upperBound(5)).toBe(2);
  });

  test("no target in array", () => {
    expect([1, 4, 5].upperBound(2)).toBe(-1);
  });

  test("array with duplicates", () => {
    expect([3, 4, 6, 6, 6, 6, 7].upperBound(6)).toBe(5);
  });
});