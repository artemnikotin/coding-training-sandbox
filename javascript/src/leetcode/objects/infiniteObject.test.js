import { describe, expect, test } from "vitest";

import { createInfiniteObject } from "./infiniteObject";

const obj = createInfiniteObject();

describe("Leetcode | 2690. Infinite Method Object", () => {
  test("call with valid method name", () => {
    expect(obj.abc123()).toBe("abc123");
    expect(obj["abc123"]()).toBe("abc123");
  });

  test("call with invalid method name", () => {
    expect(obj[".-qw73n|^2It"]()).toBe(".-qw73n|^2It");
  });
});