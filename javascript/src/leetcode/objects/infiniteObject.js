/**
 * Leetcode | Easy | 2690. Infinite Method Object
 * 
 * Creates an infinite object that dynamically handles any method call by returning the method name as a string.
 * 
 * @returns A proxy object that intercepts all property accesses and returns a function
 *          that returns the property name as a string when called.
 */
export function createInfiniteObject() {
  // Return a new Proxy that wraps an empty object
  return new Proxy({}, {
    /**
     * Interceptor for property access (get trap)
     * 
     * @param _ - The target object (unused)
     * @param prop - The property/method name being accessed
     * @returns A function that returns the property name as a string when called
     */
    get: (_, prop) => {
      // Return a function that, when called, returns the property name as a string
      return () => prop.toString();
    },
  });
}