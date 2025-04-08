// Define types for JSON-compatible values and objects
type JSONValue = null | boolean | number | string | JSONValue[] | { [key: string]: JSONValue };
type Obj = Record<string, JSONValue> | Array<JSONValue>;

/**
 * Leetcode | Medium | 2705. Compact Object
 * 
 * This function creates a compacted version of an object or array by:
 * 1. Removing all falsy values (false, 0, "", null, undefined, NaN)
 * 2. Recursively compacting nested objects and arrays
 * 
 * @param obj The object or array to compact
 * @returns A new compacted object/array with all falsy values removed
 */
export function compactObject(obj: Obj): Obj {
  // Handle array case
  if (Array.isArray(obj)) {
    // First filter out falsy values, then recursively compact remaining elements
    return obj
      .filter(el => !!el)  // Remove falsy values
      .map(el => 
        typeof el === "object" ? compactObject(el as Obj) : el  // Recursively compact objects
      );
  }

  // Handle object case
  const compact: Record<string, JSONValue> = {};
  for (let [key, value] of Object.entries(obj)) {
    // Only process truthy values
    if (Boolean(value)) {
      // Recursively compact object values, leave primitives unchanged
      compact[key] = typeof value === "object" 
        ? compactObject(value as Obj) 
        : value;
    }
  }
  return compact;
}