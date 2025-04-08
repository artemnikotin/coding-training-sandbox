import { describe, expect, test } from "vitest";
import { join } from "./join";

describe("Leetcode | 2722. Join Two Arrays by ID", () => {
  test("no duplicate ids", () => {
    const arr1 = [
      { "id": 1, "x": 1 },
      { "id": 2, "x": 9 }
    ];
    const arr2 = [
      { "id": 3, "x": 5 }
    ];
    expect(join(arr1, arr2)).toEqual([
      { "id": 1, "x": 1 },
      { "id": 2, "x": 9 },
      { "id": 3, "x": 5 }
    ]);
  });

  test("some element with the same id", () => {
    const arr1 = [
      { "id": 1, "x": 2, "y": 3 },
      { "id": 2, "x": 3, "y": 6 }
    ];
    const arr2 = [
      { "id": 2, "x": 10, "y": 20 },
      { "id": 3, "x": 0, "y": 0 }
    ];
    expect(join(arr1, arr2)).toEqual([
      { "id": 1, "x": 2, "y": 3 },
      { "id": 2, "x": 10, "y": 20 },
      { "id": 3, "x": 0, "y": 0 }
    ]);
  });

  test("two objects are merged together", () => {
    const arr1 = [
      { "id": 1, "b": { "b": 94 }, "v": [4, 3], "y": 48 }
    ];
    const arr2 = [
      { "id": 1, "b": { "c": 84 }, "v": [1, 3] }
    ];
    expect(join(arr1, arr2)).toEqual([
      { "id": 1, "b": { "c": 84 }, "v": [1, 3], "y": 48 }
    ]);
  });

  test("same id with different order", () => {
    const arr1 = [
      { "id": 1, "x": 1 },
      { "id": 2, "x": 9 },
    ];
    const arr2 = [
      { "id": 3, "x": 5 },
      { "id": 1, "x": 1 },
    ];
    expect(join(arr1, arr2)).toEqual([
      { "id": 1, "x": 1 },
      { "id": 2, "x": 9 },
      { "id": 3, "x": 5 },
    ]);
  })
});