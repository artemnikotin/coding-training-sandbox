import { afterEach, beforeEach, describe, expect, test, vi } from "vitest";
import { debounce } from "./debounce";

const fn = vi.fn();

describe('Leetcode | Debounce problem', () => {
  beforeEach(() => {
    vi.useFakeTimers();
    vi.resetAllMocks();
  });
  
  afterEach(() => {
    vi.useRealTimers();
  });

  test('first call of the original function after a timeout.', async () => {
    const wrappedFn = debounce(fn, 100);
    wrappedFn();
    expect(fn).toHaveBeenCalledTimes(0);

    await vi.advanceTimersByTimeAsync(100);
    expect(fn).toHaveBeenCalledTimes(1);
  });

  test('passing arguments', async () => {
    const wrappedFn = debounce(fn, 100);
    wrappedFn(1, 2);
    await vi.advanceTimersByTimeAsync(100);
    expect(fn.mock.calls).toEqual([[1, 2]]);
  });

  test('passing last call arguments', async () => {
    const wrappedFn = debounce(fn, 100);
    wrappedFn(1, 2);
    wrappedFn(3, 4);
    wrappedFn(5, 6);
    await vi.advanceTimersByTimeAsync(100);
    expect(fn.mock.calls).toEqual([[5, 6]]);
  });

  test('the time between calls is less than the timeout.', async () => {
    const wrappedFn = debounce(fn, 100);
    wrappedFn();
    await vi.advanceTimersByTimeAsync(50);
    wrappedFn();
    await vi.advanceTimersByTimeAsync(50);
    expect(fn).toHaveBeenCalledTimes(0);
    await vi.advanceTimersByTimeAsync(50);
    expect(fn).toHaveBeenCalledTimes(1);
  });

  test('the time between calls is greatr than the timeout.', async () => {
    const wrappedFn = debounce(fn, 100);
    wrappedFn();
    await vi.advanceTimersByTimeAsync(100);
    wrappedFn();
    await vi.advanceTimersByTimeAsync(100);
    expect(fn).toHaveBeenCalledTimes(2);
  });
})