/**
 * Leetcode | Hard | 2650. Design Cancellable Function
 * 
 * Creates a cancellable generator function that wraps an asynchronous generator.
 * 
 * @param generator - A generator function that yields Promises and eventually returns a value of type T
 * @returns A tuple containing:
 *          1. A cancel function that can be called to cancel the execution
 *          2. A Promise that resolves with the generator's return value or rejects if cancelled/errors
 */
export function cancellable<T>(generator: Generator<Promise<any>, T, unknown>): [() => void, Promise<T>] {
    // Initialize cancel function as a no-op
    let cancel = () => {};
    
    // Create a promise that will be rejected when cancelled
    const cancelPromise = new Promise((_, reject) => {
        // Assign the actual cancel function that rejects the promise
        cancel = () => reject('Cancelled');
    });
    // Add empty catch handler to prevent unhandled rejection warnings
    cancelPromise.catch(() => {});
  
    // Create the main promise that wraps the generator execution
    const promise = new Promise<T>(async (resolve, reject) => {
      try {
          // Get the first yielded value from generator
          let next = generator.next();
          
          // Continue processing until generator is done
          while (!next.done) {
              try {
                  // Wait for either the current promise or cancellation
                  const result = await Promise.race([next.value, cancelPromise]);
                  // Pass the result back to generator and get next value
                  next = generator.next(result);
              } catch (e) {
                  // If error occurs (including cancellation), throw it into generator
                  next = generator.throw(e);
              }
          }
          // Resolve with the final generator return value
          resolve(next.value);
      } catch(e) {
          // Reject if any unhandled error occurs in generator
          reject(e);
      }
    });
  
    // Return the cancel function and the execution promise
    return [cancel, promise];
  };