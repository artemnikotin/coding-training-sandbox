// Export empty object to make this a module (TypeScript requirement)
export { };

// Declare extension to global Array interface
declare global {
  interface Array<T> {
    /**
     * Leetcode | Easy | Leetcode | 2774. Array Upper Bound
     * Finds the last occurrence (upper bound) of a target number in a sorted array
     * using binary search.
     * @param target The number to search for
     * @returns The index of the last occurrence, or -1 if not found
     */
    upperBound(target: number): number;
  }
}

// Implementation of the upperBound method using binary search
Array.prototype.upperBound = function (target: number) {
  // Initialize binary search pointers
  let left = 0;
  let right = this.length; // Exclusive bound

  // Binary search to find insertion point of target+1 (upper bound)
  while (left < right) {
    const mid = (left + right) >> 1; // Fast division by 2

    // Narrow search to left half if middle element exceeds target
    if (this[mid] > target) {
      right = mid;
    }
    // Otherwise search right half (including current mid)
    else {
      left = mid + 1;
    }
  }

  // After loop, left points to first element > target
  // Check if previous element equals target
  return left > 0 && this[left - 1] == target
    ? left - 1  // Return last occurrence
    : -1;       // Not found
};