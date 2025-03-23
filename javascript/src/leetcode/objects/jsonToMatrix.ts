/**
 * Leetcode | Hard | 2675. Array of Objects to Matrix
 * 
 * This function converts an array of JSON-like objects into a matrix (2D array) where:
 * - The first row contains the sorted keys (flattened with dot notation for nested objects).
 * - Each subsequent row represents the values of the corresponding object in the input array.
 */

// Define the type for JSON values, which can be string, number, boolean, or null.
type MatrixValue = string | number | boolean | null;

// Define the type for the resulting matrix, which is a 2D array of MatrixValue.
type Matrix = MatrixValue[][];

/**
 * Converts an array of JSON-like objects into a matrix.
 * @param arr - An array of JSON-like objects.
 * @returns A matrix where the first row contains the sorted keys and the subsequent rows contain the values.
 */
export function jsonToMatrix(arr: any[]): Matrix {
  // Stack to keep track of the current key path (for nested objects).
  const prefixStack: string[] = [];

  // Set to store all unique keys (flattened with dot notation for nested objects).
  const keySet = new Set<string>();

  // Iterate over each object in the input array to collect all unique keys.
  for (const obj of arr) {
    enrichKeys(obj);
  }

  // Convert the set of keys into a sorted array.
  const keys = Array.from(keySet).sort();

  // Map to store the index of each key in the sorted keys array.
  const keyMap = new Map();
  keys.forEach((key, i) => {
    keyMap.set(key, i);
  });

  // Initialize the result matrix with the first row containing the sorted keys.
  const result: Matrix = [keys];

  // Iterate over each object in the input array to populate the matrix rows with values.
  for (let obj of arr) {
    enrichValues(obj);
  }

  /**
   * Recursively traverses the object to collect all keys (flattened with dot notation).
   * @param obj - The current object being traversed.
   */
  function enrichKeys(obj: any): void {
    dsf(obj, name => keySet.add(name));
  }

  /**
   * Populates the matrix row with values from the current object.
   * @param obj - The current object being processed.
   */
  function enrichValues(obj: any) {
    // Initialize a row with empty strings, with the same length as the keys array.
    const row = Array(keys.length).fill('');

    // Recursively traverse the object to populate the row with values.
    dsf(obj, (name, value) => {
      row[keyMap.get(name)] = value as MatrixValue;
    });

    // Add the populated row to the result matrix.
    result.push(row);
  }

  /**
   * Checks if the given object is nested (i.e., an array or a non-null object).
   * @param obj - The object to check.
   * @returns True if the object is nested, false otherwise.
   */
  function isNested(obj: object): boolean {
    return Array.isArray(obj) || (typeof obj === 'object' && obj !== null);
  }

  /**
   * Depth-first search (DFS) function to traverse the object.
   * @param obj - The current object being traversed.
   * @param cb - Callback function to be called for each key-value pair.
   */
  function dsf(obj: any, cb: (key: string, value: MatrixValue) => void) {
    if (isNested(obj)) {
      // If the object is nested, recursively traverse its properties.
      for (const key in obj) {
        prefixStack.push(key.toString()); // Push the current key to the stack.
        dsf(obj[key], cb); // Recursively traverse the nested object.
        prefixStack.pop(); // Pop the current key from the stack.
      }
    } else {
      // If the object is not nested, call the callback with the flattened key and value.
      cb(prefixStack.join('.'), obj);
    }
  }

  // Return the resulting matrix.
  return result;
}