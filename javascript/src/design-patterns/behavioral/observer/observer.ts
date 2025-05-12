export interface Observer<T> {
  subscribe(subscriber: Subscriber<T>): void;
  unsubscribe(subscriber: Subscriber<T>): void;
}

export interface Subscriber<T> {
  apply(param: T): void;
}

export abstract class AbstractObserver<T> implements Observer<T> {
  private subscribers: Set<Subscriber<T>> = new Set;

  subscribe(subscriber: Subscriber<T>): void {
    this.subscribers.add(subscriber);
  }

  unsubscribe(subscriber: Subscriber<T>): void {
    this.subscribers.delete(subscriber);
  }

  protected notifySubscribers(param: T) {
    this.subscribers.forEach(subscriber => subscriber.apply(param));
  }
}