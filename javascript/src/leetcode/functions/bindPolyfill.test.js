import { describe, test, expect } from "vitest";

import "./bindPolyfill";

const fn = function () {
  return this?.value;
}

describe("Leetcode | 2754. Bind Function to Context", () => {
  test('polyfill bind function with passed contex', () => {
    const context = {
      value: 3,
    }
    const wrappedFn = fn.bindPolyfill(context);
    expect(fn()).toBe(undefined);
    expect(wrappedFn()).toBe(3);
  });

  test('polyfill with sealed objects', () => {
    const context = {
      value: 3,
    }
    const wrappedFn = fn.bindPolyfill(context);
    expect(fn()).toBe(undefined);
    expect(wrappedFn()).toBe(3);
  });
});