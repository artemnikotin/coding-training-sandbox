export interface Originator<S> {
  save(): Memento<S>;
  restore(snapshot: Memento<S>): void;
}

export interface Memento<S> {
  getState(): S;
}