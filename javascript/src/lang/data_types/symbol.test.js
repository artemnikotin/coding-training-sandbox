import { describe, test, expect } from "vitest";

describe("Javascript/DataTypes/Symbol", () => {
  test("Symbol.toStringTag", () => {
    const obj = {
      get [Symbol.toStringTag]() {
        return "Super";
      }
    }

    expect(`${obj}`).toBe("[object Super]");
  });
});