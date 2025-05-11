import { describe, expect, test, vi } from "vitest";
import { Button, TextInput, Checkbox, Label, LoginDialog } from "./example";

describe("DesignPattern/Mediator", () => {
  test("Login Dialog workflow", () => {
    const loginButton = new Button("Login");
    const registerButton = new Button("Register");
    const usernameInput = new TextInput("Enter username");
    const passwordInput = new TextInput("Enter password");
    const rememberCheckbox = new Checkbox("Remember me");
    const errorLabel = new Label("Please fill all fields");

    const dialog = new LoginDialog(
      loginButton,
      registerButton,
      usernameInput,
      passwordInput,
      rememberCheckbox,
      errorLabel
    );

    dialog.onlogin = vi.fn();
    dialog.onregister = vi.fn();
    expect(loginButton.isEnabled()).toBe(false);

    usernameInput.setValue("user");
    passwordInput.setValue("pass");
    rememberCheckbox.click();
    expect(loginButton.isEnabled()).toBe(true);

    loginButton.click();
    expect(dialog.onlogin).toBeCalledWith("user", "pass", true);

    usernameInput.setValue("");
    passwordInput.setValue("");
    loginButton.click();
    expect(dialog.onlogin).toBeCalledTimes(1);

    registerButton.click();
    expect(dialog.onregister).toBeCalledTimes(1);
  });
});