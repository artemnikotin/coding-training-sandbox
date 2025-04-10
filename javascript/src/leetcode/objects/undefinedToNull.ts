/**
 * Leetcode | Medium | 2775 - Undefined to Null
 * 
 * Recursively converts all undefined values in an object (including nested objects) to null.
 * This function modifies the original object in place.
 * 
 * @param obj The object to process (can be a plain object or array)
 * @returns The modified object with undefined values replaced by null
 * 
 * @example
 * undefinedToNull({a: undefined, b: {c: undefined}});
 * // Returns {a: null, b: {c: null}}
 * 
 * @example
 * undefinedToNull([undefined, {x: undefined}]);
 * // Returns [null, {x: null}]
 */
export function undefinedToNull(obj: Record<any, any>): Record<any, any> {
  // Iterate through all enumerable properties of the object
  for (const key in obj) {
      // Check if the property value is an object (including arrays)
      if (typeof obj[key] === 'object' && obj[key] !== null) {
          // Recursively process nested objects
          obj[key] = undefinedToNull(obj[key]);
      }
      
      // Replace undefined values with null
      if (obj[key] === undefined) {
          obj[key] = null;
      }
  }
  
  // Return the modified object
  return obj;
}