/**
 * Leetcode | Medium | 2700. Differences Between Two Objects
 * 
 * This function compares two objects and returns their differences.
 * It recursively compares nested objects and only returns paths where values differ.
 * 
 * @param o1 First object to compare
 * @param o2 Second object to compare
 * @returns An object containing the differences, or an array if primitive values differ
 */
export function objDiff(o1: any, o2: any) {
  // If the types are different, return the differing values immediately
  if (type(o1) !== type(o2)) {
    return [o1, o2];
  }

  // If the values are primitives and not equal, return the differing values
  if (!isObject(o1)) {
    return o1 === o2 ? {} : [o1, o2];
  }

  // Initialize an object to store the differences
  const diff: Record<string, unknown> = {};

  // Compare only keys that exist in both objects
  Object.keys(o1)
    .filter(key => key in o2)
    .forEach(key => {
      // Recursively compare nested properties
      const subDiff = objDiff(o1[key], o2[key]);
      
      // Only add to the result if there are actual differences
      if (Object.keys(subDiff).length) {
        diff[key] = subDiff;
      }
    });

  return diff;
}

/**
 * Helper function to get precise type of an object
 * @param obj The object to check
 * @returns The object's type as a string (e.g., "Object", "Array", "Date")
 */
function type(obj: any): string {
  // Uses Object.prototype.toString to get accurate type for all objects
  return Object.prototype.toString.call(obj).slice(8, -1);
}

/**
 * Helper function to check if a value is a non-null object
 * @param obj The value to check
 * @returns True if the value is an object and not null
 */
function isObject(obj: any): boolean {
  return typeof obj === 'object' && obj !== null;
}