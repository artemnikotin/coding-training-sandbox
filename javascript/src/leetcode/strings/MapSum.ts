/**
 * Leetcode | 677. Map Sum Pairs | Medium
 * Design a map that allows you to do the following:
 *   - Maps a string key to a given value.
 *   - Returns the sum of the values that have a key with a prefix equal to a given string.
 */
export class MapSum {
  private readonly root: TrieNode;

  constructor() {
    this.root = new TrieNode;
  }

  insert(key: string, val: number): void {
    const oldVal = this.get(key) ?? 0;
    if (oldVal === val) {
      return;
    }

    const delta = val - oldVal;
    let current = this.root;
    current.prefixScore += delta;
    for (let letter of key) {
      if (!current.map.has(letter)) {
        current.map.set(letter, new TrieNode());
      }
      current = current.map.get(letter)!;
      current.prefixScore += delta;
    }
    current.wordScore = val;
  }

  sum(prefix: string): number {
    let current = this.root;
    for (let letter of prefix) {
      if (!current.map.has(letter)) {
        return 0;
      }
      current = current.map.get(letter)!;
    }
    return current.prefixScore;
  }

  private get(key: string): number {
    let current = this.root;
    for (let letter of key) {
      if (!current.map.has(letter)) {
        return 0;
      }
      current = current.map.get(letter)!;
    }
    return current.wordScore;
  }
}

class TrieNode {
  map: Map<string, TrieNode> = new Map();
  prefixScore: number = 0;
  wordScore: number = 0;
}