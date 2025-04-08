import { describe, expect, test } from 'vitest';
import './callPolyfill';

const fn = function () {
  return this?.value;
}

describe("Leetcode | 2693. Call Function with Custom Context", () => {
  test('polyfill call function with passed contex', () => {
    const context = {
      value: 3,
    }
    expect(fn()).toBe(undefined);
    expect(fn.callPolyfill(context)).toBe(3);
  });

  test('polyfill with sealed objects', () => {
    const context = {
      value: 3,
    }
    Object.seal(context);
    expect(fn.callPolyfill(context)).toBe(3);
  });
});