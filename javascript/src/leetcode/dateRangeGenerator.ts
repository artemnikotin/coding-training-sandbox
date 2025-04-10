/**
 * A generator function that yields dates in a range with a specified step.
 * Leetcode | Medium | 2777 - Date Range Generator
 * 
 * @param start - The starting date string in ISO format (YYYY-MM-DD)
 * @param end - The ending date string in ISO format (YYYY-MM-DD)
 * @param step - The number of days to increment between each date
 * @yields {string} - Dates in ISO format (YYYY-MM-DD) without time component
 * 
 * @example
 * const gen = dateRangeGenerator('2023-01-01', '2023-01-05', 1);
 * gen.next().value; // '2023-01-01'
 * gen.next().value; // '2023-01-02'
 * // etc...
 */
export function* dateRangeGenerator(
  start: string,
  end: string,
  step: number
): Generator<string> {
  // Convert input strings to Date objects for comparison and manipulation
  const startDate = new Date(start);
  const endDate = new Date(end);

  // Loop while current date hasn't passed the end date
  while (startDate <= endDate) {
    // Yield the current date in YYYY-MM-DD format
    yield startDate.toISOString().slice(0, 10);

    // Increment the date by the specified step (in days)
    startDate.setDate(startDate.getDate() + step);
  }
}