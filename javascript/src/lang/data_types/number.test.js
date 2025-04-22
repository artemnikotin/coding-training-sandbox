import { describe, expect, test } from "vitest";

describe("Javascript/DataTypes/Number", () => {
  test("NaN", () => {
    /* Failed number conversion */
    expect(Number("blabla")).toBe(NaN);
    /* Math operation where the result is not a real number */
    expect(Math.sqrt(-1)).toBe(NaN);
    /* Indeterminate */
    expect(Infinity / Infinity).toBe(NaN);
    /* NaN is contagious */
    expect(7 * "blabla").toBe(NaN);
    /* An invalid value is to be represented as a number */
    expect((new Date("blabla")).getTime()).toBe(NaN);
    expect("".charCodeAt(1)).toBe(NaN);

    /* Testing is NaN */
    expect(NaN === NaN).toBe(false);

    /* isNaN and Number.isNaN difference */
    expect(Number.isNaN("blabla")).toBe(false);
    /* type is coerced to a number */
    expect(isNaN("blabla")).toBe(true);
  });

  test("Number function and constructor difference", () => {
    expect(Number(10)).toBe(Number(10));
    expect(new Number(10)).not.toBe(new Number(10));

    const objTen = new Object(10);
    const primeTen = Object(10);

    expect(objTen + 5).toBe(primeTen + 5);
  });
});