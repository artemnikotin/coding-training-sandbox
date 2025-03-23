/**
 * Leetcode | Medium | 2623. Memoize
 * 
 * This function creates a memoized version of the input function `fn`.
 * Memoization is a technique used to optimize functions by caching their results
 * based on the input arguments. If the function is called again with the same arguments,
 * the cached result is returned instead of recomputing it.
 * 
 * **Note**: This implementation only works for functions that accept numeric parameters.
 * It uses the string representation of the arguments as the cache key, which is not suitable
 * for functions with non-numeric or complex arguments (e.g., objects, arrays, etc.).
 */

type Fn = (...args: number[]) => any;

/**
 * Creates a memoized version of the input function `fn`.
 * @param fn - The original function to memoize.
 * @returns A memoized version of the input function.
 */
export function memoize(fn: Fn): Fn {
  // Create a cache to store the results of function calls.
  // The key is the string representation of the arguments, and the value is the result of the function.
  const cache = new Map<string, ReturnType<Fn>>();

  // Return a new function that is the memoized version of `fn`.
  return function (...args: Parameters<Fn>) {
    // Convert the arguments to a string to use as the cache key.
    const key = String(args);

    // If the result for the current arguments is not in the cache, compute it and store it.
    if (!cache.has(key)) {
      cache.set(key, fn(...args));
    }

    // Return the cached result for the current arguments.
    return cache.get(key);
  };
}