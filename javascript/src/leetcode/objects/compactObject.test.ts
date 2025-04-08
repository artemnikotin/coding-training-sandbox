import { describe, expect, test } from "vitest";
import { compactObject } from "./compactObject";

describe("Leetcode | 2705. Compact Object", () => {
  test("compact array", () => {
    const obj = [null, 0, false, 1];
    expect(compactObject(obj)).toEqual([1]);
  });

  test("compact object", () => {
    const obj = { "a": null, "b": [false, 1] };
    expect(compactObject(obj)).toEqual({ "b": [1] });
  });

  test("compact nested array", () => {
    const obj = [null, 0, 5, [0], [false, 16]];
    expect(compactObject(obj)).toEqual([5, [], [16]]);
  });
});