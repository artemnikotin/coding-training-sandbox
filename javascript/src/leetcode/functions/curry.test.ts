import { describe, expect, test } from 'vitest';

import { curry } from './curry';

describe("Leetcode | 2632. Curry", () => {
  test("curry no argument function", () => {
    const foo = () => 5;
    const wrappedFn = curry(foo);
    expect(foo()).toEqual(wrappedFn());
  });

  test("curry one argument function", () => {
    const foo = (a: number) => a;
    const wrappedFn = curry(foo);
    expect(foo(1)).toEqual(wrappedFn(1));
  });

  test('curry two arguments function', () => {
    const sum = (a: number, b: number) => a + b;
    const wrappedFn = curry(sum);
    expect(sum(1, 2)).toEqual(wrappedFn(1)(2));
    expect(sum(1, 2)).toEqual(wrappedFn(1, 2));
  });

  test('curry more arguments function', () => {
    const sum = (a: number, b: number, c: number, d: number) => a + b + c + d;
    const wrappedFn = curry(sum);
    expect(sum(1, 2, 3, 4)).toEqual(wrappedFn(1)(2)(3)(4));
    expect(sum(1, 2, 3, 4)).toEqual(wrappedFn(1, 2)(3, 4));
  });

  test('re-calling a curated function', () => {
    const sum = (a: number, b: number) => a + b;
    const wrappedFn = curry(sum);
    expect(sum(1, 2)).toEqual(wrappedFn(1)(2));
    expect(sum(1, 2)).toEqual(wrappedFn(1)(2));
  });
});