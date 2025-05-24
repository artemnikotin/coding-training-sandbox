import { describe, expect, test } from "vitest";
import { topKFrequent_BucketSort, topKFrequent_PQ, topKFrequent_QuickSelect, topKFrequent_Sort } from "./topKFrequent";

describe.each([
  ['topKFrequent(PriorityQueue)', topKFrequent_PQ],
  ['topKFrequent(Sort)', topKFrequent_Sort],
  ['topKFrequent(QuickSelect)', topKFrequent_QuickSelect],
  ['topKFrequent(BucketSort)', topKFrequent_BucketSort],
])('Leetcode | 347. Top K Frequent Elements | %s', (_, topKFrequent) => {
  test('Top K frequent in empty array', () => {
    expect(topKFrequent([], 2)).toEqual([]);
  });

  test('Top K frequent in one element array', () => {
    const nums = [1, 1, 1, 2, 2, 3], k = 2;
    expect(topKFrequent(nums, k).sort()).toEqual([1, 2]);
  });

  test('Top K frequent in multi element array', () => {
    const nums = [1], k = 1;
    expect(topKFrequent(nums, k)).toEqual([1]);
  });
});