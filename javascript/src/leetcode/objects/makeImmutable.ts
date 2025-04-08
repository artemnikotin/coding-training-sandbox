/**
 * Leetcode | 2692. Make Object Immutable
 */
export function makeImmutable<T extends object>(obj: T): T {
  const mutatingMethods = new Set([
    'pop',
    'push',
    'shift',
    'unshift',
    'splice',
    'sort',
    'reverse',
  ]);

  const handler: ProxyHandler<object> = {
    set(target, prop) {
      throw Array.isArray(target)
        ? `Error Modifying Index: ${String(prop)}`
        : `Error Modifying: ${String(prop)}`;
    },
    get(target, prop) {
      const key = prop as keyof typeof target;
      return prop === 'prototype' || target[key] === null
        || (typeof target[key] !== 'object' && typeof target[key] !== 'function')
        ? target[key]
        : new Proxy(target[key], handler);
    },
    apply(target: Function, thisArg, argumentsList) {
      if (mutatingMethods.has(target.name)) {
        throw `Error Calling Method: ${(target as any).name}`;
      }
      return target.apply(thisArg, argumentsList);
    },
  }
  return new Proxy(obj, handler) as T;
}