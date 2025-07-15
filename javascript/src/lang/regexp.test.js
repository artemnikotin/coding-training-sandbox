import { describe, test, expect } from "vitest";

describe("Javascript/Regexp", () => {
  test("Test uppercase letter", () => {
    expect((/\p{Lu}/u).test("Ф")).toBe(true); // Lu - uppercase letter
    expect((/\p{Lu}/u).test("ф")).toBe(false);
  });
});