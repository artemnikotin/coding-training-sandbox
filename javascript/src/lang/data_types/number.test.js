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

  test("Right shift cuts number to 32 bit", () => {
    const num = 0b111010;
    const shiftedNum = num >> 2;

    expect(num).toBe(58);
    expect(num.toString(2)).toBe("111010");
    expect(shiftedNum).toBe(14);
    expect(shiftedNum.toString(2)).toBe("1110");

    const max32bitInteger = Math.pow(2, 31) - 1;
    
    expect(max32bitInteger).toBe(2_147_483_647);
    expect(max32bitInteger >> 1).toBe(1_073_741_823);
    expect((max32bitInteger + 1) >> 1).toBe(-1_073_741_824);
  });
});