/**
 * A Trie (prefix tree) data structure implementation.
 * Efficient for storing and searching strings with common prefixes.
 */
export class Trie {
    private root: TrieNode;
  
    /**
     * Constructs a new Trie with an empty root node.
     */
    constructor() {
        this.root = new TrieNode();
    }
  
    /**
     * Inserts a word into the trie.
     * @param word - The string to be inserted into the trie
     */
    insert(word: string): void {
        let current = this.root;
        // Traverse through each character of the word
        for (let letter of word) {
            // If the current node doesn't have this letter as a child,
            // create a new node and add it
            if (!current.map.has(letter)) {
                current.map.set(letter, new TrieNode());
            }
            // Move to the child node corresponding to the current letter
            current = current.map.get(letter)!;
        }
        // Mark the end of the complete word
        current.leaf = true;
    }
  
    /**
     * Searches for a complete word in the trie.
     * @param word - The string to search for
     * @returns True if the word exists in the trie, false otherwise
     */
    search(word: string): boolean {
        let current = this.root;
        for (let letter of word) {
            // If any letter in the word is not found, return false
            if (!current.map.has(letter)) {
                return false;
            }
            current = current.map.get(letter)!;
        }
        // Only return true if we're at a leaf node (end of a complete word)
        return current.leaf === true;
    }
  
    /**
     * Checks if any word in the trie starts with the given prefix.
     * @param prefix - The prefix to search for
     * @returns True if the prefix exists in the trie, false otherwise
     */
    startsWith(prefix: string): boolean {
        let current = this.root;
        for (let letter of prefix) {
            // If any letter in the prefix is not found, return false
            if (!current.map.has(letter)) {
                return false;
            }
            current = current.map.get(letter)!;
        }
        // The prefix exists if we've successfully traversed all its letters
        return true;
    }
  }
  
  /**
   * Represents a single node in the Trie.
   * Each node contains child nodes and a flag indicating if it's a complete word.
   */
  class TrieNode {
    /**
     * A map of child nodes, where the key is a character
     * and the value is the corresponding TrieNode.
     */
    public map: Map<string, TrieNode>;
  
    /**
     * Indicates whether this node represents the end of a complete word.
     */
    public leaf: boolean; 
    
    /**
     * Constructs a new TrieNode with an empty child map
     * and leaf flag set to false.
     */
    constructor() {
        this.map = new Map();
        this.leaf = false;
    }
  }