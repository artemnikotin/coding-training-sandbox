import { afterEach, beforeEach, describe, expect, test, vi } from "vitest";
import { cancellable } from "./cancellable";

describe("Leetcode | 2650. Design Cancellable Function", () => {
  beforeEach(() => {
    vi.useFakeTimers();
  });

  afterEach(() => {
    vi.useRealTimers();
  });

  test("cancelling a finished generator", async () => {
    const generatorFunction = function* () {
      return 42;
    }

    const [cancel, promise] = cancellable(generatorFunction());
    setTimeout(cancel, 100);
    await vi.advanceTimersByTimeAsync(100);
    expect(await promise).toBe(42);
  });

  test("cancelling working generator", async () => {
    const fn = vi.fn();
    const generatorFunction = function* () {
      const val: number = yield new Promise(resolve => resolve(2 + 2));
      yield new Promise(resolve => setTimeout(resolve, 100));
      fn(); // calculation shouldn't be done.
      return val + 1;
    }

    const [cancel, promise] = cancellable(generatorFunction());
    promise.catch(e => expect(e).toBe("Cancelled"));

    setTimeout(cancel, 50);
    await vi.advanceTimersByTimeAsync(50);

    expect(fn).toBeCalledTimes(0);
  });

  test("executing generator with error", async() => {
    const generatorFunction = function*() { 
      const msg: string = yield new Promise(res => res("Hello")); 
      throw `Error: ${msg}`; 
    }
    const [_, promise] = cancellable(generatorFunction());
    promise.catch(e => expect(e).toBe("Error: Hello"));
  });

  test("cancelation is caught by generator", async () => {
    const generatorFunction = function*() { 
      let result = 0; 
      try { 
        yield new Promise(res => setTimeout(res, 100)); 
        result += (yield new Promise(res => res(1))) as number; 
        yield new Promise(res => setTimeout(res, 100)); 
        result += (yield new Promise(res => res(1))) as number; 
      } catch(e) { 
        return result; 
      } 
      return result; 
    }
    const [cancel, promise] = cancellable(generatorFunction());

    setTimeout(cancel, 150);
    await vi.advanceTimersByTimeAsync(300);

    expect(await promise).toBe(1);
  });

  test("generator catchs internal errors", async () => {
    const generatorFunction = function*() { 
      try { 
        yield new Promise((_, reject) => reject("Promise Rejected")); 
      } catch(e) { 
        let a = (yield new Promise(resolve => resolve(2))) as number;
        let b = (yield new Promise(resolve => resolve(2))) as number; 
        return a + b; 
      }; 
    };
    const [_, promise] = cancellable(generatorFunction());
    expect(await promise).toBe(4);
  });
});