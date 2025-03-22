type Callback = (...args: any[]) => any;
type Subscription = {
  unsubscribe: () => void
}

/**
 * Leetcode | 2694. Event Emitter
 * The EventEmitter should allow for subscribing to events and emitting them.
 */
export class EventEmitter {
  // A private Map to store event names as keys and Sets of callbacks as values
  private readonly events: Map<string, Set<Callback>> = new Map;

  /**
   * Subscribes to an event with a given callback.
   * @param eventName - The name of the event to subscribe to.
   * @param callback - The callback function to be invoked when the event is emitted.
   * @returns A subscription object with an `unsubscribe` method to remove the callback.
   */
  subscribe(eventName: string, callback: Callback): Subscription {
    // If the event does not exist in the Map, initialize it with a new Set
    if (!this.events.has(eventName)) {
      this.events.set(eventName, new Set);
    }
    const callbacks = this.events.get(eventName)!;
    callbacks.add(callback);

    return {
      unsubscribe: () => {
        callbacks.delete(callback);
      }
    };
  }

  /**
   * Emits an event, invoking all subscribed callbacks with the provided arguments.
   * @param eventName - The name of the event to emit.
   * @param args - An array of arguments to pass to the callbacks.
   * @returns An array of results from the invoked callbacks.
   */
  emit(eventName: string, args: any[] = []): any[] {
    const callbacks = this.events.get(eventName);
    // If no callbacks are subscribed to the event, return an empty array
    if (!callbacks) {
      return [];
    }
    // JavaScript Sets maintain insertion order, so callbacks are invoked in the order they were added
    return Array.from(callbacks).map(cb => cb(...args));
  }
}