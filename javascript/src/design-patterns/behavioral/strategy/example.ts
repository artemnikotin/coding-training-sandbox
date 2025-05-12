interface SortStrategy {
  sort(data: number[]): number[];
}

export class BubbleSort implements SortStrategy {
  sort(data: number[]): number[] {
    // Implementation omitted for brevity
    return [...data].sort((a, b) => a - b);
  }
}

export class QuickSort implements SortStrategy {
  sort(data: number[]): number[] {
    // Implementation omitted for brevity
    return [...data].sort((a, b) => a - b);
  }
}

export class MergeSort implements SortStrategy {
  sort(data: number[]): number[] {
    // Implementation omitted for brevity
    return [...data].sort((a, b) => a - b);
  }
}

export class SorterContext {
  private strategy: SortStrategy;

  constructor(strategy: SortStrategy = new BubbleSort()) {
    this.strategy = strategy;
  }

  setStrategy(strategy: SortStrategy): void {
    this.strategy = strategy;
  }

  sort(data: number[]): number[] {
    return this.strategy.sort(data);
  }
}