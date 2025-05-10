import { describe, expect, test } from "vitest";
import { HtmlFactory } from "./HTMLFactory";
import { GUIFactory } from "./GUIFactory";
import { SvgFactory } from "./SVGFactory";

function makeApp(factory: GUIFactory) {
  const button = factory.createButton();
  const buttonText = factory.createText();
  const header = factory.createText();

  buttonText.setText("Click");
  button.setText(buttonText);
  header.setText("Hello");

  const ui = factory.createContainer();
  ui.addElement(header);
  ui.addElement(button);

  return ui;
}

describe("Pattern/AbstractFactory", () => {
  test("HTML GUI app", () => {
    const factory = new HtmlFactory(); 
    const ui = makeApp(factory);
    
    expect(ui.render()).toBe("<div><span>Hello</span><button><span>Click</span></button></div>")
  });

  test("SVG GUI app", () => {
    const factory = new SvgFactory(); 
    const ui = makeApp(factory);
    
    expect(ui.render()).toBe("<g><text>Hello</text><rect><text>Click</text></rect></g>")
  });
});