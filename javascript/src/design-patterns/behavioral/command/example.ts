import { ReversibleCommand } from "./command";

export class Light {
  private isOn: boolean = false;

  turnOn(): void {
    this.isOn = true;
  }

  turnOff(): void {
    this.isOn = false;
  }

  getStatus(): boolean {
    return this.isOn;
  }
}

export class Thermostat {
  constructor(private temperature: number = 20) {
  }

  setTemperature(temp: number): void {
    this.temperature = temp;
  }

  getTemperature(): number {
    return this.temperature;
  }
}

export class LightOnCommand implements ReversibleCommand {
  constructor(private light: Light) { }

  execute(): void {
    this.light.turnOn();
  }

  undo(): void {
    this.light.turnOff();
  }
}

export class LightOffCommand implements ReversibleCommand {
  constructor(private light: Light) { }

  execute(): void {
    this.light.turnOff();
  }

  undo(): void {
    this.light.turnOn();
  }
}

export class SetThermostatCommand implements ReversibleCommand {
  private previousTemp?: number = undefined;

  constructor(private thermostat: Thermostat, private newTemp: number) {
  }

  execute(): void {
    this.previousTemp = this.thermostat.getTemperature();
    this.thermostat.setTemperature(this.newTemp);
  }

  undo(): void {
    this.previousTemp !== null && this.thermostat.setTemperature(this.previousTemp!);
  }
}

export class RemoteControl {
  private commandHistory: ReversibleCommand[] = [];

  pressButton(command: ReversibleCommand): void {
    command.execute();
    this.commandHistory.push(command);
  }

  undoLastCommand(): void {
    const lastCommand = this.commandHistory.pop();
    lastCommand && lastCommand.undo();
  }
}