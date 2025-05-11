import { describe, expect, test } from "vitest";
import { Collection } from "./example";

describe("Pattern/Iterator", () => {
  test("Iterate over collection", () => {
    const collection = new Collection<number>();
    collection.add(1);
    collection.add(2);
    collection.add(3);

    const iterator = collection.iterator();

    expect(iterator.hasNext()).toBe(true);
    expect(iterator.next()).toBe(1);
    expect(iterator.next()).toBe(2);
    expect(iterator.next()).toBe(3);
    expect(iterator.hasNext()).toBe(false);
  })
});