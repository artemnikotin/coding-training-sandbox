// Makes complex stuff simple
class Facade {
    protected system1: Subsystem1;
    protected system2: Subsystem2;

    // Takes existing systems or creates new ones
    constructor(system1?: Subsystem1, system2?: Subsystem2) {
        this.system1 = system1 || new Subsystem1();
        this.system2 = system2 || new Subsystem2();
    }

    // One simple method hides complex operations
    public doEverything(): string {
        let result = 'Starting systems:\n';
        result += this.system1.start();
        result += this.system2.prepare();
        result += 'Running actions:\n';
        result += this.system1.execute();
        result += this.system2.run();

        return result;
    }
}

// Complex system parts
class Subsystem1 {
    public start(): string {
        return 'System1: Ready!\n';
    }

    public execute(): string {
        return 'System1: Running!\n';
    }
}

class Subsystem2 {
    public prepare(): string {
        return 'System2: Prepared!\n';
    }

    public run(): string {
        return 'System2: Done!';
    }
}

// Client just uses the simple facade
function runApp(facade: Facade) {
    return facade.doEverything();
}

// Usage
const system1 = new Subsystem1();
const system2 = new Subsystem2();
const simpleInterface = new Facade(system1, system2);
runApp(simpleInterface);