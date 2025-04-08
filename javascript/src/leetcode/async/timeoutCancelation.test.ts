import { afterEach, beforeEach, describe, expect, test, vi } from "vitest";
import { cancellable } from "./timeoutCancelation";

describe("Leetcode | 2715. Timeout Cancellation", () => {
  beforeEach(() => {
    vi.useFakeTimers();
  });

  afterEach(() => {
    vi.useRealTimers();
  });

  test("one argument call", () => {
    const fn = vi.fn(x => x * 5);
    const cancel = cancellable(fn, [5], 20);

    setTimeout(cancel, 50);
    vi.advanceTimersByTime(50);

    expect(fn.mock.calls[0]).toEqual([5]);
    expect(fn.mock.results[0].value).toEqual(25);
  });

  test("cancellation", () => {
    const fn = vi.fn(x => x * 5);
    const cancel = cancellable(fn, [5], 50);

    setTimeout(cancel, 20);
    vi.advanceTimersByTime(50);

    expect(fn).toBeCalledTimes(0);
  });

  test("multi arguments call", () => {
    const fn = vi.fn((x, y) => x + y);
    const cancel = cancellable(fn, [5, 10], 20);

    setTimeout(cancel, 50);
    vi.advanceTimersByTime(50);

    expect(fn.mock.calls[0]).toEqual([5, 10]);
    expect(fn.mock.results[0].value).toEqual(15);
  });
});