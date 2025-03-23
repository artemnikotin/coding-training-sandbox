/**
 * Leetcode | Medium | 2625. Flatten Deeply Nested Array
 * 
 * This function flattens a deeply nested array up to a specified depth `n`.
 * It recursively traverses the array and flattens nested arrays until the desired depth is reached.
 */

type MultiDimensionalArray = (number | MultiDimensionalArray)[];

/**
 * Flattens a deeply nested array up to a specified depth `n`.
 * @param arr - The input multi-dimensional array to flatten.
 * @param n - The depth to which the array should be flattened.
 * @returns A flattened array up to the specified depth.
 */
export function flat(arr: MultiDimensionalArray, n: number): MultiDimensionalArray {
  // Initialize an empty array to store the flattened result.
  const flatten: MultiDimensionalArray = [];

  // Call the helper function to perform the flattening.
  flatDeep(arr, n, flatten);

  // Return the flattened array.
  return flatten;
}

/**
 * Recursively flattens a multi-dimensional array up to a specified depth `n`.
 * @param arr - The current array being processed.
 * @param n - The remaining depth to which the array should be flattened.
 * @param result - The array to store the flattened result.
 */
function flatDeep(arr: MultiDimensionalArray, n: number, result: MultiDimensionalArray) {
  // Base case: If the remaining depth `n` is 0, push all elements of the current array into the result.
  if (n === 0) {
    return arr.forEach(el => result.push(el));
  }

  // Iterate over each element in the current array.
  arr.forEach(el => {
    // If the element is an array, recursively flatten it with depth reduced by 1.
    if (Array.isArray(el)) {
      flatDeep(el, n - 1, result);
    } else {
      // If the element is not an array, push it directly into the result.
      result.push(el);
    }
  });
}