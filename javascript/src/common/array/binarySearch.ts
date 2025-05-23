export function binarySearch<T>(values: T[], target: T, compareFn: (a: T, b: T) => number): number {
  let low = 0, high = values.length - 1;
  while (low <= high) {
    // for arrays of more than one billion elements use Math.floor((low + high) / 2)
    const mid = (low + high) >> 1;
    const ratio = compareFn(values[mid], target);
    if (ratio < 0) {
      low = mid + 1;
    } else if (ratio > 0) {
      high = mid - 1;
    } else {
      return mid;
    }
  }
  return -1;
};