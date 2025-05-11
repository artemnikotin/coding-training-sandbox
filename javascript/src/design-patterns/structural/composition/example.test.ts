import { describe, expect, test } from "vitest";
import { Circle, Square, Container } from "./example";

describe("DesignPattern/Composition - Example", () => {
  test("Render graphic components tree", () => {
    const circle = new Circle(0, 0, 5);
    expect(circle.render()).toBe("circle(0,0,5)");

    const square = new Square(5, 5, 5);
    expect(square.render()).toBe("square(5,5,5)");

    const group = new Container();
    group.add(circle);
    group.add(square);
    expect(group.render()).toBe("[circle(0,0,5),square(5,5,5)]");

    group.move(5, 5);
    expect(group.render()).toBe("[circle(5,5,5),square(10,10,5)]");
  });
});