/**
 * Leetcode | Medium | 2755. Deep Merge of Two Objects
 * 
 * Recursively merges two objects deeply, combining their properties.
 * Arrays and objects are merged recursively, while primitive values are overwritten.
 * 
 * @param obj1 The base object to merge into
 * @param obj2 The object whose properties will be merged into obj1
 * @returns A new object containing the merged properties
 * 
 * @example
 * const obj1 = { a: 1, b: { c: 2 } };
 * const obj2 = { b: { d: 3 }, e: 4 };
 * deepMerge(obj1, obj2); // Returns { a: 1, b: { c: 2, d: 3 }, e: 4 }
 */
export function deepMerge(obj1: any, obj2: any): any {
  // Helper function to check if a value is a non-null object
  const isObj = (obj: any) => obj !== null && typeof obj === 'object';
  
  // Helper function to check if a value is an array
  const isArr = (obj: any) => Array.isArray(obj);

  // If either object is not an object type, return obj2 (no merge possible)
  if (!isObj(obj1) || !isObj(obj2)) {
    return obj2;
  }

  // If one is an array and the other isn't, return obj2 (can't merge different types)
  if (isArr(obj1) !== isArr(obj2)) {
    return obj2;
  }

  // Recursively merge each property from obj2 into obj1
  for (const key in obj2) {
    obj1[key] = deepMerge(obj1[key], obj2[key]);
  }

  // Return the merged object (note: mutates obj1)
  return obj1;
}