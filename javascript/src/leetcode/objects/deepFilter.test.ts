import { describe, expect, test } from "vitest";
import { deepFilter } from "./deepFilter";

describe("Leetcode | 2823. Deep Object Filter", () => {
  test("array filter", () => {
    const obj = [-5, -4, -3, -2, -1, 0, 1];
    const fn = (x: number) => x > 0;
    expect(deepFilter(obj, fn)).toEqual([1]);
  });

  test("nested object filter", () => {
    const obj = { "a": 1, "b": "2", "c": 3, "d": "4", "e": 5, "f": 6, "g": { "a": 1 } };
    const fn = (x: any) => typeof x === "string";
    expect(deepFilter(obj, fn)).toEqual({ "b": "2", "d": "4" });
  });

  test("nested array filter", () => {
    const obj = [-1, [-1, -1, 5, -1, 10], -1, [-1], [-5]];
    const fn = (x: number) => x > 0;
    expect(deepFilter(obj, fn)).toEqual([[5, 10]]);
  });

  test("to be undefined", () => {
    const obj = [[[[5]]]];
    const fn = (x: any) => Array.isArray(x);
    expect(deepFilter(obj, fn)).toEqual(undefined);
  });
});