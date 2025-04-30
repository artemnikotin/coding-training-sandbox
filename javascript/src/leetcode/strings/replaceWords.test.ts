import { describe, expect, test } from "vitest";
import { replaceWords } from "./replaceWords";

describe("Leetcode | 648. Replace Words", () => {
  test("replace all the derivatives in the sentence with the root forming it", () => {
    const dictionary = ["cat", "bat", "rat"];
    const sentence = "the cattle was rattled by the battery";
    const expected = "the cat was rat by the bat";

    expect(replaceWords(dictionary, sentence)).toBe(expected);
  });
});