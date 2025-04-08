// Define types for JSON-compatible values and a function type that accepts them
type JSONValue = null | boolean | number | string | JSONValue[] | { [key: string]: JSONValue };
type Fn = (...args: JSONValue[]) => void;  // Function that accepts JSONValues and returns void

/**
 * Leetcode | Easy | 2715. Timeout Cancellation
 * 
 * Creates a cancellable version of a function that would execute after a delay.
 * 
 * @param fn The function to execute after timeout
 * @param args Arguments to pass to the function
 * @param t Delay in milliseconds before execution
 * @returns A cancellation function that can be called to cancel the timeout
 * 
 * @example
 * const cancel = cancellable(console.log, ['Hello'], 1000);
 * cancel();  // Cancels the scheduled log
 */
export function cancellable(fn: Fn, args: JSONValue[], t: number): Function {
    // Set up the timeout to call fn with args after t milliseconds
    const timeout = setTimeout(fn, t, ...args);
    
    // Return a cancellation function that clears the timeout
    return () => clearTimeout(timeout);
};