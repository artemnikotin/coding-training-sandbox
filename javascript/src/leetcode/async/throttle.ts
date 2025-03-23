/**
 * Leetcode | Medium | 2676. Throttle
 * 
 * This function creates a throttled version of the input function `fn`.
 * The throttled function ensures that `fn` is executed at most once every `t` milliseconds,
 * even if the throttled function is called multiple times within that interval.
 */

type F = (...p: any[]) => any;

/**
 * Creates a throttled version of the input function `fn`.
 * @param fn - The original function to throttle.
 * @param t - The interval in milliseconds during which `fn` can be executed at most once.
 * @returns A throttled version of the input function.
 */
export function throttle(fn: F, t: number): F {
  // Variable to store the timeout ID returned by `setTimeout`.
  let cooldown: ReturnType<typeof setTimeout> = 0;

  // Variable to store the last arguments passed to the throttled function during the cooldown period.
  let lastArgs: any[] | null = null;

  // Return a new function that will be the throttled version of `fn`.
  return function call(...args) {
    // If there is no active timeout, execute `fn` immediately.
    if (!cooldown) {
      fn(...args);

      // Set a timeout to reset the cooldown period after `t` milliseconds.
      cooldown = setTimeout(() => {
        cooldown = 0; // Reset the timeout ID.

        // If there are stored arguments from a call during the cooldown period, execute `fn` with those arguments.
        if (lastArgs) {
          call(...lastArgs);
          lastArgs = null; // Clear the stored arguments.
        }
      }, t);
    } else {
      // If there is an active timeout (i.e., the function is in the cooldown period),
      // store the current arguments for execution after the cooldown ends.
      lastArgs = args;
    }
  };
}
