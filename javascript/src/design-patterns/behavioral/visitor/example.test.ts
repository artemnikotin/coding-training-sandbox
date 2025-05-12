import { describe, expect, test } from "vitest";
import { ShoppingItem, Book, Electronics, Fruit, TaxVisitor, DiscountVisitor } from "./example";

describe("DesignPattern/Visitor", () => {
  test("Calculate shopping prices", () => {
    const items: ShoppingItem[] = [
      new Book(20, "123-456"),
      new Electronics(1000, "TV"),
      new Fruit(5, 3, "Apples")
    ];

    // Calculate total with tax
    const taxVisitor = new TaxVisitor();
    const totalWithTax = items.reduce((sum, item) => sum + item.accept(taxVisitor), 0);
    expect(totalWithTax.toFixed(2)).toBe("1236.00");

    // Calculate total with discounts
    const discountVisitor = new DiscountVisitor();
    const totalWithDiscount = items.reduce((sum, item) => sum + item.accept(discountVisitor), 0);
    expect(totalWithDiscount.toFixed(2)).toBe("980.75");
  });
});