import { describe, expect, test } from "vitest";
import { BlackCoffee, Coffee, MilkDecorator, SugarDecorator, WhippedCreamDecorator } from "./example";

describe("DesignPattern/Decorator - Example", () => {
	test("Coffee menu", () => {
		let coffee: Coffee = new BlackCoffee();
		expect(coffee.description()).toBe("Black coffee");
		expect(coffee.cost()).toBe(5);

		coffee = new MilkDecorator(coffee);
		expect(coffee.description()).toBe("Black coffee, with milk");
		expect(coffee.cost()).toBe(7);

		coffee = new SugarDecorator(coffee);
		expect(coffee.description()).toBe("Black coffee, with milk, with sugar");
		expect(coffee.cost()).toBe(8);

		coffee = new WhippedCreamDecorator(coffee);
		expect(coffee.description()).toBe("Black coffee, with milk, with sugar, with whipped cream");
		expect(coffee.cost()).toBe(11);
	})
});