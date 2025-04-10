// Export an empty object to make this file a module (needed for global declarations)
export { }

// Extend the global String interface with custom methods
declare global {
  interface String {
    /**
     * Leetcode | Easy | 2796. Repeat String
     * Creates a new string by repeating the original string a specified number of times
     * @param times - The number of times to repeat the string
     * @returns The new repeated string
     * @example
     * 'hello'.replicate(3); // returns 'hellohellohello'
     */
    replicate(times: number): string;
  }
}

// Implement the replicate method on String prototype
String.prototype.replicate = function (times: number) {
  // Create an array with 'times' elements, each filled with the original string
  // Then join all elements together into a single string
  return new Array(times).fill(this).join('');
};