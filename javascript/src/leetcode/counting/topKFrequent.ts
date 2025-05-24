import { quickSelectInPlace } from "../../common/array/quickSelect";
import { PriorityQueue } from "../../data-structures/queue/PriorityQueue";

/**
 * Leetcode | Medium | 347. Top K Frequent Elements
 */

type NumberFrequency = [number, number];

export function topKFrequent_PQ(nums: number[], k: number): number[] {
  // 1. Build hash map: number and how often it appears
  const counter: Map<number, number> = new Map;
  for (let num of nums) {
    counter.set(num, (counter.get(num) ?? 0) + 1);
  }

  // 2. Keep k top frequent elements in the heap
  const pq = new PriorityQueue<NumberFrequency>((a, b) => a[1] - b[1]);
  for (let entry of counter.entries()) {
    pq.enqueue(entry);
    if (pq.size() > k) {
      pq.dequeue();
    }
  }

  // 3. Build an output array
  return [...pq].map(el => el[0]);
};

export function topKFrequent_Sort(nums: number[], k: number): number[] {
  // 1. Build hash map: number and how often it appears
  const counter: Map<number, number> = new Map;
  for (let num of nums) {
    counter.set(num, (counter.get(num) ?? 0) + 1);
  }

  // 2. Sort counter map (from top frequent)
  const sorted = [...counter.entries()].sort((a, b) => b[1] - a[1]).map(el => el[0]);

  // 3. Slice first k numbers
  return sorted.slice(0, k);
};

export function topKFrequent_QuickSelect(nums: number[], k: number): number[] {
  // 1. Build hash map: number and how often it appears
  const counter: Map<number, number> = new Map;
  for (let num of nums) {
    counter.set(num, (counter.get(num) ?? 0) + 1);
  }

  // 2. Use QuickSelect on counter map
  const partlySorted = [...counter.entries()];
  quickSelectInPlace(partlySorted, k, (a, b) => b[1] - a[1]);

  // 3. Slice first k numbers
  return partlySorted.slice(0, k).map(el => el[0]);
};

export function topKFrequent_BucketSort(nums: number[], k: number): number[] {
  // 1. Build hash map: number and how often it appears
  const counter: Map<number, number> = new Map;
  let maxFrequent = 0;
  for (let num of nums) {
    const curFrequency = (counter.get(num) ?? 0) + 1;
    counter.set(num, curFrequency);
    maxFrequent = Math.max(maxFrequent, curFrequency);
  }

  // 2. Build a frequency array: frequency => element[]
  const frequency: number[][] = Array(maxFrequent + 1);
  for (let [num, freq] of counter.entries()) {
    if (!frequency[freq]) {
      frequency[freq] = [];
    }
    frequency[freq].push(num);
  }

  // 3. Build a result from frequency array
  const result = [];
  for (let i = frequency.length; i > 0; i--) {
    if (frequency[i]) {
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