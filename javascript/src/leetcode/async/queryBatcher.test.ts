import { afterEach, beforeEach, describe, expect, test, vi } from "vitest";
import { QueryBatcher } from "./queryBatcher";

const queryFn = vi.fn();
const resultFn = vi.fn();

function doCalls(batcher: QueryBatcher, calls: { key: string, time: number }[]) {
  calls.forEach(call => {
    setTimeout(() => batcher.getValue(call.key).then(resultFn), call.time);
  });
}

describe("Leetcode | 2756. Query Batching", () => {
  beforeEach(() => {
    vi.useFakeTimers();
    vi.resetAllMocks();
  });

  afterEach(() => {
    vi.useRealTimers();
  });

  test("immediate query result", async () => {
    const queryMultiple = async function (keys: string[]) {
      queryFn(keys);
      return keys.map(key => key + '!');
    };
    const batcher = new QueryBatcher(queryMultiple, 100);

    doCalls(batcher, [
      { "key": "a", "time": 10 },
      { "key": "b", "time": 20 },
      { "key": "c", "time": 30 },
    ])

    await vi.advanceTimersByTimeAsync(10); // 10ms
    expect(queryFn.mock.calls).toEqual([[["a"]]]);

    await vi.advanceTimersByTimeAsync(90); // 100ms
    expect(queryFn.mock.calls).toEqual([[["a"]]]);

    await vi.advanceTimersByTimeAsync(10); // 110ms
    expect(queryFn.mock.calls).toEqual([[["a"]], [["b", "c"]]]);
    expect(resultFn.mock.calls).toEqual([["a!"], ["b!"], ["c!"]]);
  });

  test("timeout query result", async () => {
    const queryMultiple = async function (keys: string[]) {
      queryFn(keys);
      await new Promise(res => setTimeout(res, 100));
      return keys.map(key => key + '!');
    };
    const batcher = new QueryBatcher(queryMultiple, 100);

    doCalls(batcher, [
      { "key": "a", "time": 10 },
      { "key": "b", "time": 20 },
      { "key": "c", "time": 30 },
    ])

    await vi.advanceTimersByTimeAsync(10); // 10ms
    expect(queryFn.mock.calls).toEqual([[["a"]]]);

    await vi.advanceTimersByTimeAsync(90); // 100ms
    expect(queryFn.mock.calls).toEqual([[["a"]]]);

    await vi.advanceTimersByTimeAsync(10); // 110ms
    expect(queryFn.mock.calls).toEqual([[["a"]], [["b", "c"]]]);
    expect(resultFn.mock.calls).toEqual([["a!"]]);

    await vi.advanceTimersByTimeAsync(100); // 210ms
    expect(resultFn.mock.calls).toEqual([["a!"], ["b!"], ["c!"]]);
  });

  test("prorressive timeout query result", async () => {
    const queryMultiple = async function (keys: string[]) {
      queryFn(keys);
      await new Promise(res => setTimeout(res, keys.length * 100));
      return keys.map(key => key + '!');
    };
    const batcher = new QueryBatcher(queryMultiple, 100);

    doCalls(batcher, [
      { "key": "a", "time": 10 },
      { "key": "b", "time": 20 },
      { "key": "c", "time": 30 },
      { "key": "d", "time": 40 },
      { "key": "e", "time": 250 },
      { "key": "f", "time": 300 },
    ]);

    await vi.advanceTimersByTimeAsync(10); // 10ms
    expect(queryFn.mock.calls).toEqual([[["a"]]]);

    await vi.advanceTimersByTimeAsync(100); // 110ms
    expect(queryFn.mock.calls).toEqual([[["a"]], [["b", "c", "d"]]]);
    expect(resultFn.mock.calls).toEqual([["a!"]]);

    await vi.advanceTimersByTimeAsync(140); // 250ms
    expect(queryFn.mock.calls).toEqual([[["a"]], [["b", "c", "d"]], [["e"]]]);

    await vi.advanceTimersByTimeAsync(100); // 350ms
    expect(queryFn.mock.calls).toEqual([[["a"]], [["b", "c", "d"]], [["e"]], [["f"]]]);
    expect(resultFn.mock.calls).toEqual([["a!"], ["e!"]]);

    await vi.advanceTimersByTimeAsync(60); // 410ms
    expect(resultFn.mock.calls).toEqual([["a!"], ["e!"], ["b!"], ["c!"], ["d!"]]);

    await vi.advanceTimersByTimeAsync(40); // 450ms
    expect(resultFn.mock.calls).toEqual([["a!"], ["e!"], ["b!"], ["c!"], ["d!"], ["f!"]]);
  });
});
