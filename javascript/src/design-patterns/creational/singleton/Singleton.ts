interface SingletonConstructor<T> {
  new(): T;
  instance?: T;
}

export abstract class Singleton {
  public constructor() {
    const ctor = this.constructor as SingletonConstructor<unknown>;
    if (ctor.instance) {
      throw new Error("Use getInstance() instead of new");
    }
  }

  public static getInstance<T extends Singleton>(this: SingletonConstructor<T>): T {
    if (!this.instance) {
      this.instance = new this();
    }
    return this.instance;
  }
}