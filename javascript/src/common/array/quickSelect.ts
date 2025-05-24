import { randomInt } from "../number/random";

/**
 * Selects the k-th smallest element in the array using the QuickSelect (Hoare's selection) algorithm.
 * This modifies the array in place.
 *
 * @template T - The type of elements in the array.
 * @param arr - The array to search within.
 * @param k - The index (0-based) of the element to find.
 * @param compareFn - Comparison function similar to the one used in Array.prototype.sort.
 * @returns The element that would be at index `k` in a sorted version of the array.
 */
export function quickSelectInPlace<T>(arr: T[], k: number, compareFn: (a: T, b: T) => number) {
  quickSelectSlice(arr, 0, arr.length - 1, k, compareFn);
  return arr[k];
}

/**
 * Recursive helper function that performs the QuickSelect algorithm
 * on a portion of the array from index `left` to `right`.
 *
 * @param arr - The array to process.
 * @param left - The left boundary of the current subarray.
 * @param right - The right boundary of the current subarray.
 * @param k - The index of the desired element.
 * @param compareFn - Function used to compare elements.
 */
function quickSelectSlice<T>(arr: T[], left: number, right: number, k: number, compareFn: (a: T, b: T) => number) {
  // base cases: the list contains no or one element
  if (right <= left) {
    return;
  }

  //Select a random pivot_index
  let pivotIndex = left + randomInt(0, right - left);

  // Find the pivot position in a sorted list
  pivotIndex = partition(arr, left, right, pivotIndex, compareFn);

  // If the pivot is in its final sorted position
  if (k === pivotIndex) {
    return;
  } else if (k < pivotIndex) { // go left
    quickSelectSlice(arr, left, pivotIndex - 1, k, compareFn);
  } else { // go right
    quickSelectSlice(arr, pivotIndex + 1, right, k, compareFn);
  }
}

/**
 * Partitions the array such that all elements less than the pivot are to its left,
 * and all elements greater are to its right. Returns the final position of the pivot.
 * implements Lomuto's Partition Scheme
 *
 * @param arr - The array to partition.
 * @param left - Left index of the partitioning range.
 * @param right - Right index of the partitioning range.
 * @param pivotIndex - Index of the pivot element.
 * @param compareFn - Function used to compare elements.
 * @returns The index where the pivot element ends up.
 */
function partition<T>(arr: T[], left: number, right: number, pivotIndex: number, compareFn: (a: T, b: T) => number) {
  const pivotElement = arr[pivotIndex];

  // 1. Move pivot to end
  swap(arr, pivotIndex, right);
  let storeIndex = left;

  // 2. Move all less frequent elements to the left
  for (let i = left; i <= right; i++) {
    if (compareFn(arr[i], pivotElement) < 0) {
      swap(arr, storeIndex, i);
      storeIndex++;
    }
  }

  // 3. Move the pivot to its final place
  swap(arr, storeIndex, right);

  return storeIndex;
}

/**
 * Swaps two elements in an array.
 *
 * @param arr - The array containing the elements to swap.
 * @param i - Index of the first element.
 * @param j - Index of the second element.
 */
function swap<T>(arr: T[], i: number, j: number) {
  const temp = arr[i];
  arr[i] = arr[j];
  arr[j] = temp;
}