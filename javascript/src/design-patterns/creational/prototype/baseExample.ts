import { BasePrototype } from "./prototype";

export class Person extends BasePrototype {
  constructor(public name: string, public age: number) {
    super();
  }
}

export class Employee extends Person {
  constructor(name: string, age: number, public department: string) {
    super(name, age);
  }
}