import { describe, expect, test } from "vitest";

describe("Javascript/DataTypes/Objects", () => {
  test("Object.freeze", () => {
    const obj = {
      a: 1,
      b: {
        c: 2,
      }
    };
    Object.freeze(obj);

    /* Nested objects are not frozen */
    obj.b.d = 3;
    expect(obj.b.d).toBe(3);

    /* Change property */
    expect(() => obj.a = 2).toThrow();
    /* Add property */
    expect(() => obj.d = 10).toThrow();
  });
});