import { afterEach, beforeEach, describe, expect, test, vi } from "vitest";
import { throttle } from "./throttle";

const fn = vi.fn();

describe('Leetcode | 2676. Throttle', () => {
  beforeEach(() => {
    vi.useFakeTimers();
    vi.resetAllMocks();
  });
  
  afterEach(() => {
    vi.useRealTimers();
  });

  test('immediate and the only call of the original function on the only wrapped call', async () => {
    const wrappedFn = throttle(fn, 100);
    wrappedFn();
    expect(fn).toHaveBeenCalledTimes(1);

    await vi.advanceTimersByTimeAsync(100);
    expect(fn).toHaveBeenCalledTimes(1);
  });

  test('passing arguments', async () => {
    const wrappedFn = throttle(fn, 100);
    wrappedFn(1, 2);
    await vi.advanceTimersByTimeAsync(100);
    expect(fn.mock.calls).toEqual([[1, 2]]);
  });

  test('passing last call arguments', async () => {
    const wrappedFn = throttle(fn, 100);
    wrappedFn(1, 2);
    expect(fn.mock.calls).toEqual([[1, 2]]);
    wrappedFn(3, 4);
    wrappedFn(5, 6);
    await vi.advanceTimersByTimeAsync(100);
    expect(fn.mock.calls).toEqual([[1, 2], [5, 6]]);
  });

  test('the time between calls is less than the timeout.', async () => {
    const wrappedFn = throttle(fn, 100);
    wrappedFn();
    await vi.advanceTimersByTimeAsync(40);
    wrappedFn();
    await vi.advanceTimersByTimeAsync(40);
    wrappedFn();
    await vi.advanceTimersByTimeAsync(20);
    expect(fn).toHaveBeenCalledTimes(2);
  });

  test('the time between calls is greater than the timeout.', async () => {
    const wrappedFn = throttle(fn, 100);
    wrappedFn();
    await vi.advanceTimersByTimeAsync(100);
    wrappedFn();
    await vi.advanceTimersByTimeAsync(100);
    expect(fn).toHaveBeenCalledTimes(2);
  });
})