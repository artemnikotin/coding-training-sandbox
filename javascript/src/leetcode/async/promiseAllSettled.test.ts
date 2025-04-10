import { afterEach, beforeEach, describe, expect, test, vi } from "vitest";
import { promiseAllSettled } from "./promiseAllSettled";

describe("Leetcode | 2795 - Parallel Execution of Promises for Individual Results Retrieval", () => {
  beforeEach(() => {
    vi.useFakeTimers();
  });

  afterEach(() => {
    vi.useRealTimers();
  });

  test("one resolved promise", async () => {
    const functions = [
      () => new Promise(resolve => setTimeout(resolve, 100, 15)),
    ];
    const exp = [
      { "status": "fulfilled", "value": 15 },
    ];
    const promise = promiseAllSettled(functions);

    await vi.advanceTimersByTimeAsync(100);
    expect(await promise).toEqual(exp);
  });

  test("several resolved promises", async () => {
    const functions = [
      () => new Promise(resolve => setTimeout(resolve, 100, 20)),
      () => new Promise(resolve => setTimeout(resolve, 50, 15)),
    ];
    const exp = [
      { "status": "fulfilled", "value": 20 },
      { "status": "fulfilled", "value": 15 }
    ];
    const promise = promiseAllSettled(functions);

    await vi.advanceTimersByTimeAsync(100);
    expect(await promise).toEqual(exp);
  });

  test("rejected promise", async () => {
    const functions = [
      () => new Promise(resolve => setTimeout(resolve, 200, 30)),
      () => new Promise((_, reject) => setTimeout(reject, 100, "Error")),
    ];
    const exp = [
      { "status": "fulfilled", "value": 30 },
      { "status": "rejected", "reason": "Error" },
    ];
    const promise = promiseAllSettled(functions);

    await vi.advanceTimersByTimeAsync(200);
    expect(await promise).toEqual(exp);
  });
});