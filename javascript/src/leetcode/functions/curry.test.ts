import { expect, test } from 'vitest';

import { curry } from './curry';

test('curry two arguments function', () => {
  const sum = (a: number, b: number) => a + b;
  const csum = curry(sum);
  expect(sum(1, 2)).toEqual(csum(1)(2));
});

test('curry more arguments function', () => {
  const sum = (a: number, b: number, c: number, d: number) => a + b + c + d;
  const csum = curry(sum);
  expect(sum(1, 2, 3, 4)).toEqual(csum(1)(2)(3)(4));
});

test('re-calling a curated function', () => {
  const sum = (a: number, b: number) => a + b;
  const csum = curry(sum);
  expect(sum(1, 2)).toEqual(csum(1)(2));
  expect(sum(1, 2)).toEqual(csum(1)(2));
});