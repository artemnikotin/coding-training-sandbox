/**
 * Leetcode | Medium | 2633. Convert Object to JSON String
 * Converts a JavaScript object, array, or primitive value into a JSON string.
 * This function mimics the behavior of `JSON.stringify` but is implemented manually.
 *
 * @param {any} object - The value to be converted into a JSON string.
 * @returns {string | undefined} - The JSON string representation of the input value.
 *                                 Returns `undefined` for unsupported types (e.g., functions, symbols).
 */
export function jsonStringify(object: any): string | undefined {
  // Handle primitive types: null, numbers, and booleans.
  // These can be directly converted to strings using `String()`.
  if (object === null || typeof object === 'number' || typeof object === 'boolean') {
    return String(object);
  }

  // Handle strings. Strings need to be wrapped in double quotes to be valid JSON.
  if (typeof object === 'string') {
    return `"${object}"`;
  }

  // Handle arrays. Arrays are converted into a comma-separated list of JSON-encoded values,
  // enclosed in square brackets.
  if (Array.isArray(object)) {
    // Map each element of the array to its JSON string representation.
    // If an element cannot be stringified (e.g., undefined), it is replaced with 'null'.
    const parts = [];
    for (let i = 0; i < object.length; i++) {
      parts.push(jsonStringify(object[i]) ?? 'null');
    }
    // Join the parts with commas and wrap in square brackets.
    return `[${parts.join(',')}]`;
  }

  // Handle objects. Objects are converted into a comma-separated list of key-value pairs,
  // enclosed in curly braces.
  if (typeof object === 'object') {
    return (
      '{' +
      Object.keys(object)
        // Map each key-value pair to a JSON string representation.
        .map((key) => [key, jsonStringify(object[key])])
        // Filter out key-value pairs where the value is `undefined`.
        .filter(([, value]) => value !== undefined)
        // Format each key-value pair as `"key":value`.
        .map(([key, value]) => `"${key}":${value}`)
        // Join the pairs with commas.
        .join(',') +
      '}'
    );
  }

  // Return `undefined` for unsupported types (e.g., functions, symbols).
  return undefined;
}