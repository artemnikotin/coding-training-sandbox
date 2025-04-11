/**
 * Recursively filters an object or array, removing elements that don't pass the filter function.
 * Works deeply through nested structures and prunes empty branches.
 * Leetcode | 2823. Deep Object Filter | Medium
 * 
 * @param obj - The object or array to filter (can be deeply nested)
 * @param filterFn - Function that determines if a primitive value should be kept
 * @returns {any} - Filtered copy of the original object/array, or undefined if nothing passes
 * 
 * @example
 * const obj = { a: 1, b: { c: 2, d: false }};
 * deepFilter(obj, x => x > 1); // Returns { b: { c: 2 }}
 * 
 * @example
 * const arr = [1, { x: 2, y: 0 }, [3, false]];
 * deepFilter(arr, x => x); // Returns [ { x: 2 }, [ 3 ] ]
 */
export function deepFilter(obj: any, filterFn: (el: any) => boolean): any {
  /**
   * Depth-first search helper function that processes each element
   * @param obj - Current object/array/value being processed
   * @returns Filtered value or undefined if filtered out
   */
  function dfs(obj: any): any {
    // Handle arrays - recursively filter each element
    if (Array.isArray(obj)) {
      // Process each element and remove undefined (filtered out) values
      obj = obj.map(dfs).filter(el => el !== undefined);
      // Return array only if it still has elements, otherwise undefined
      return obj.length > 0 ? obj : undefined;
    }

    // Handle objects - recursively filter each property
    if (typeof obj === 'object' && obj !== null) {
      const filteredObj: Record<string, any> = {};
      for (const key in obj) {
        // Recursively process each property value
        const value = dfs(obj[key]);
        if (value === undefined) {
          continue; // Skip properties with filtered out values
        }
        filteredObj[key] = value;
      }
      // Return object only if it still has properties, otherwise undefined
      return Object.keys(filteredObj).length > 0 ? filteredObj : undefined;
    }

    // Handle primitive values - apply the filter function directly
    return filterFn(obj) ? obj : undefined;
  }

  // Start the recursive filtering from the root object
  return dfs(obj);
}