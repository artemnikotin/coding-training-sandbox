import { describe, expect, test } from "vitest";
import { VendingMachine } from "./example";

describe("DesignPattern/State", () => {
  test("Vending mashine workflow", () => {
    const vendingMachine = new VendingMachine();
    vendingMachine.selectItem("Soda");
    expect(vendingMachine.getMessage()).toBe("Please insert money");
    vendingMachine.insertMoney(100);
    expect(vendingMachine.getMessage()).toBe("Inserted 100 cents");
    vendingMachine.selectItem("Soda");
    expect(vendingMachine.getMessage()).toBe("Not enough money for Soda. Need 50 more cents");
    vendingMachine.insertMoney(50);
    vendingMachine.selectItem("Soda");
    expect(vendingMachine.getMessage()).toBe("Selected Soda (150 cents)");
    vendingMachine.dispenseItem();
    expect(vendingMachine.takeItem()).toBe("Soda");
  });
});