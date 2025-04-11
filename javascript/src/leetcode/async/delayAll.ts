/**
 * Creates new functions that delay the execution of the original functions by a specified time.
 * Leetcode | 2821. Delay the Resolution of Each Promise | Medium
 * 
 * @param functions - Array of functions to be delayed (can return promises or regular values)
 * @param ms - Delay time in milliseconds before each function executes
 * @returns {Function[]} - New array of functions that each delay their execution by ms milliseconds
 * 
 * @example
 * const functions = [
 *   () => 1,
 *   () => new Promise(res => setTimeout(() => res(2), 100))
 * ];
 * const delayed = delayAll(functions, 200);
 * delayed[0](); // Returns Promise that resolves to 1 after 200ms delay
 * delayed[1](); // Returns Promise that resolves to 2 after 300ms total (200ms delay + original 100ms)
 */
export function delayAll(functions: Function[], ms: number): Function[] {
  // Map each original function to a new delayed version
  return functions.map(fn => {
    // Return a new async function that will:
    // 1. First wait for the specified delay
    // 2. Then execute the original function
    return async function () {
      // Wait for the specified delay time
      await new Promise(resolve => setTimeout(resolve, ms));
      
      // Execute and return the result of the original function
      // (works for both regular functions and promise-returning functions)
      return fn();
    };
  });
}