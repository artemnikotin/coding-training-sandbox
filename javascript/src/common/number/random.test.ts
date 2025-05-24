import { describe, expect, test } from "vitest";
import { randomInt } from "./random";

describe("Common/Number | Random", () => {
  test("Random value is between the minimum (inclusive) and the maximum (exclusive)", () => {
    const random = randomInt(1, 10);
    expect(1 <= random && random < 10).toBe(true);
  });

  test("Random value is integr", () => {
    const random = randomInt(1, 10);
    expect(Number.isInteger(random)).toBe(true);
  });
});