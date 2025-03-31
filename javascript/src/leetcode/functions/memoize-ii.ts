/**
 * Leetcode | Hard | 2630. Memoize II
 * 
 * This module provides two implementations of memoization functions:
 * 1. memoizeTrie - Uses a trie data structure for memoization
 * 2. memoizeMap - Uses a Map-based approach for memoization
 */

// Type definition for a generic function
type Fn = (...params: any[]) => any;

/**
 * Memoizes a function using a trie data structure to cache results.
 * The trie approach is particularly efficient for nested object parameters.
 * @param fn The function to memoize
 * @returns A memoized version of the input function
 */
export function memoizeTrie<F extends Fn>(fn: F): F {
  // Initialize a new trie for storing cached values
  let trie = new LookupTrie;
  
  // Return a new function that checks the cache before calling the original function
  return ((...params: any[]) => {
    // Check if the value exists in the trie
    if (trie.hasValue(params)) {
      return trie.getValue(params);
    }
    // Compute and cache the result if not found
    const value = fn(...params);
    trie.setValue(params, value);
    return value;
  }) as F;
}

/**
 * A trie node class used by LookupTrie for storing cached values.
 * Each node can have multiple children and optionally store a value.
 */
class LookupTrieNode {
  readonly children = new Map<any, LookupTrieNode>(); // Child nodes
  valuable = false; // Flag indicating if this node stores a value
  value: any = null; // The actual cached value
}

/**
 * A trie implementation for memoization purposes.
 * Stores values along paths of function arguments.
 */
class LookupTrie {
  readonly root = new LookupTrieNode; // The root node of the trie

  /**
   * Checks if a value exists for the given path (function arguments)
   * @param path Array of function arguments
   * @returns True if a value exists for this path
   */
  hasValue(path: any[]) {
    let current = this.root;
    // Traverse the trie following the path of arguments
    for (let arg of path) {
      if (!current.children.has(arg)) {
        return false;
      }
      current = current.children.get(arg)!;
    }
    return current.valuable;
  }

  /**
   * Retrieves a value for the given path (function arguments)
   * @param path Array of function arguments
   * @returns The cached value or null if not found
   */
  getValue(path: any[]) {
    let current = this.root;
    // Traverse the trie following the path of arguments
    for (let arg of path) {
      if (!current.children.has(arg)) {
        return null;
      }
      current = current.children.get(arg)!;
    }
    return current.valuable ? current.value : null;
  }

  /**
   * Stores a value for the given path (function arguments)
   * @param path Array of function arguments
   * @param value The value to cache
   */
  setValue(path: any[], value: any) {
    let current = this.root;
    // Traverse the trie, creating nodes as needed
    for (let arg of path) {
      if (!current.children.has(arg)) {
        current.children.set(arg, new LookupTrieNode);
      }
      current = current.children.get(arg)!;
    }
    // Mark the node as valuable and store the value
    current.valuable = true;
    current.value = value;
  }
}

/**
 * Memoizes a function using a Map-based approach.
 * This implementation assigns unique IDs to each parameter and uses them as cache keys.
 * @param fn The function to memoize
 * @returns A memoized version of the input function
 */
export function memoizeMap<F extends Fn>(fn: F): F {
  // Maps parameters to unique IDs for consistent key generation
  const idMap = new Map<any, number>();
  // The cache storing computed results
  const cache = new Map<string, any>();
  
  /**
   * Gets or creates a unique ID for a parameter
   * @param param The function parameter
   * @returns A unique ID for the parameter
   */
  const getIndex = (param: any) => {
    if (!idMap.has(param)) {
      idMap.set(param, idMap.size);
    }
    return idMap.get(param)!;
  }

  // Return a new function that checks the cache before calling the original function
  return (function (...args) {
    // Generate a cache key by joining parameter IDs
    const key = args.map(getIndex).join(',');
    if (cache.has(key)) {
      return cache.get(key);
    }
    // Compute and cache the result if not found
    const result = fn(...args);
    cache.set(key, result);
    return result;
  }) as F;
}