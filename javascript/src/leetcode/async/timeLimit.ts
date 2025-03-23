/**
 * Leetcode | Medium | 2637. Promise Time Limit
 * 
 * This problem involves creating a function that adds a time limit to an asynchronous function.
 * If the function does not complete within the specified time limit, it should be rejected with a "Time Limit Exceeded" error.
 * Two implementations are provided:
 * 1. Using `Promise.race` to handle the time limit.
 * 2. Without using `Promise.race`, manually managing the timeout and promise resolution.
 */

type Fn = (...params: any[]) => Promise<any>;

/**
 * Adds a time limit to the input function `fn` using `Promise.race`.
 * @param fn - The original asynchronous function to which the time limit will be applied.
 * @param t - The time limit in milliseconds.
 * @returns A new function that will reject with "Time Limit Exceeded" if `fn` does not complete within `t` milliseconds.
 */
export function timeLimit(fn: Fn, t: number): Fn {
  return (...args) => {
    // Create a promise that rejects with "Time Limit Exceeded" after `t` milliseconds.
    const timeoutPromise = new Promise((_, reject) => {
      setTimeout(() => reject("Time Limit Exceeded"), t);
    });

    // Execute the original function `fn` with the provided arguments.
    const fnPromise = fn(...args);

    // Use `Promise.race` to resolve or reject based on whichever promise settles first:
    // - If `fnPromise` completes before the timeout, its result is returned.
    // - If the timeout occurs before `fnPromise` completes, the timeout promise rejects.
    return Promise.race([fnPromise, timeoutPromise]);
  };
}

/**
 * Adds a time limit to the input function `fn` without using `Promise.race`.
 * This implementation manually manages the timeout and promise resolution.
 * @param fn - The original asynchronous function to which the time limit will be applied.
 * @param t - The time limit in milliseconds.
 * @returns A new function that will reject with "Time Limit Exceeded" if `fn` does not complete within `t` milliseconds.
 */
export function timeLimitNoRace(fn: Fn, t: number): Fn {
  return function (...args) {
    return new Promise((resolve, reject) => {
      // Set a timeout to reject the promise with "Time Limit Exceeded" after `t` milliseconds.
      const timeout = setTimeout(() => {
        reject("Time Limit Exceeded");
      }, t);

      // Execute the original function `fn` with the provided arguments.
      fn(...args)
        .then((result) => {
          // If `fn` completes successfully, resolve the promise with its result.
          resolve(result);
        })
        .catch((error) => {
          // If `fn` throws an error, reject the promise with that error.
          reject(error);
        })
        .finally(() => {
          // Clear the timeout to prevent it from triggering after `fn` has completed.
          clearTimeout(timeout);
        });
    });
  };
}