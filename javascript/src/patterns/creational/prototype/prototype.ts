export interface Prototype {
  clone(): this;
}

export interface ExplitPrototype<T extends ExplitPrototype<T>> {
    clone(): T;
}

export abstract class BasePrototype implements Prototype {
  clone(): this {
    // For simple cases, Object.create() maintains the prototype chain
    const clone = Object.create(this);
    // Copy properties if needed (for shallow clone)
    return Object.assign(clone, this);
  }
}