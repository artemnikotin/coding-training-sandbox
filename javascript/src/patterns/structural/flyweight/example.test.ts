import { describe, expect, test } from "vitest";
import { CharacterFactory, TextCharacter } from "./example";

describe("Pattern/Flyweight", () => {
  test("Shares common data between objects", () => {
    const factory = new CharacterFactory();

    const text = "Hello, World!";
    const fontSize = 12;

    const characters: TextCharacter[] = [];

    // Create text characters (reusing flyweights)
    for (let i = 0; i < text.length; i++) {
      const char = text[i];
      const flyweight = factory.getCharacter(char);
      characters.push(new TextCharacter(flyweight, fontSize));
    }

    expect(factory.countCharacters(), "Total unique characters created").toBe(10);
    expect(characters.length, "Total characters rendered").toBe(13);
  });
});