import { describe, expect, test } from "vitest";

import "./replicate";

describe("Leetcode | 2796. Repeat String", () => {
  test("replicate 0 times", () => {
    expect("hello".replicate(0)).toBe("");
  });

  test("replicate 1 time", () => {
    expect("code".replicate(1)).toBe("code");
  });

  test("replicate several times", () => {
    expect("js".replicate(3)).toBe("jsjsjs");
  });
});