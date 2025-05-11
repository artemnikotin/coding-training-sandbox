import { describe, expect, test } from "vitest";
import { ManagerHandler, DirectorHandler, CEOHandler, approve } from "./example";

describe("Pattern/ChainOfResponsibility", () => {
  test("Approval process", () => {
    const manager = new ManagerHandler();
    const director = new DirectorHandler();
    const ceo = new CEOHandler();

    manager.setNext(director).setNext(ceo);

    expect(approve(manager, "vacation")).toBe("Approved by Manager");
    expect(approve(manager, "raise")).toBe("Approved by Director");
    expect(approve(manager, "new position")).toBe("Approved by CEO");
    expect(approve(manager, "bonus")).toBe("Not approved");

    expect(approve(director, "vacation")).toBe("Not approved");
  });
});