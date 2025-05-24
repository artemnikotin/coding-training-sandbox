/**
 * Generates a random integer between min (inclusive) and max (exclusive)
 * 
 * @param {number} min - The minimum value (inclusive)
 * @param {number} max - The maximum value (exclusive)
 * @returns {number} A random integer between min (included) and max (excluded)
 * 
 * @example
 * // Returns a random number between 1 (included) and 10 (excluded)
 * randomInt(1, 10);
 * 
 * @note
 * - Uses Math.random() which is cryptographically not secure
 * - For cryptographic purposes, use crypto.getRandomValues() instead
 * - The minimum is inclusive, the maximum is exclusive
 */
export function randomInt(min: number, max: number): number {
  return Math.floor(Math.random() * (max - min) + min);
}