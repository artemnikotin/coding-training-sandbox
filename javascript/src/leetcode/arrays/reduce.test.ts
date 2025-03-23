import { expect, test } from "vitest";
import { reduce } from "./reduce";

function sum(acc: number, curr: number) {
  return acc + curr;
}

const arr = [1, 2, 3];

test('sum with zero initial number', () => {
  expect(reduce(arr, sum, 0)).toBe(6);
});

test('sum with non-zero initial number', () => {
  expect(reduce(arr, sum, 100)).toBe(106);
});

test('empty array', () => {
  expect(reduce([], sum, 100)).toBe(100);
});