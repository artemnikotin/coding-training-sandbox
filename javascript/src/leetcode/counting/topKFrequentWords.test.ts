import { describe, expect, test } from "vitest"
import { topKFrequentWords } from "./topKFrequentWords";

describe('Leetcode | 692. Top K Frequent Words', () => {
  test("Most frequent occurance", () => {
    const words = ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4;
    const output = ["the", "is", "sunny", "day"];
    expect(topKFrequentWords(words, k)).toEqual(output);
  });

  test("Result in lower alphabetical order", () => {
    const words = ["i", "love", "leetcode", "i", "love", "coding"], k = 2;
    const output = ["i", "love"];
    expect(topKFrequentWords(words, k)).toEqual(output);
  });
});