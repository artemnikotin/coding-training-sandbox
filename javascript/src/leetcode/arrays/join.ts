// Define types for JSON-compatible values and objects with required "id" property
type JSONValue = null | boolean | number | string | JSONValue[] | { [key: string]: JSONValue };
type ArrayType = { "id": number } & Record<string, JSONValue>;  // Objects must have numeric id plus any other JSON properties

/**
 * Leetcode | Medium | 2722. Join Two Arrays by ID
 * 
 * Merges two arrays of objects by their "id" property, combining properties when ids match.
 * Similar to a SQL JOIN operation, but with last-seen values taking precedence.
 * 
 * @param arr1 First array of objects (each must have an "id" property)
 * @param arr2 Second array of objects (each must have an "id" property)
 * @returns New merged array sorted by id with combined objects where ids match
 * 
 * @example
 * const arr1 = [{id: 1, x: 1}, {id: 2, x: 2}];
 * const arr2 = [{id: 2, x: 3, y: 2}, {id: 3, x: 4}];
 * join(arr1, arr2); // [{id: 1, x: 1}, {id: 2, x: 3, y: 2}, {id: 3, x: 4}]
 */
export function join(arr1: ArrayType[], arr2: ArrayType[]): ArrayType[] {
  // Sort both arrays by id for efficient merge
  arr1.sort(compare);
  arr2.sort(compare);

  // Initialize pointers and result array
  let i1 = 0;  // Pointer for arr1
  let i2 = 0;  // Pointer for arr2
  const result = [];

  // Merge while both arrays have elements
  while (i1 < arr1.length && i2 < arr2.length) {
    const o1 = arr1[i1];
    const o2 = arr2[i2];

    if (o1.id < o2.id) {
      // arr1 has smaller id - take from arr1
      result.push(o1);
      i1++;
    } else if (o1.id > o2.id) {
      // arr2 has smaller id - take from arr2
      result.push(o2);
      i2++;
    } else {
      // Matching ids - merge objects (arr2 properties overwrite arr1)
      result.push({...o1, ...o2});
      i1++;
      i2++;
    }
  }

  // Add remaining elements from arr1 (if any)
  while (i1 < arr1.length) {
    result.push(arr1[i1]);
    i1++;
  }

  // Add remaining elements from arr2 (if any)
  while (i2 < arr2.length) {
    result.push(arr2[i2]);
    i2++;
  }

  return result;
};

/**
 * Comparator function for sorting objects by their "id" property
 * @param o1 First object to compare
 * @param o2 Second object to compare
 * @returns -1 if o1.id < o2.id, 1 otherwise (simple ascending sort)
 */
function compare(o1: ArrayType, o2: ArrayType) {
  return o1.id < o2.id ? -1 : 1;
}