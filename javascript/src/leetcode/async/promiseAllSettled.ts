// Define types for the possible result objects
type FulfilledObj = {
  status: 'fulfilled';
  value: any;  // The resolved value of the promise
};
type RejectedObj = {
  status: 'rejected';
  reason: string;  // The rejection reason
};
type Obj = FulfilledObj | RejectedObj;  // Union type for all possible results
type PromiseFn = () => Promise<any>;  // Type for functions that return promises

/**
 * Implements functionality similar to Promise.allSettled() - executes all promise-returning
 * functions in parallel and returns their results (both fulfilled and rejected) in the original order.
 * 
 * Leetcode | Medium | 2795 - Parallel Execution of Promises for Individual Results Retrieval
 * 
 * @param functions - Array of functions that return promises
 * @returns Promise that resolves with an array of result objects for each input promise
 */
export function promiseAllSettled(functions: PromiseFn[]): Promise<Obj[]> {
  return new Promise(resolve => {
    let left = functions.length;  // Counter for remaining promises to settle

    // Pre-allocate result array to maintain order
    const results = new Array(functions.length);

    // Handle empty input case immediately
    if (functions.length === 0) {
      resolve(results);
      return;
    }

    // Execute each promise-returning function
    functions.forEach((fn, i) => {
      fn()
        // Handle successful resolution
        .then(value => ({ status: 'fulfilled', value }))
        // Handle rejection
        .catch(reason => ({ status: 'rejected', reason }))
        // Store result and check completion
        .then(result => {
          results[i] = result;  // Store result at original index
          
          // Decrement counter and resolve if all promises are settled
          if (--left === 0) {
            resolve(results);
          }
        });
    });
  });
}