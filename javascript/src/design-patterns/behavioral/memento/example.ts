import { Memento, Originator } from "./memento";

export class TextEditor implements Originator<string> {
  private text: string = '';

  save(): TextSnapshot {
    return new TextSnapshot(this.text);
  }

  restore(snapshot: TextSnapshot): void {
    this.text = snapshot.getState();
  }

  setText(text: string) {
    this.text = text;
  }

  getText() {
    return this.text;
  }
}

export class TextSnapshot implements Memento<string> {
  constructor(private text: string) {
  }

  getState() {
    return this.text;
  }
}

export class Caretaker {
  private history: TextSnapshot[] = [];

  constructor(private editor: TextEditor) {
  }

  setText(text: string) {
    this.history.push(this.editor.save());
    this.editor.setText(text);
  }

  undo() {
    const snapshot = this.history.pop();
    snapshot && this.editor.restore(snapshot);
  }
}