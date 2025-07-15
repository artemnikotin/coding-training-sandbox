/**
 * Leetcode | Medium | 2628. JSON Deep Equal
 * This function checks if two objects or arrays are deeply equal.
 * @param o1 - The first object or array to compare.
 * @param o2 - The second object or array to compare.
 * @returns {boolean} - Returns true if the objects or arrays are deeply equal, otherwise false.
 */
export function areDeeplyEqual(o1: any, o2: any): boolean {
  // If both objects are the same reference or both are primitive values and equal, return true.
  if (Object.is(o1, o2)) {
    return true;
  }

  // If both are arrays, check if they have the same length.
  if (Array.isArray(o1) && Array.isArray(o2)) {
    if (o1.length !== o2.length) {
      return false;
    }

    // Recursively check each element in the arrays.
    return o1.every((el, i) => areDeeplyEqual(el, o2[i]));
  } else if (Array.isArray(o1) || Array.isArray(o2)) {
    // If one is an array and the other is not, they are not equal.
    return false;
  }

  // If both are objects, check if they have the same number of keys.
  if (typeof o1 === 'object' && typeof o2 === 'object') {
    const keys = Object.keys(o1);
    if (keys.length !== Object.keys(o2).length) {
      return false;
    }

    // Recursively check each value corresponding to the keys in both objects.
    return keys.every(key => areDeeplyEqual(o1[key], o2[key]));
  }

  // If none of the above conditions are met, the objects are not equal.
  return false;
};