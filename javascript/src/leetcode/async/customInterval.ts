/**
 * Implements a custom interval timer with initial delay and periodic execution.
 * Leetcode | 2805. Custom Interval | Medium
 */

// Global map to store active interval timers
// Key: interval ID, Value: timeout reference
const intervalMap = new Map<number, ReturnType<typeof setTimeout>>();

/**
 * Creates a custom interval that:
 * 1. Waits for initial delay before first execution
 * 2. Then executes repeatedly with specified period between executions
 * @param fn - The function to execute periodically
 * @param delay - Initial delay before first execution (milliseconds)
 * @param period - Time between subsequent executions (milliseconds)
 * @returns {number} Unique ID for the interval (can be used to clear it)
 * 
 * @example
 * const id = customInterval(() => console.log('Hello'), 1000, 2000);
 * // Logs 'Hello' after 1 second, then every 2 seconds
 */
export function customInterval(fn: () => void, delay: number, period: number): number {
  // Recursive function that schedules the next execution
  function next() {
    intervalMap.set(
      id,
      setTimeout(() => {
        next();  // Schedule next execution
        fn();    // Execute the function
      }, 
      delay + period * i++)  // Calculate next execution time
    );
  }

  const id = Date.now();  // Generate unique ID using current timestamp
  let i = 0;  // Counter to track number of executions
  next();     // Start the interval
  return id;  // Return the ID for potential clearing
}

/**
 * Clears a custom interval created by customInterval()
 * @param id - The ID returned by customInterval()
 * 
 * @example
 * customClearInterval(id);  // Stops the interval
 */
export function customClearInterval(id: number) {
  const timeout = intervalMap.get(id);
  if (timeout) {
    clearTimeout(timeout);  // Cancel the scheduled execution
    intervalMap.delete(id); // Remove from tracking map
  }
}