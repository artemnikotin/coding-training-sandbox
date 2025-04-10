import { afterEach, beforeEach, describe, expect, test, vi } from "vitest";
import { CallbackFn, promisify } from "./promisify";

describe("Leetcode | 2776. Convert Callback Based Function to Promise Based Function", () => {
  beforeEach(() => {
    vi.useFakeTimers();
  });

  afterEach(() => {
    vi.useRealTimers();
  });

  test("immediate callback call", async () => {
    const fn: CallbackFn = (callback, a, b, c) => {
      callback(a * b * c);
    };
    const wrappedFn = promisify(fn);
    expect(await wrappedFn(1, 2, 3)).toBe(6);
  });

  test("deferred callback call", async () => {
    const fn: CallbackFn = (callback, a, b, c) => {
      setTimeout(callback, 100, a * b * c);
    };
    const wrappedFn = promisify(fn);

    const promise = wrappedFn(1, 2, 3);
    await vi.advanceTimersByTimeAsync(100);
    expect(await promise).toBe(6);
  });

  test("deferred error call", async () => {
    const fn: CallbackFn = (callback, a, b, c) => {
      setTimeout(callback, 100, a * b * c, "Promise Rejected");
    };
    const wrappedFn = promisify(fn);
    await expect(async () => {
      const promise = wrappedFn(1, 2, 3);
      vi.advanceTimersByTime(100);
      return promise;
    }).rejects.toThrowError("Promise Rejected");
  });
});