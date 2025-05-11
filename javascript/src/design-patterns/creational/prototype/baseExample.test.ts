import { describe, expect, test } from "vitest";
import { Person, Employee } from "./baseExample";

describe("DesignPattern/Prototype - Example", () => {
  test("Base prototype cloning", () => {
    const person = new Person("Alice", 30);
    const personClone = person.clone();
    expect(personClone instanceof Person).toBe(true);
    expect(personClone.name).toBe("Alice");

    const employee = new Employee("Bob", 40, "Engineering");
    const employeeClone = employee.clone();
    expect(employeeClone instanceof Employee).toBe(true);
    expect(employeeClone.department).toBe("Engineering");
  });
});