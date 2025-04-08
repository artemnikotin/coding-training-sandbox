import { describe, expect, test } from "vitest";

import { deepMerge } from "./deepMerge";

describe("Leetcode | 2755. Deep Merge of Two Objects", () => {
  test("one level merge", () => {
    const obj1 = { "a": 1, "c": 3 };
    const obj2 = { "a": 2, "b": 2 };
    const exp = { "a": 2, "c": 3, "b": 2 };

    expect(deepMerge(obj1, obj2)).toEqual(exp);
  });

  test("arrays", () => {
    const obj1 = [{}, 2, 3];
    const obj2 = [[], 5];
    const exp = [[], 5, 3];

    expect(deepMerge(obj1, obj2)).toEqual(exp);
  });

  test("nested objects", () => {
    const obj1 = { "a": 1, "b": { "c": [1, [2, 7], 5], "d": 2 } };
    const obj2 = { "a": 1, "b": { "c": [6, [6], [9]], "e": 3 } };
    const exp = { "a": 1, "b": { "c": [6, [6, 7], [9]], "d": 2, "e": 3 } };

    expect(deepMerge(obj1, obj2)).toEqual(exp);
  });

  test("primitives", () => {
    const obj1 = true;
    const obj2 = null;
    const exp = null;

    expect(deepMerge(obj1, obj2)).toEqual(exp);
  });
})