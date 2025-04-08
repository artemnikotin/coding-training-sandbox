/**
 * Leetcode | Easy | 2626. Array Reduce Transformation
 * 
 * This function mimics the behavior of the `Array.prototype.reduce` method.
 * It applies a reducer function to each element of the array, accumulating a single result.
 */

// Define a type for the reducer function.
// It takes an accumulator (`accum`) and the current element (`curr`) and returns a number.
type Fn = (accum: number, curr: number) => number;

/**
 * Applies a reducer function to each element of the array, accumulating a single result.
 * @param nums - The input array of numbers.
 * @param fn - The reducer function to apply to each element.
 * @param init - The initial value of the accumulator.
 * @returns The final accumulated value after applying the reducer function to all elements.
 */
export function reduce(nums: number[], fn: Fn, init: number): number {
  // Initialize the accumulator with the provided initial value.
  let acc = init;

  // Iterate over each element in the array.
  for (let i = 0; i < nums.length; i++) {
    // Apply the reducer function to the accumulator and the current element.
    // Update the accumulator with the result.
    acc = fn(acc, nums[i]);
  }

  // Return the final accumulated value.
  return acc;
}