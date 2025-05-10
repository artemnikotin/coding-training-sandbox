interface Prototype<T> {
  clone(): T;
}

export class ProtoMail implements Prototype<ProtoMail> {
  private email: string;
  private name: {
    first: string;
    last: string;
  };

  constructor(from: ProtoMail);
  constructor(email: string);
  constructor(arg: ProtoMail | string) {
    if (arg instanceof ProtoMail) {
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

  clone(): ProtoMail {
    return new ProtoMail(this);
  }

  toString() {
    const parts = [this.email];
    if (this.name.first || this.name.last) {
      parts.push(`<${[this.name.first, this.name.last].filter(s => s).join(" ")}>`);
    }
    return parts.join(" ");
  }
}