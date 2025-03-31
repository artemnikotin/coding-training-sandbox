import { beforeEach, describe, expect, test } from "vitest";
import { Trie } from "./trie";

let trie: Trie;

describe('Trie', () => {
  beforeEach(() => {
    trie = new Trie();
  });

  test('empty string', () => {
    expect(trie.search('')).toBe(false);
    trie.insert('');
    expect(trie.search('')).toBe(true);
  });

  test('search and prefix', () => {
    trie.insert("apple");
    expect(trie.search("apple")).toBe(true);
    expect(trie.search("app")).toBe(false);
    expect(trie.startsWith("app")).toBe(true);
    trie.insert("app");
    expect(trie.search("app")).toBe(true);
  });
});