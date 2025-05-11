import { Mediator } from "./mediator";

type DialogMediator = Mediator<Component, string>;

abstract class Component {
  protected mediator?: DialogMediator;

  setMediator(mediator: DialogMediator): void {
    this.mediator = mediator;
  }

  abstract click(): void;
  abstract change(): void;
  abstract keypress(): void;
}

export class LoginDialog implements DialogMediator {
  public onlogin?: (user: string, pass: string, rememberMe: boolean) => void;
  public onregister?: () => void;

  constructor(
    private loginButton: Button,
    private registerButton: Button,
    private usernameInput: TextInput,
    private passwordInput: TextInput,
    private rememberCheckbox: Checkbox,
    private errorLabel: Label
  ) {
    loginButton.setMediator(this);
    registerButton.setMediator(this);
    usernameInput.setMediator(this);
    passwordInput.setMediator(this);
    rememberCheckbox.setMediator(this);

    this.validateForm();
  }

  notify(sender: Component, event: string): void {
    if (sender === this.loginButton && event === 'click') {
      this.handleLogin();
    }
    else if (sender === this.registerButton && event === 'click') {
      this.handleRegister();
    }
    else if ((sender === this.usernameInput || sender === this.passwordInput) && event === 'change') {
      this.validateForm();
    }
    else if (sender === this.rememberCheckbox && event === 'change') {
      this.toggleRememberMe();
    }
  }

  private handleLogin(): void {
    if (this.validateForm()) {
      this.onlogin?.(this.usernameInput.getValue(), this.passwordInput.getValue(), this.rememberCheckbox.isChecked());
    }
  }

  private handleRegister(): void {
    this.onregister?.();
  }

  private validateForm(): boolean {
    const isValid = this.usernameInput.getValue().length > 0
      && this.passwordInput.getValue().length > 0;

    this.loginButton.setEnabled(isValid);
    this.errorLabel.setVisible(!isValid);

    return isValid;
  }

  private toggleRememberMe(): void {
  }
}

export class Button extends Component {
  private enabled = true;

  constructor(private label: string) {
    super();
  }

  click(): void {
    if (this.enabled) {
      this.mediator?.notify(this, 'click');
    }
  }

  change(): void { }
  keypress(): void { }

  setEnabled(enabled: boolean): void {
    this.enabled = enabled;
  }

  isEnabled() {
    return this.enabled;
  }
}

export class TextInput extends Component {
  private value = '';

  constructor(private _: string) {
    super();
  }

  click(): void { }

  change(): void {
    this.mediator?.notify(this, 'change');
  }

  keypress(): void {
    this.mediator?.notify(this, 'keypress');
  }

  getValue(): string {
    return this.value;
  }

  setValue(value: string): void {
    this.value = value;
    this.change();
  }
}

export class Checkbox extends Component {
  private checked = false;

  constructor(private _: string) {
    super();
  }

  click(): void {
    this.toggle();
  }

  change(): void {
    this.mediator?.notify(this, 'change');
  }

  keypress(): void { }

  toggle(): void {
    this.checked = !this.checked;
    this.change();
  }

  isChecked(): boolean {
    return this.checked;
  }
}

export class Label extends Component {
  private visible = false;

  constructor(private _: string) {
    super();
  }

  click(): void { }
  change(): void { }
  keypress(): void { }

  setVisible(visible: boolean): void {
    this.visible = visible;
  }

  isVisible() {
    return this.visible;
  }
}