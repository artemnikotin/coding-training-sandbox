/**
 * Leetcode | Medium | 692. Top K Frequent Words
 */
export function topKFrequentWords(words: string[], k: number): string[] {
  // 1. Build hash map: number and how often it appears
  const counter: Map<string, number> = new Map;
  let maxFrequent = 0;
  for (let word of words) {
    const curFrequency = (counter.get(word) ?? 0) + 1;
    counter.set(word, curFrequency);
    maxFrequent = Math.max(maxFrequent, curFrequency);
  }

  // 2. Build a frequency array: frequency => element[]
  const frequency: string[][] = Array(maxFrequent + 1);
  for (let [word, freq] of counter.entries()) {
    if (!frequency[freq]) {
      frequency[freq] = [];
    }
    frequency[freq].push(word);
  }

  // 3. Build a result from frequency array
  const result = [];
  for (let i = frequency.length; i > 0; i--) {
    if (frequency[i]) {
      frequency[i].sort();
      for (let num of frequency[i]) {
        result.push(num);
        if (result.length === k) {
          return result;
        }
      }
    }
  }
  return result;
}