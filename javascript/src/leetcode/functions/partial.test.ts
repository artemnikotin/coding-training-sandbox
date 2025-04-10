import { describe, expect, test } from "vitest";
import { partial } from "./partial";

describe("Leetcode | 2797. Partial Function with Placeholders", () => {
  test("no placeholders '_' in args", () => {
    const fn = (...args: any[]) => args;
    const args = [2, 4, 6];
    const restArgs = [8, 10];
    const partialFn = partial(fn, args);

    expect(partialFn(...restArgs)).toEqual([2, 4, 6, 8, 10]);
  });

  test("placeholders '_' are replaced with values from the restArgs", () => {
    const fn = (...args: any[]) => args;
    const args = [1, 2, "_", 4, "_", 6];
    const restArgs = [3, 5];
    const partialFn = partial(fn, args);

    expect(partialFn(...restArgs)).toEqual([1, 2, 3, 4, 5, 6]);
  });

  test("placeholders '_' are replaced with values from the restArgs and added at the end of args", () => {
    const fn = (a: number, b: number, c: number) => b + a - c;
    const args = ["_", 5];
    const restArgs = [5, 20];
    const partialFn = partial(fn, args);

    expect(partialFn(...restArgs)).toBe(-10);
  });
});