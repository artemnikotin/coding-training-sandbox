import { beforeEach, describe, expect, test } from "vitest";
import { MapSum } from "./MapSum";

describe("Leetcode | 677. Map Sum Pairs", () => {
  let map: MapSum;
  beforeEach(() => {
    map = new MapSum();
  });

  test("Insert prefix as new word", () => {
    map.insert("apple", 3);
    expect(map.sum("ap")).toBe(3);
    map.insert("app", 2);
    expect(map.sum("ap")).toBe(5);
  });

  test("Update word score", () => {
    map.insert("apple", 2);
    expect(map.sum("ap")).toBe(2);
    map.insert("apple", 3);
    expect(map.sum("ap")).toBe(3);
    map.insert("apple", 2);
    expect(map.sum("ap")).toBe(2);
  });

  test("Update prefix word score", () => {
    map.insert("apple", 2);
    expect(map.sum("ap")).toBe(2);
    map.insert("app", 3);
    expect(map.sum("ap")).toBe(5);
    map.insert("app", 2);
    expect(map.sum("ap")).toBe(4);
  });
});