import {test, expect, describe} from 'vitest';

describe('Weired/Array', () => {
  test('Empty array and filter method', () => {
    const arr = new Array(10);

    expect(arr.length).toBe(10);
    expect(arr.filter(() => true).length).toBe(0); // weird
  });
  
  test('Empty array and map method', () => {
    const arr = new Array(5);
    arr[1] = null;
    arr[3] = undefined;

    expect(arr.map(el => `(${el})`).join(',')).toBe(",(null),,(undefined),"); // weird
  });

  test('Some array methods cannot find NaN, while others can', () => {
    const arr = [2, 4, NaN, 12];

    expect(arr.includes(NaN)).toBe(true);
    expect(arr.indexOf(NaN)).toBe(-1); // weird
  });

  test("Default JavaScript sort method coerces types to string.", () => {
    const arr = [1, 9, 10];
    expect(arr.sort()).toEqual([1, 10, 9]); // weird
  });
});