import { afterEach, beforeEach, describe, expect, test, vi } from "vitest";
import { promiseAll } from "./promiseAll";

const fn = vi.fn(x => x);

describe("Leetcode | 2721. Execute Asynchronous Functions in Parallel", () => {
  beforeEach(() => {
    vi.useFakeTimers();
    vi.resetAllMocks();
  });

  afterEach(() => {
    vi.useRealTimers();
  });

  test("the single function is resolved", async () => {
    const functions = [
      () => new Promise(resolve => setTimeout(resolve, 200, 5)).then(fn),
    ];
    const promise = promiseAll(functions);

    await vi.advanceTimersByTimeAsync(200);

    expect(fn.mock.calls).toEqual([[5]]);
    expect(await promise).toEqual([5]);
  });

  test("one of the promise is rejected", async () => {
    const functions = [
      () => new Promise(resolve => setTimeout(resolve, 200, 1)).then(fn),
      () => new Promise((_, reject) => setTimeout(reject, 100, "Error")),
    ];
    const promise = promiseAll(functions);
    promise.catch(e => expect(e).toBe("Error"));

    await vi.advanceTimersByTimeAsync(100);

    expect(fn).toBeCalledTimes(0);

    await vi.advanceTimersByTimeAsync(100);

    expect(fn).toBeCalledTimes(1);
  });

  test("all the promises resolved with a value", async () => {
    const functions = [
      () => new Promise(resolve => setTimeout(resolve, 50, 50)).then(fn),
      () => new Promise(resolve => setTimeout(resolve, 150, 150)).then(fn),
      () => new Promise(resolve => setTimeout(resolve, 100, 100)).then(fn),
    ];
    const promise = promiseAll(functions);

    await vi.advanceTimersByTimeAsync(150);

    expect(fn.mock.calls).toEqual([[50], [100], [150]]);
    expect(await promise).toEqual([50, 150, 100]);
  });

});