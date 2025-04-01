/**
 * Leetcode | 2691. Immutability Helper
 */

type ProxyState = {
  overrides: Map<string | symbol, any>; // Track direct property modifications
  proxies: Map<PropertyKey, any>;       // Track child object proxies
};

export class ImmutableHelper<T extends object> {
  constructor(private readonly target: T) {
    // Store the original immutable target object
  }

  /**
   * Produces a new immutable state by applying mutations to a draft proxy
   * @param mutator - Function that receives a draft proxy for mutation
   * @returns New immutable state with applied changes
   */
  produce(mutator: (draft: T) => void): T {
    // Create a proxy object that intercepts mutations
    const proxy = ImmutableHelper.createProxy(this.target);

    // Apply user mutations to the proxy
    mutator(proxy as T);

    // Convert proxy changes into a new frozen immutable object
    return ImmutableHelper.finalize(this.target, (proxy as any)[ImmutableHelper.PROXY_STATE]) as T;
  }

  // Symbol used to track internal proxy state
  private static PROXY_STATE = Symbol('ProxyState');

  /**
   * Creates a draft proxy that tracks mutations
   * @param target - The object to proxy
   * @returns Proxy object with mutation tracking
   */
  private static createProxy<T extends object>(target: T) {
    // Internal state storage for tracking changes
    const state: ProxyState = {
      overrides: new Map(),
      proxies: new Map(),
    };

    // Proxy handler with traps for property access/mutation
    const handler: ProxyHandler<T> = {
      get(target, prop, receiver) {
        // Handle access to internal state symbol
        if (prop === ImmutableHelper.PROXY_STATE) {
          return state;
        }

        // Return modified value if exists
        if (state.overrides.has(prop)) {
          return state.overrides.get(prop);
        }

        // Return existing child proxy if exists
        if (state.proxies.has(prop)) {
          return state.proxies.get(prop);
        }

        // Get original value
        const value = Reflect.get(target, prop, receiver);

        // Recursively proxy nested objects
        if (typeof value === 'object' && value !== null) {
          const proxy = ImmutableHelper.createProxy(value);
          state.proxies.set(prop, proxy);
          return proxy;
        }

        return value;
      },

      set(_, prop, value) {
        // Store direct modifications in overrides map
        state.overrides.set(prop, value);
        // Clear any existing child proxy for this property
        state.proxies.delete(prop);
        return true;
      },

      ownKeys(target) {
        // Combine original keys with modified property keys
        return Array.from(
          new Set([...Reflect.ownKeys(target), ...state.overrides.keys()])
        );
      },

      getOwnPropertyDescriptor(target, prop) {
        // Return descriptors for modified properties
        if (state.overrides.has(prop)) {
          return {
            value: state.overrides.get(prop),
            writable: true,
            enumerable: true,
            configurable: true
          };
        }
        return Reflect.getOwnPropertyDescriptor(target, prop);
      }
    };

    return new Proxy(target, handler);
  }

  /**
   * Converts proxy state into a final immutable object
   * @param original - Original base object
   * @param state - Proxy's internal change tracking state
   * @returns Frozen immutable object with all changes applied
   */
  private static finalize(original: Object, state: ProxyState): Object {
    // Create shallow copy of original (array or object)
    const copy: any = Array.isArray(original)
      ? [...original]
      : { ...original };

    // Apply direct property modifications
    for (const [key, value] of state.overrides) {
      copy[key] = value;
    }

    // Recursively finalize child proxies
    for (const [key, proxy] of state.proxies) {
      const childState = proxy[ImmutableHelper.PROXY_STATE];
      copy[key] = ImmutableHelper.finalize(
        Reflect.get(original, key), // Use original value as base
        childState                  // Pass child proxy's state
      );
    }

    // Freeze the final object
    return Object.freeze(copy);
  }
}