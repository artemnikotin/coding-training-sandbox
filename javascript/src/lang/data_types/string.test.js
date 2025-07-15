import { describe, expect, test } from "vitest";

describe("Javascript/DataTypes/String", () => {
  test("string.normalize", () => {
    expect("й").not.toBe("и\u{306}");
    expect("й").toBe("и\u{306}".normalize());

    expect("й".length).toBe(1);
    expect("й".normalize("NFD").length).toBe(2);
  });

  test("code points vs code units", () => {
    expect("\u{1F47B}").toBe("👻"); // code point
    expect("\uD83D\uDC7B").toBe("👻"); // code units
    expect("👻".length).toBe(2);
  });
});