import { afterEach, beforeEach, describe, expect, test, vi } from "vitest";
import { timeLimit, timeLimitNoRace } from "./timeLimit";

describe.each([
  ['timeLimit', timeLimit],
  ['timeLimitNoRace', timeLimitNoRace],
]
)('Leetcode | %s', (_, limitFn) => {
  beforeEach(() => {
    vi.useFakeTimers();
  });

  afterEach(() => {
    vi.useRealTimers();
  });

  test('the function resolve befor time limit', async () => {
    const double = async (n: number) => new Promise(resolve => setTimeout(resolve, 100, n * 2));
    const wrappedFn = limitFn(double, 1000);

    const promise = wrappedFn(2);
    await vi.advanceTimersByTimeAsync(1000);

    expect(await promise).toBe(4);
  });

  test('the function reject befor time limit', async () => {
    const double = async (n: number) => new Promise((_, reject) => setTimeout(() => {
      reject(new Error('Function'));
    }, 100, n * 2));
    const wrappedFn = limitFn(double, 1000);
    await expect((async () => {
      const promise = wrappedFn(2);
      vi.advanceTimersByTimeAsync(100);
      return promise;
    })()).rejects.toThrowError('Function');
  });

  test('the function reject after time limit', async () => {
    const double = async (n: number) => new Promise((_, reject) => setTimeout(() => {
      reject(new Error('function'));
    }, 1000, n * 2));
    const wrappedFn = limitFn(double, 100);
    await expect((async () => {
      const promise = wrappedFn(2);
      vi.advanceTimersByTimeAsync(100);
      return promise;
    })()).rejects.toThrowError('Limit');
  });

  test('the function resolve after time limit', async () => {
    const double = async (n: number) => new Promise(resolve => setTimeout(resolve, 1000, n * 2));
    const wrappedFn = limitFn(double, 100);

    await expect((async () => {
      const promise = wrappedFn(2);
      vi.advanceTimersByTimeAsync(1000);
      return promise;
    })()).rejects.toThrowError('Limit');
  });
})