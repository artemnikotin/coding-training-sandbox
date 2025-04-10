import { describe, expect, test } from "vitest";
import { undefinedToNull } from "./undefinedToNull";

describe("Leetcode | 2775 - Undefined to Null", () => {
  test("nested objects", () => {
    const obj = { "a": undefined, "b": 3, "c": null };
    const exp = { "a": null, "b": 3, "c": null };
    expect(undefinedToNull(obj)).toEqual(exp);
  });

  test("nested arrays", () => {
    const obj = [1, "2", undefined, [], [undefined, null], null];
    const exp = [1, "2", null, [], [null, null], null];
    expect(undefinedToNull(obj)).toEqual(exp);
  });

  test("nested mix", () => {
    const obj = { "a": undefined, "b": ["a", undefined], "c": [{ a: undefined }] };
    const exp = { "a": null, "b": ["a", null], "c": [{ a: null }] };
    expect(undefinedToNull(obj)).toEqual(exp);
  });
});