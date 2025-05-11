import { describe, expect, test } from "vitest";
import { Caretaker, TextEditor } from "./example";

describe("DesignPattern/Memento", () => {
  test("Restore text editor state", () => {
    const editor = new TextEditor();
    const caretaker = new Caretaker(editor);

    caretaker.setText("Hello");
    caretaker.setText("Word");

    expect(editor.getText()).toBe("Word");
    caretaker.undo();
    expect(editor.getText()).toBe("Hello");
    caretaker.undo();
    expect(editor.getText()).toBe("");
  });
});