export interface GUIElement {
  render(): string;
}

export abstract class ButtonElement implements GUIElement {
  abstract setText(txt: TextElement): void;
  abstract render(): string;
}

export abstract class TextElement implements GUIElement {
  abstract setText(str: string): void;
  abstract render(): string;
}

export abstract class ContainerElement implements GUIElement {
  abstract render(): string;
  abstract addElement(el: GUIElement): void;
}

export abstract class GUIFactory {
  abstract createButton(): ButtonElement;
  abstract createText(): TextElement;
  abstract createContainer(): ContainerElement;
}