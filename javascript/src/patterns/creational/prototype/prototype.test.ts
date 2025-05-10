import { describe, expect, test } from "vitest";
import { ProtoMail } from "./prototype";

describe("Patterns/Prototype", () => {
  test("Create clone using Prototype pattern", () => {
    const original = new ProtoMail("johndoe@example.com");
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