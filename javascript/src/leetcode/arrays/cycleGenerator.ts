/** 
 * Leetcode | Medium | 2757. Generate Circular Array Values
 * 
 * A generator function that yields values from an array in a circular manner,
 * adjusting the current index based on provided jump values.
 * 
 * @param arr The array to cycle through
 * @param startIndex The initial position in the array
 * @yields The current array value at the calculated index
 * @returns Never returns (infinite generator)
 * 
 * @example
 * const gen = cycleGenerator([1,2,3,4], 0);
 * gen.next().value; // 1 (start at index 0)
 * gen.next(1).value; // 2 (0 + 1 = index 1)
 * gen.next(2).value; // 4 (1 + 2 = index 3)
 * gen.next(-1).value; // 3 (3 - 1 = index 2)
 */
export function* cycleGenerator(
  arr: number[], 
  startIndex: number
): Generator<number, never, number> {
  const n = arr.length;
  
  while (true) {
    // Yield current value and receive jump amount from next()
    const jump = yield arr[startIndex];
    
    // Calculate new index with proper circular wrapping:
    // 1. (startIndex + jump) % n - gets the raw next position
    // 2. + n - ensures positive value before final modulo
    // 3. % n - final circular wrapping
    startIndex = (((startIndex + jump) % n) + n) % n;
  }
}