import { ButtonElement, ContainerElement, GUIElement, GUIFactory, TextElement } from "./GUIFactory";

interface SvgGUIElement extends GUIElement {
}

class SvgTextElement extends TextElement implements SvgGUIElement {
  private str: string = "";

  setText(str: string): void {
    this.str = str;
  }

  render(): string {
    return `<text>${this.str}</text>`;
  }
}

class SvgButtonElement extends ButtonElement implements SvgGUIElement {
  private txt?: TextElement;

  setText(txt: TextElement): void {
    this.txt = txt;
  }

  render(): string {
    return `<rect>${this.txt?.render()}</rect>`;
  }
}

class SvgContainerElement extends ContainerElement implements SvgGUIElement {
  private children: SvgGUIElement[] = [];

  addElement(el: SvgGUIElement): void {
    this.children.push(el);
  }

  render(): string {
    return `<g>${this.children.map(el => el.render()).join('')}</g>`;
  }
}

export class SvgFactory extends GUIFactory {
  createButton(): SvgButtonElement {
    return new SvgButtonElement();
  }

  createText(): TextElement {
    return new SvgTextElement();
  }

  createContainer(): ContainerElement {
    return new SvgContainerElement();
  }
}