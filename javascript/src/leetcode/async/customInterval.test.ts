import { afterEach, beforeEach, describe, expect, test, vi } from "vitest";
import { customClearInterval, customInterval } from "./customInterval";

describe("Leetcode | 2805. Custom Interval", () => {
  beforeEach(() => {
    vi.useFakeTimers();
  });
  
  afterEach(() => {
    vi.useRealTimers();
  });
  
  test("no calls parameters", async () => {
    const delay = 50, period = 20, stopTime = 40;
    const fn = vi.fn();
    const id = customInterval(fn, delay, period);
    setTimeout(customClearInterval, stopTime, id);

    expect(fn).toHaveBeenCalledTimes(0);

    await vi.advanceTimersByTimeAsync(50); // 50ms
    expect(fn).toHaveBeenCalledTimes(0);
  });

  test("several calls parameters", async () => {
    const delay = 50, period = 20, stopTime = 225;
    const fn = vi.fn();
    const id = customInterval(fn, delay, period);
    setTimeout(customClearInterval, stopTime, id);

    expect(fn).toHaveBeenCalledTimes(0);

    await vi.advanceTimersByTimeAsync(50); // 50ms
    expect(fn).toHaveBeenCalledTimes(1);

    await vi.advanceTimersByTimeAsync(70); // 120ms
    expect(fn).toHaveBeenCalledTimes(2);

    await vi.advanceTimersByTimeAsync(90); // 210ms
    expect(fn).toHaveBeenCalledTimes(3);

    await vi.advanceTimersByTimeAsync(90); // 320ms
    expect(fn).toHaveBeenCalledTimes(3);
  });

  test("two intervals", async () => {
    const delay = 50, period = 20, stopTime1 = 110, stopTime2 = 130;
    const fn1 = vi.fn();
    const fn2 = vi.fn();

    setTimeout(customClearInterval, stopTime1, customInterval(fn1, delay, period));
    vi.advanceTimersToNextTimer();
    setTimeout(customClearInterval, stopTime2, customInterval(fn2, delay, period));

    await vi.advanceTimersByTimeAsync(50); // 50ms
    expect(fn1).toHaveBeenCalledTimes(1);
    expect(fn2).toHaveBeenCalledTimes(1);

    await vi.advanceTimersByTimeAsync(70); // 120ms
    expect(fn1).toHaveBeenCalledTimes(1);
    expect(fn2).toHaveBeenCalledTimes(2);

    await vi.advanceTimersByTimeAsync(90); // 210ms
    expect(fn1).toHaveBeenCalledTimes(1);
    expect(fn2).toHaveBeenCalledTimes(2);
  });
});