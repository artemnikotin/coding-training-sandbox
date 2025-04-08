// Generic type for a function that returns a Promise of type T
type Fn<T> = () => Promise<T>;

/**
 * Leetcode | Medium | 2721. Execute Asynchronous Functions in Parallel
 * 
 * Similar to Promise.all(), this function executes an array of asynchronous functions in parallel
 * and returns a promise that resolves with an array of their results in the same order.
 * If any promise rejects, the entire operation rejects immediately.
 * 
 * @template T The type of the resolved promise values
 * @param functions Array of functions that return promises
 * @returns Promise that resolves with an array of results or rejects if any function fails
 * 
 * @example
 * const functions = [
 *   () => new Promise(resolve => setTimeout(() => resolve(1), 200)), 
 *   () => new Promise(resolve => setTimeout(() => resolve(2), 100))
 * ];
 * promiseAll(functions).then(console.log); // [1, 2] (after ~200ms)
 */
export function promiseAll<T>(functions: Fn<T>[]): Promise<T[]> {
  return new Promise((resolve, reject) => {
    // Counter for remaining functions to complete
    let left = functions.length;
    
    // Pre-allocate results array to maintain order
    const results = new Array(functions.length);
    
    // Edge case: if empty array passed, resolve immediately with empty array
    if (functions.length === 0) {
      resolve(results);
      return;
    }

    // Execute each function in the array
    functions.forEach((fn, i) => {
      fn() // Call the function to get its promise
        .then(result => {
          // Store result in correct position
          results[i] = result;
          
          // Check if all promises have resolved
          if (--left === 0) {
            resolve(results);
          }
        })
        .catch(reject); // Reject immediately if any promise rejects
    });
  });
};