/**
 * Leetcode | 2632. Curry
 * Curries a given function, allowing it to be called with arguments one at a time.
 * The curry function is a higher-order function that transforms a given function into its curried version.
 * Currying is a functional programming technique where a function that takes multiple arguments is
 * transformed into a sequence of functions, each taking a single argument.
 * @param fn - The function to be curried.
 * @returns {Function} - A curried version of the input function.
 */
export function curry(fn: Function): Function {
  return function(...args: any[]) {
    // Accumulate the arguments passed so far.
    let argumentsAccumulator: any[] = [...args];

    // If enough arguments have been accumulated, call the original function.
    if (argumentsAccumulator.length >= fn.length) {
      return fn(...argumentsAccumulator);
    } else {
      // Otherwise, return a new function that continues to accumulate arguments.
      return function curried(...args: any[]) {
        // Append the new arguments to the accumulator.
        argumentsAccumulator = [...argumentsAccumulator, ...args];

        // If enough arguments are now available, call the original function.
        // Otherwise, continue currying by returning the curried function.
        return (argumentsAccumulator.length >= fn.length) 
          ? fn(...argumentsAccumulator) 
          : curried;
      };
    }
  }
}