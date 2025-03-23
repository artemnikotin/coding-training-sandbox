import { describe, expect, test, vi, beforeEach, afterEach } from 'vitest';
import { promisePool, promisePoolAlternate } from './promisePool';

const fn = vi.fn();

const functions = [
  () => new Promise((resolve) => setTimeout(resolve, 900, 1)).then(fn),
  () => new Promise((resolve) => setTimeout(resolve, 100, 2)).then(fn),
  () => new Promise((resolve) => setTimeout(resolve, 100, 3)).then(fn),
  () => new Promise((resolve) => setTimeout(resolve, 500, 4)).then(fn),
  () => new Promise((resolve) => setTimeout(resolve, 100, 5)).then(fn),
  () => new Promise((resolve) => setTimeout(resolve, 100, 6)).then(fn),
];

async function simulateTimer() {
  await vi.advanceTimersByTimeAsync(100);
  await vi.advanceTimersByTimeAsync(100);
  await vi.advanceTimersByTimeAsync(300);
  await vi.advanceTimersByTimeAsync(400);
}

describe.each([
  ['promisePool', promisePool],
  ['promisePoolAlternate', promisePoolAlternate],
])('Leetcode | %s', (_, promisePool) => {
  beforeEach(() => {
    vi.useFakeTimers();
    vi.resetAllMocks();
  });
  afterEach(() => {
    vi.useRealTimers();
  });

  test('Promise Pool', async () => {
    const done = promisePool(functions, 3);

    await simulateTimer();

    await done;
    fn(7);

    expect(fn.mock.calls).toEqual([[2], [3], [5], [6], [4], [1], [7]]);
  });
});