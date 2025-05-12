import { describe, expect, test, vi } from "vitest";
import { SorterContext, QuickSort, MergeSort, BubbleSort } from "./example";

describe("DesignPattern/Strategy", () => {
  test("Apply different sort strategies", () => {
    const bubble = vi.spyOn(BubbleSort.prototype, "sort");
    const quick = vi.spyOn(QuickSort.prototype, "sort");
    const merge = vi.spyOn(MergeSort.prototype, "sort");

    const data = [5, 2, 8, 1, 9];
    const sorter = new SorterContext();
    sorter.sort(data);

    sorter.setStrategy(new QuickSort());
    sorter.sort(data);

    sorter.setStrategy(new MergeSort());
    sorter.sort(data);

    expect(bubble).toBeCalledTimes(1);
    expect(quick).toBeCalledTimes(1);
    expect(merge).toBeCalledTimes(1);
  });
});