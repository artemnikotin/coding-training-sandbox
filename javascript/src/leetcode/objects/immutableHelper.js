/**
 * Leetcode | 2691. Immutability Helper
 */
export class ImmutableHelper {
  constructor(obj) {
    this.obj = obj;
  }

  produce(mutator) {
    const proxyObj = ImmutableHelper.createProxyObj(this.obj);
    mutator(proxyObj);
    proxyObj[ImmutableHelper.STOP_WRAP_METHOD]();
    return proxyObj;
  }

  static STOP_WRAP_METHOD = Symbol();

  static createProxyObj(obj) {
    const proxies = new Map();
    const overwrites = new Map();
    let noWrap = false;

    const stopWrap = () => {
      noWrap = true;
      for (let [_, proxy] of proxies) {
        proxy[this.STOP_WRAP_METHOD]();
      }
    }

    return new Proxy(obj, {
      get: (target, prop) => {
        if (overwrites.has(prop)) {
          return overwrites.get(prop);
        }
        if (proxies.has(prop)) {
          return proxies.get(prop);
        }
        if (prop === this.STOP_WRAP_METHOD) {
          return stopWrap;
        }

        const value = Reflect.get(target, prop);
        if (!noWrap && this.isObject(value)) {
          const proxy = this.createProxyObj(value);
          proxies.set(prop, proxy);
          return proxy;
        }
        return value;
      },

      set: (target, prop, value) => {
        if (noWrap) {
          return Reflect.set(target, prop, value);
        }
        proxies.delete(prop);
        overwrites.set(prop, value);
        return true;
      },

      ownKeys: (target) => {
        return Array.from((new Set(Reflect.ownKeys(target))).union(overwrites));
      },

      getOwnPropertyDescriptor(target, prop) {
        if (overwrites.has(prop)) {
          return {
            enumerable: true,
            configurable: true
          };
        }
        return Reflect.getOwnPropertyDescriptor(target, prop);
      }
    });
  }

  static isObject(obj) {
    return obj !== null && typeof obj === 'object';
  }
}