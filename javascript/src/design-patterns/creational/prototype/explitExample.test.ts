import { describe, expect, test } from "vitest";
import { EmailAddress } from "./explitExample";

describe("DesignPattern/Prototype - Example", () => {
  test("Explit prototype cloning", () => {
    const original = new EmailAddress("johndoe@example.com");
    original.setName("John");
    expect(`${original}`).toBe("johndoe@example.com <John>");

    const copy = original.clone();
    expect(`${copy}`).toBe("johndoe@example.com <John>");

    original.setName(undefined, "Doe");
    expect(`${original}`).toBe("johndoe@example.com <John Doe>");
    expect(`${copy}`).toBe("johndoe@example.com <John>");

    copy.setEmail("cena@example.com");
    copy.setName(undefined, "CENA");
    expect(`${original}`).toBe("johndoe@example.com <John Doe>");
    expect(`${copy}`).toBe("cena@example.com <John CENA>");
  });
});