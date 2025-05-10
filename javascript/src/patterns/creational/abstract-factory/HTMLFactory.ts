import { ButtonElement, ContainerElement, GUIElement, GUIFactory, TextElement } from "./GUIFactory";

interface HtmlGUIElement extends GUIElement{
}

class HtmlTextElement extends TextElement implements HtmlGUIElement {
  private str: string = "";

  setText(str: string): void {
    this.str = str;
  }

  render(): string {
    return `<span>${this.str}</span>`;
  }
}

class HtmlButtonElement extends ButtonElement implements HtmlGUIElement  {
  private txt?: TextElement;

  setText(txt: TextElement): void {
    this.txt = txt;
  }

  render(): string {
    return `<button>${this.txt?.render()}</button>`;
  }
}

class HtmlContainerElement extends ContainerElement implements HtmlGUIElement  {
  private children: HtmlGUIElement[] = [];

  addElement(el: HtmlGUIElement): void {
    this.children.push(el);
  }

  render(): string {
    return `<div>${this.children.map(el => el.render()).join('')}</div>`;
  }
}

export class HtmlFactory extends GUIFactory {
  createButton(): HtmlButtonElement {
    return new HtmlButtonElement();
  }

  createText(): TextElement {
    return new HtmlTextElement();
  }

  createContainer(): ContainerElement {
    return new HtmlContainerElement();
  }
}