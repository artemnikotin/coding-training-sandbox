/**
 * Leetcode | 648. Replace Words | Medium
 * Replace all the derivatives in the sentence with the root forming it
 * 
 * @param dictionary Consists of of many roots 
 * @param sentence Consisting of words separated by spaces
 * @returns sentence replaced with the root that has the shortest length
 */
export function replaceWords(dictionary: string[], sentence: string): string {
  if (sentence.length === 0) {
    return "";
  }

  const words = sentence.split(" ");
  const trie = new Trie;
  for (let word of dictionary) {
    trie.insert(word);
  }
  for (let i = 0; i < words.length; i++) {
    words[i] = trie.shortestRoot(words[i]);
  }
  return words.join(" ");
};

class TrieNode {
  map: Map<string, TrieNode> = new Map;
  leaf: boolean = false;
}

class Trie {
  root = new TrieNode;

  insert(word: string): void {
    let current = this.root;
    for (let letter of word) {
      if (!current.map.has(letter)) {
        current.map.set(letter, new TrieNode);
      }
      current = current.map.get(letter)!;
    }
    current.leaf = true;
  }

  shortestRoot(word: string): string {
    let current = this.root;
    for (let i = 0; i < word.length; i++) {
      const letter = word[i];
      if (!current.map.has(letter)) {
        return word;
      }
      current = current.map.get(letter)!;
      if (current.leaf) {
        return word.substring(0, i + 1);
      }
    }
    return word;
  }
}