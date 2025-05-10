import { ExplitPrototype } from "./prototype";

export class EmailAddress implements ExplitPrototype<EmailAddress> {
  private email: string;
  private name: {
    first: string;
    last: string;
  };

  constructor(from: EmailAddress);
  constructor(email: string);
  constructor(arg: EmailAddress | string) {
    if (arg instanceof EmailAddress) {
      this.email = arg.email;
      this.name = { ...arg.name };
    } else {
      this.email = arg;
      this.name = {
        first: "",
        last: "",
      }
    }
  }

  setEmail(email: string) {
    this.email = email;
  }

  setName(firstName?: string, lastName?: string) {
    firstName && (this.name.first = firstName);
    lastName && (this.name.last = lastName);
  }

  clone(): EmailAddress {
    return new EmailAddress(this);
  }

  toString() {
    const parts = [this.email];
    if (this.name.first || this.name.last) {
      parts.push(`<${[this.name.first, this.name.last].filter(s => s).join(" ")}>`);
    }
    return parts.join(" ");
  }
}