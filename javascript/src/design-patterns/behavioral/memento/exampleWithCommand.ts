import { ReversibleCommand } from "../command/command";

export class TextEditor {
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
}

export class TextSnapshot {
  constructor(private text: string) {
  }

  getState() {
    return this.text;
  }
}

export class Command implements ReversibleCommand {
  private backup?: TextSnapshot;

  constructor(private editor: TextEditor) {
  }

  execute() {
    this.backup = this.editor.save();
  }

  undo() {
    this.backup && this.editor.restore(this.backup);
  }
}