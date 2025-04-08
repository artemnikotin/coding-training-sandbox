/**
 * Leetcode | 2692. Make Object Immutable
 * 
 * This function takes an object and returns an immutable version of it.
 * Any attempts to modify the object or its nested properties will throw an error.
 * 
 * @param obj The object to make immutable
 * @returns An immutable proxy of the original object
 */
export function makeImmutable<T extends object>(obj: T): T {
  // List of array methods that mutate the array
  const mutatingMethods = new Set([
    'pop',
    'push',
    'shift',
    'unshift',
    'splice',
    'sort',
    'reverse',
  ]);

  // Proxy handler that implements immutable behavior
  const handler: ProxyHandler<object> = {
    /**
     * Trap for property assignment - throws an error when trying to set any property
     * @param target The target object
     * @param prop The property being set
     * @throws Error when attempting to modify any property
     */
    set(target, prop) {
      // Different error message for arrays (mentioning index) vs objects
      throw Array.isArray(target)
        ? `Error Modifying Index: ${String(prop)}`
        : `Error Modifying: ${String(prop)}`;
    },

    /**
     * Trap for property access - returns immutable versions of nested objects
     * @param target The target object
     * @param prop The property being accessed
     * @returns The property value, wrapped in a proxy if it's an object/function
     */
    get(target, prop) {
      const key = prop as keyof typeof target;
      // For prototype, null, or non-object/function values, return directly
      return prop === 'prototype' || target[key] === null
        || (typeof target[key] !== 'object' && typeof target[key] !== 'function')
        ? target[key]
        : new Proxy(target[key], handler); // Wrap nested objects in proxies
    },

    /**
     * Trap for function calls - prevents calling mutating array methods
     * @param target The function being called
     * @param thisArg The 'this' value for the function call
     * @param argumentsList The arguments passed to the function
     * @returns The result of the function call if allowed
     * @throws Error when attempting to call a mutating array method
     */
    apply(target: Function, thisArg, argumentsList) {
      // Check if the called method is in our list of mutating methods
      if (mutatingMethods.has(target.name)) {
        throw `Error Calling Method: ${(target as any).name}`;
      }
      // Allow non-mutating methods to proceed normally
      return target.apply(thisArg, argumentsList);
    },
  };

  // Create and return a proxy wrapping the original object
  return new Proxy(obj, handler) as T;
}