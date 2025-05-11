import { Command } from "./command";

// Simple command - handles its own logic
class SimpleCommand implements Command {
    constructor(private payload: string) {}

    public execute(): void {
        // Do something simple with the payload
    }
}

// Complex command - delegates work to a receiver
class ComplexCommand implements Command {
    constructor(
        private receiver: Receiver,
        private a: string,
        private b: string
    ) {}

    public execute(): void {
        this.receiver.doSomething(this.a);
        this.receiver.doSomethingElse(this.b);
    }
}

// Receiver - knows how to perform actual operations
class Receiver {
    public doSomething(a: string): void {
        // Perform some action
    }

    public doSomethingElse(b: string): void {
        // Perform another action
    }
}

// Invoker - asks commands to carry out requests
class Invoker {
    private onStart?: Command;
    private onFinish?: Command;

    public setOnStart(command: Command): void {
        this.onStart = command;
    }

    public setOnFinish(command: Command): void {
        this.onFinish = command;
    }

    public doSomethingImportant(): void {
        this.onStart?.execute();
        // Do important work
        this.onFinish?.execute();
    }
}

// Usage
const invoker = new Invoker();
invoker.setOnStart(new SimpleCommand('Start task'));
const receiver = new Receiver();
invoker.setOnFinish(new ComplexCommand(receiver, 'Data', 'Backup'));
invoker.doSomethingImportant();