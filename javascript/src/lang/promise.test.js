import { vi, beforeEach, afterEach, describe, test, expect } from "vitest";

const thenFn = vi.fn();
const catchFn = vi.fn(() => 'Rej');
const finallyFn = vi.fn();

describe("Javascript/Promise", () => {
  beforeEach(() => {
    vi.useFakeTimers();
    vi.resetAllMocks();
  });

  afterEach(() => {
    vi.useRealTimers();
  });

  test("The value of a promise is set once", async () => {
    const promise = new Promise((resolve, reject) => {
      resolve('Ok');
      reject('Err');
    });
    promise.then(thenFn).catch(catchFn);

    await vi.runAllTimersAsync();

    expect(thenFn).toBeCalledTimes(1);
    expect(catchFn).toBeCalledTimes(0);
  });

  test("The refusal handle either through the second parameter in then, or through catch", async () => {
    const rejectedPromise = Promise.reject('Err');
    rejectedPromise.then(thenFn, catchFn);
    rejectedPromise.catch(catchFn);

    await vi.runAllTimersAsync();

    expect(catchFn).toBeCalledTimes(2);
  });

  test("promise.finally", async () => {
    const resolvedPromise = Promise.resolve('Ok');
    const rejectedPromise = Promise.reject('Err');

    expect(resolvedPromise).not.toBe(resolvedPromise.finally());

    resolvedPromise.finally(finallyFn).then(thenFn);
    rejectedPromise.finally(finallyFn).catch(catchFn);

    await vi.runAllTimersAsync();

    expect(thenFn).lastCalledWith('Ok');
    expect(catchFn).lastCalledWith('Err');
  });

  test("Promise chaining", async () => {
    Promise.resolve()
      .then(() => Promise.reject('O_o'))
      .then(thenFn)
      .catch(catchFn)
      .then(thenFn);

    await vi.runAllTimersAsync();

    expect(catchFn).toBeCalledTimes(1);
    expect(thenFn.mock.calls).toEqual([['Rej']]);
  });

  test("Thenable promise", async () => {
    const thenable = {
      then(fulfill) {
        fulfill('Then')
      }
    }

    const promise = Promise.resolve('Ok')

    const thenableResolved = Promise.resolve(thenable)
    const promiseRResolved = Promise.resolve(promise)

    expect(thenable).not.toBe(thenableResolved);
    expect(promise).toBe(promiseRResolved);

    thenableResolved.then(thenFn);
    await vi.runAllTimersAsync();

    expect(thenFn).toBeCalledTimes(1);
  });

  test("Promise.all returns an array of values ​​or the first failure", () => {
    Promise.all([
      new Promise(resolve => setTimeout(resolve, 400, 'Ok_1')),
      new Promise(resolve => setTimeout(resolve, 200, 'Ok_2')),
    ]).then((result) => {
      expect(result).toEqual(['Ok_1', 'Ok_2']);
    });

    Promise.all([
      new Promise(resolve => setTimeout(resolve, 400, 'Ok')),
      new Promise((_, reject) => setTimeout(reject, 200, 'Err')),
    ]).catch((error) => {
      expect(error).toBe('Err');
    });

    Promise.all([])
      .then((result) => {
        expect(result).toEqual([]);
      });
  });

  test("Promise.race returns the first value or the first failure", async () => {
    Promise.race([
      new Promise(resolve => setTimeout(resolve, 100, 'Ok')),
      new Promise((_, reject) => setTimeout(reject, 200, 'Err')),
    ]).then((result) => {
      expect(result).toBe('Ok');
    });

    Promise.race([
      new Promise(resolve => setTimeout(resolve, 200, 'Ok')),
      new Promise((_, reject) => setTimeout(reject, 100, 'Err')),
    ]).catch((error) => {
      expect(error).toBe('Err');
    });

    Promise.race([]).then(thenFn).catch(catchFn);
    await vi.runAllTimersAsync();

    expect(thenFn).toBeCalledTimes(0);
    expect(catchFn).toBeCalledTimes(0);
  });

  test("Promise.any returns the first value or an array with the reasons for the failure", () => {
    Promise.any([
      new Promise(resolve => setTimeout(resolve, 200, 'Ok')),
      new Promise((_, reject) => setTimeout(reject, 100, 'Err')),
    ]).then((result) => {
      expect(result).toBe('Ok');
    });

    Promise.any([
      new Promise((_, reject) => setTimeout(reject, 400, 'Err_1')),
      new Promise((_, reject) => setTimeout(reject, 200, 'Err_2')),
    ]).catch((error) => {
      expect(error.message).toBe("All promises were rejected");
      expect(error.errors).toEqual(["Err_1", "Err_2"]);
    });

    Promise.any([])
      .catch((error) => {
        expect(error.message).toBe("All promises were rejected");
      })
  });

  test("Promise allSettled returns", () => {
    Promise.allSettled([
      new Promise(resolve => setTimeout(resolve, 200, 'Ok')),
      new Promise((_, reject) => setTimeout(reject, 100, 'Err')),
    ]).then((result) => {
      expect(result).toEqual([
        { status: "fulfilled", value: "Ok" },
        { status: "rejected", reason: "Err" },
      ]);
    });

    Promise.allSettled([])
      .then((result) => {
        expect(result).toEqual([]);
      });
  });
});