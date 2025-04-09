// Type for batch query function that takes multiple keys and returns array of results
type QueryMultipleFn = (keys: string[]) => Promise<string[]>;

// Type for tracking pending queries waiting to be batched
type PendingQuery = {
  key: string; // The query key
  resolve: (result: string) => void; // Promise resolve callback
};

/**
 * Leetcode | Hard | 2756. Query Batching
 * QueryBatcher - A class that batches individual queries into bulk requests
 * to optimize calls by combining multiple requests into a single batch.
 * 
 * Features:
 * - Collects queries during a cooldown period
 * - Executes batched queries when cooldown ends
 * - Automatically manages the batching cycle
 */
export class QueryBatcher {
  // Timer handle for the cooldown period
  private cooldown: ReturnType<typeof setTimeout> | null = null;
  // Queue of pending queries waiting to be batched
  private pendingQueries: PendingQuery[] = [];

  /**
   * @param queryMultiple The batch query function to use
   * @param t Cooldown time in milliseconds between batch executions
   */
  constructor(private queryMultiple: QueryMultipleFn, private t: number) {}

  /**
   * Gets a value by key, either immediately or via batching
   * @param key The key to query
   * @returns Promise resolving to the query result
   */
  async getValue(key: string) {
    if (!this.cooldown) {
      // If no cooldown active, start new batch cycle
      this.cooldown = setTimeout(this.tick, this.t);
      // Execute immediate query since we're first in batch window
      const results = await this.queryMultiple([key]);
      return results[0];
    } else {
      // If in cooldown period, add to pending queue
      return new Promise((resolve) => {
        this.pendingQueries.push({ key, resolve });
      });
    }
  }

  /**
   * Processes pending queries when cooldown timer triggers
   */
  private tick = async () => {
    if (this.pendingQueries.length === 0) {
      // No pending queries - reset cooldown
      this.cooldown = null;
    } else {
      // More queries pending - restart cooldown timer
      this.cooldown = setTimeout(this.tick, this.t);
      
      // Take snapshot of current pending queries
      const tasks = [...this.pendingQueries];
      this.pendingQueries = []; // Clear the queue
      
      // Extract keys and resolve callbacks
      const keys = tasks.map(obj => obj.key);
      const resolves = tasks.map(obj => obj.resolve);
      
      // Execute batch query
      const res = await this.queryMultiple(keys);
      
      // Resolve all pending promises with their results
      for (let i = 0; i < keys.length; i++) {
        resolves[i](res[i]);
      }
    }
  };
}