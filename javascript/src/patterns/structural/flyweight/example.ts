// Flyweight interface
interface CharacterFlyweight {
  display(fontSize: number): string;
}

// Concrete Flyweight - stores intrinsic (shared) state
class Character implements CharacterFlyweight {
  constructor(private readonly char: string) { }

  display(fontSize: number): string {
    return `Character '${this.char}' with font size ${fontSize}`;
  }
}

// Flyweight Factory - creates and manages flyweights
export class CharacterFactory {
  private characters: Map<string, Character> = new Map;

  getCharacter(char: string): CharacterFlyweight {
    if (!this.characters.has(char)) {
      this.characters.set(char, new Character(char));
    }
    return this.characters.get(char)!;
  }

  countCharacters(): number {
    return this.characters.size;
  }
}

// Client class - contains extrinsic (unique) state
export class TextCharacter {
  constructor(
    private readonly flyweight: CharacterFlyweight,
    private readonly fontSize: number
  ) { }

  display(): string {
    return this.flyweight.display(this.fontSize);
  }
}