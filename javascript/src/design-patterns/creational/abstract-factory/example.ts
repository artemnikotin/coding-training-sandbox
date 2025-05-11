// #region Abstract
interface Element {
  render(): string;
}

abstract class Button implements Element {
  abstract setText(txt: Text): void;
  abstract render(): string;
}

abstract class Text implements Element {
  abstract setText(str: string): void;
  abstract render(): string;
}

abstract class Container implements Element {
  abstract render(): string;
  abstract addElement(el: Element): void;
}

export abstract class Factory {
  abstract createButton(): Button;
  abstract createText(): Text;
  abstract createContainer(): Container;
}
// #endregion

// #region HTML
interface HtmlElement extends Element {
}

class HtmlText extends Text implements HtmlElement {
  private str: string = "";

  setText(str: string): void {
    this.str = str;
  }

  render(): string {
    return `<span>${this.str}</span>`;
  }
}

class HtmlButton extends Button implements HtmlElement {
  private txt?: Text;

  setText(txt: Text): void {
    this.txt = txt;
  }

  render(): string {
    return `<button>${this.txt?.render()}</button>`;
  }
}

class HtmlContainer extends Container implements HtmlElement {
  private children: HtmlElement[] = [];

  addElement(el: HtmlElement): void {
    this.children.push(el);
  }

  render(): string {
    return `<div>${this.children.map(el => el.render()).join('')}</div>`;
  }
}

export class HtmlFactory extends Factory {
  createButton(): HtmlButton {
    return new HtmlButton();
  }

  createText(): Text {
    return new HtmlText();
  }

  createContainer(): Container {
    return new HtmlContainer();
  }
}
// #endregion

// #region SVG
interface SvgElement extends Element {
}

class SvgText extends Text implements SvgElement {
  private str: string = "";

  setText(str: string): void {
    this.str = str;
  }

  render(): string {
    return `<text>${this.str}</text>`;
  }
}

class SvgButton extends Button implements SvgElement {
  private txt?: Text;

  setText(txt: Text): void {
    this.txt = txt;
  }

  render(): string {
    return `<rect>${this.txt?.render()}</rect>`;
  }
}

class SvgContainer extends Container implements SvgElement {
  private children: SvgElement[] = [];

  addElement(el: SvgElement): void {
    this.children.push(el);
  }

  render(): string {
    return `<g>${this.children.map(el => el.render()).join('')}</g>`;
  }
}

export class SvgFactory extends Factory {
  createButton(): SvgButton {
    return new SvgButton();
  }

  createText(): Text {
    return new SvgText();
  }

  createContainer(): Container {
    return new SvgContainer();
  }
}
// #endregion