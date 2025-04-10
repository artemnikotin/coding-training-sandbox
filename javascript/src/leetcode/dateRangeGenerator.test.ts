import { describe, expect, test } from "vitest";
import { dateRangeGenerator } from "./dateRangeGenerator";

describe("Leetcode | 2777 - Date Range Generator", () => {
  test("one day step", () => {
    const g = dateRangeGenerator("2023-04-01", "2023-04-04", 1);
    expect(Array.from(g)).toEqual(["2023-04-01", "2023-04-02", "2023-04-03", "2023-04-04"]);
  });

  test("several days step", () => {
    const g = dateRangeGenerator("2023-04-10", "2023-04-20", 3);
    expect(Array.from(g)).toEqual(["2023-04-10", "2023-04-13", "2023-04-16", "2023-04-19"]);
  });

  test("same dates for start and end", () => {
    const g = dateRangeGenerator("2023-04-10", "2023-04-10", 6);
    expect(Array.from(g)).toEqual(["2023-04-10"]);
  });

  test("start after the end", () => {
    const g = dateRangeGenerator("2023-04-11", "2023-04-10", 1);
    expect(Array.from(g)).toEqual([]);
  });
});