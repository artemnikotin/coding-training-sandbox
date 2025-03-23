/**
 * Leetcode | Medium | 2693. Call Function with Custom Context
 * Please solve this without using the built-in Function.call method.
 * 
 * @param {Object} context - The context (`this` value) to bind the function to.
 * @param {Array} args - Arguments to pass to the function.
 * @return {null|boolean|number|string|Array|Object} - The result of the function invocation.
 */
Function.prototype.callPolyfill = function (context, ...args) {
  // Create a unique Symbol to use as a property key for the function
  var call = Symbol();

  // Store the function (this) in a variable
  var fn = this;

  // If no context is provided, default to the global object (e.g., `window` in browsers, `global` in Node.js)
  context = context ?? globalThis;

  // Create a Proxy for the context object
  return new Proxy(context, {
    // Intercept property access on the context object
    get: (target, prop) => {
      // If the accessed property is the unique Symbol, return the function
      if (prop === call) {
        return fn;
      }
      // Otherwise, delegate to the original property access behavior
      return Reflect.get(target, prop);
    },
  })[call](...args); // Access the function via the Symbol and invoke it with the provided arguments
};
