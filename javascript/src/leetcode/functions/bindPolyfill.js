/**
 * Leetcode | Medium | 2754. Bind Function to Context
 * 
 * Polyfill for Function.prototype.bind that binds a function to a context object
 * using a Proxy-based approach to maintain the binding when called.
 * 
 * @param {Object} context - The object to bind as the 'this' context
 * @returns {Function} A bound function that maintains the context when called
 * 
 * @example
 * const obj = { x: 42 };
 * function test() { return this.x; }
 * const boundFn = test.bindPolyfill(obj);
 * boundFn(); // Returns 42
 */
Function.prototype.bindPolyfill = function (context) {
  // Create a unique Symbol to use as a property key for the function
  // This ensures our property won't conflict with existing properties
  var call = Symbol();

  // Store the function (this) in a variable
  // 'this' refers to the function being bound
  var fn = this;

  // If no context is provided, default to the global object
  // (window in browsers, global in Node.js)
  context = context ?? globalThis;

  // Return a new function that will maintain the bound context
  return function (...args) {
    // Create a Proxy for the context object to intercept property access
    return new Proxy(context, {
      // Intercept property access on the context object
      get: (target, prop) => {
        // If the accessed property is our unique Symbol,
        // return the original function to be called
        if (prop === call) {
          return fn;
        }
        // For all other properties, use normal property access
        return Reflect.get(target, prop);
      },
    })[call](...args); // Call the function with the provided arguments
  }
};