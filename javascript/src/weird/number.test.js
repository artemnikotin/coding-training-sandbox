import {test, expect, describe} from 'vitest';

describe('Weired/Number', () => {
  test('isNaN and Number.isNaN difference', () => {
    expect(Number.isNaN("blabla")).toBe(false);

    /* type is coerced to a number */
    expect(isNaN("blabla")).toBe(true);
  });

  test("Numbers around the magnitude of Number.MAX_SAFE_INTEGER and Number.isInteger", () => {
    const num = 4500000000000000.1;
    expect(num < Number.MAX_SAFE_INTEGER).toBe(true);
    
    expect(Number.isInteger(4500000000000000.1)).toBe(true); // weird
    expect(Number.isInteger(4500000000000000.5)).toBe(false); // more weird
  })
});