// Type definition for callback-based functions
// These functions take:
// - A next callback that accepts (data, error) parameters
// - Any number of numeric arguments
export type CallbackFn = (next: (data: number, error?: string) => void, ...args: number[]) => void;

// Type definition for the promisified version of the function
// Takes numeric arguments and returns a Promise<number>
type Promisified = (...args: number[]) => Promise<number>;

/**
 * Leetcode | Medium | 2776. Convert Callback Based Function to Promise Based Function
 * 
 * Converts a callback-style asynchronous function into a Promise-based function.
 * This is a common pattern for modernizing older callback-based APIs.
 * 
 * @param fn The callback-based function to promisify
 * @returns A new function that returns a Promise
 * 
 * @example
 * const callbackFn = (next, x, y) => {
 *   setTimeout(() => next(x + y), 100);
 * };
 * const promisedAdd = promisify(callbackFn);
 * promisedAdd(2, 3).then(console.log); // 5
 * 
 * @example Error handling
 * const errorFn = (next, x) => {
 *   next(0, "Division by zero");
 * };
 * const promisedDiv = promisify(errorFn);
 * promisedDiv(1, 0).catch(console.error); // "Division by zero"
 */
export function promisify(fn: CallbackFn): Promisified {
  // Return a new function that takes the same numeric arguments
  return (...args) => new Promise<number>((resolve, reject) => {
    // Call the original callback-based function with:
    // 1. A new callback that resolves/rejects the Promise
    // 2. The original arguments
    fn((data, error) => {
      if (error) {
        // If error exists, reject the Promise with the error
        reject(error);
      } else {
        // Otherwise resolve with the data
        resolve(data);
      }
    }, ...args);
  });
}