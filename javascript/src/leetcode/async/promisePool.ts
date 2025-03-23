/**
 * Leetcode | Medium | 2636. Promise Pool
 * The goal is to execute a pool of asynchronous functions (functions) with a concurrency limit (n).
 * This means that at most n functions can run simultaneously. Once one function completes, the next function in the queue should start,
 * ensuring that the concurrency limit is always respected.
 */

type F = () => Promise<any>;

/**
 * This implementation uses a recursive approach to manage the concurrency limit.
 */
export async function promisePool(functions: F[], n: number): Promise<void> {
  return new Promise((resolve) => {
    let i = 0; // Index to track the next function to execute.
    let inProgress = 0; // Counter to track the number of currently running functions.

    // Recursive function to execute functions while respecting the concurrency limit.
    function next() {
      // While there are functions left to execute and the concurrency limit is not exceeded:
      while (i < functions.length && inProgress < n) {
        // Execute the next function in the array.
        functions[i++]()
          .then(() => {
            // When the function completes, decrement the inProgress counter.
            inProgress--;
            // Call `next` again to start the next function.
            next();
          });
        // Increment the inProgress counter since a new function has started.
        inProgress++;
      }

      // If no functions are in progress, resolve the outer promise.
      if (inProgress === 0) {
        resolve();
      }
    }

    // Start the process by calling `next`.
    next();
  });
}

/**
 * This implementation uses an alternative approach with Promise.all and recursion.
 */
export async function promisePoolAlternate(functions: F[], n: number): Promise<void> {
  let i = 0; // Index to track the next function to execute.

  // Recursive function to execute one function at a time.
  async function next() {
    // If all functions have been executed, stop.
    if (i === functions.length) {
      return;
    }
    // Execute the next function and wait for it to complete.
    await functions[i++]();
    // Recursively call `next` to execute the next function.
    await next();
  }

  // Start `n` concurrent executions of the `next` function.
  const initialBatch = Array(n).fill(0).map(next);
  // Wait for all initial executions to complete.
  await Promise.all(initialBatch);
}
