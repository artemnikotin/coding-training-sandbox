// The high-level control interface (e.g., remote control)
// It works with implementations through the bridge
class Abstraction {
  protected implementation: Implementation;

  constructor(implementation: Implementation) {
    this.implementation = implementation;
  }

  public operation(): string {
    const result = this.implementation.operationImplementation();
    return `Abstraction result: ${result}`;
  }
}

// You can add new features without touching implementations
class ExtendedAbstraction extends Abstraction {
  public operation(): string {
    const result = this.implementation.operationImplementation();
    return `Extended result: ${result}`;
  }
}

// The basic operations interface (e.g., device functions)
// Separate from the high-level control
interface Implementation {
  operationImplementation(): string;
}

// Platform-specific implementations
class ConcreteImplementationA implements Implementation {
  public operationImplementation(): string {
    return 'Platform A result';
  }
}

class ConcreteImplementationB implements Implementation {
  public operationImplementation(): string {
    return 'Platform B result';
  }
}

// Client code works with abstractions only
function clientCode(abstraction: Abstraction) {
  return abstraction.operation();
}

// Usage examples
let implementation = new ConcreteImplementationA();
let abstraction = new Abstraction(implementation);
clientCode(abstraction);

implementation = new ConcreteImplementationB();
abstraction = new ExtendedAbstraction(implementation);
clientCode(abstraction);