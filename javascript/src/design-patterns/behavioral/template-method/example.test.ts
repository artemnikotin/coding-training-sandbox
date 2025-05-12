import { describe, expect, test } from "vitest";
import { Cocktail, Coffee, Tea } from "./example";

describe("DesignPattern/TemplateMethod", () => {
  test("Prepare beverage", () => {
    const tea = new Tea();
    tea.prepareBeverage();
    expect(tea.getTaste()).toBe("tea");
    expect(tea.getTemperature()).toBe(100);

    const coffee = new Coffee();
    coffee.prepareBeverage();
    expect(coffee.getTaste()).toBe("coffee");
    expect(coffee.getTemperature()).toBe(80);

    const cocktail = new Cocktail();
    cocktail.prepareBeverage();
    expect(cocktail.getTaste()).toBe("sweet");
    expect(cocktail.getTemperature()).toBe(20);
  })
})