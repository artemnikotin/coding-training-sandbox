/**
 * Creates a partially applied function with placeholder support.
 * Replaces "_" placeholders in the initial arguments with subsequent arguments.
 * Leetcode | Easy | 2797. Partial Function with Placeholders
 * 
 * @param fn - The original function to be partially applied
 * @param args - Array of initial arguments (may contain "_" placeholders)
 * @returns {Function} - A new function that will merge placeholder arguments with remaining arguments
 * 
 * @example
 * const fn = (a, b, c) => a + b + c;
 * const partialFn = partial(fn, ["_", 2, "_"]);
 * partialFn(1, 3); // returns 6 (1 + 2 + 3)
 */
export function partial(fn: Function, args: any[]): Function {
  return function (...restArgs: any[]) {
    let i = 0; // Tracks current position in restArgs

    // Process arguments by:
    // 1. Replacing "_" placeholders with values from restArgs
    // 2. Appending any remaining restArgs
    return fn(
      ...args
        .map(arg => arg === "_" ? restArgs[i++] : arg) // Replace placeholders
        .concat(restArgs.slice(i)) // Append leftover arguments
    );
  }
}