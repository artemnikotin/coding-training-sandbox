import { afterEach, beforeEach, describe, expect, test, vi } from "vitest";
import { delayAll } from "./delayAll";

describe("Leetcode | 2821. Delay the Resolution of Each Promise", () => {
  beforeEach(() => {
    vi.useFakeTimers();
  });

  afterEach(() => {
    vi.useRealTimers();
  });

  test("promises are resolved", async () => {
    const fn = vi.fn();
    const functions = [
      () => new Promise((resolve) => setTimeout(resolve, 50, 50)).finally(fn),
      () => new Promise((resolve) => setTimeout(resolve, 80, 80)).finally(fn),
    ];
    const wrappedFunctions = delayAll(functions, 50);

    const promise = Promise.all(wrappedFunctions.map(fn => fn()));

    await vi.advanceTimersByTimeAsync(80); // 80ms
    expect(fn).toBeCalledTimes(0);

    await vi.advanceTimersByTimeAsync(20); // 100ms
    expect(fn).toBeCalledTimes(1);

    await vi.advanceTimersByTimeAsync(30); // 130ms
    expect(await promise).toEqual([50, 80]);
  });

  test("promises are rejected", async () => {
    const fn = vi.fn();
    const functions = [
      () => new Promise((_, reject) => setTimeout(reject, 50, "Error")).catch(fn),
      () => new Promise((_, reject) => setTimeout(reject, 80, "Error")).catch(fn),
    ];
    const wrappedFunctions = delayAll(functions, 50);

    wrappedFunctions.map(fn => fn());

    await vi.advanceTimersByTimeAsync(80); // 80ms
    expect(fn).toBeCalledTimes(0);

    await vi.advanceTimersByTimeAsync(20); // 100ms
    expect(fn).toBeCalledTimes(1);

    await vi.advanceTimersByTimeAsync(30); // 130ms
    expect(fn).toBeCalledTimes(2);
  });
});