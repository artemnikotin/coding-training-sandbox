/**
 * Leetcode | Medium | 2627. Debounce
 * 
 * This function creates a debounced version of the input function `fn`.
 * The debounced function delays the execution of `fn` until `t` milliseconds
 * have passed since the last time the debounced function was called.
 */

type F = (...p: any[]) => any;

/**
 * Creates a debounced version of the input function `fn`.
 * @param fn - The original function to debounce.
 * @param t - The delay in milliseconds to wait before executing `fn`.
 * @returns A debounced version of the input function.
 */
export function debounce(fn: F, t: number): F {
  // Variable to store the timeout ID returned by `setTimeout`.
  let timeout: ReturnType<typeof setTimeout> = 0;

  // Return a new function that will be the debounced version of `fn`.
  return function (...args) {
    // If there is an existing timeout, clear it to reset the delay.
    if (timeout) {
      clearTimeout(timeout);
    }

    // Set a new timeout to execute `fn` after `t` milliseconds.
    timeout = setTimeout(() => fn(...args), t);
  };
}