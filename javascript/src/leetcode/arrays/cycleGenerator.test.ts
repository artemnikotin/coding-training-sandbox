import { describe, expect, test } from "vitest";
import { cycleGenerator } from "./cycleGenerator";

describe("Leetcode | 2757. Generate Circular Array Values", () => {
  test("example 1", () => {
    const arr = [1, 2, 3, 4, 5];
    const gen = cycleGenerator(arr, 0);

    expect(gen.next().value).toBe(1);
    expect(gen.next(1).value).toBe(2);
    expect(gen.next(2).value).toBe(4);
    expect(gen.next(6).value).toBe(5);
  });

  test("example 2", () => {
    const arr = [10, 11, 12, 13, 14, 15];
    const gen = cycleGenerator(arr, 1);

    expect(gen.next().value).toBe(11);
    expect(gen.next(1).value).toBe(12);
    expect(gen.next(4).value).toBe(10);
    expect(gen.next(0).value).toBe(10);
    expect(gen.next(-1).value).toBe(15);
    expect(gen.next(-3).value).toBe(12);
  });

  test("example 3", () => {
    const arr = [2, 4, 6, 7, 8, 10];
    const gen = cycleGenerator(arr, 3);

    expect(gen.next().value).toBe(7);
    expect(gen.next(-4).value).toBe(10);
    expect(gen.next(5).value).toBe(8);
    expect(gen.next(-3).value).toBe(4);
    expect(gen.next(10).value).toBe(10);
  });
});