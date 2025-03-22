import {test, expect, describe} from 'vitest';

describe('array weird behaviour', () => {
  test('filer on empty array', () => {
    const arr = new Array(10);
    expect(arr.filter(() => true).length).toBe(0); // WTF?
  });
  
  test('map on partly empty array', () => {
    const arr = new Array(5);
    arr[1] = null;
    arr[3] = undefined;
    expect(arr.map(el => `(${el})`).join(',')).toBe(",(null),,(undefined),"); // WTF?
  });
});