import { describe, expect, test } from "vitest";
import { Light, LightOffCommand, LightOnCommand, RemoteControl, SetThermostatCommand, Thermostat } from "./example";

describe("Pattern/Command", () => {
  test("Control devices via commands", () => {
    const livingRoomLight = new Light();
    const bedroomThermostat = new Thermostat();

    const lightOn = new LightOnCommand(livingRoomLight);
    const lightOff = new LightOffCommand(livingRoomLight);
    const setTempTo22 = new SetThermostatCommand(bedroomThermostat, 22);
    const setTempTo24 = new SetThermostatCommand(bedroomThermostat, 24);

    const remote = new RemoteControl();

    remote.pressButton(lightOn);
    expect(livingRoomLight.getStatus()).toBe(true);
    remote.pressButton(setTempTo22);
    expect(bedroomThermostat.getTemperature()).toBe(22);
    remote.pressButton(setTempTo24);
    expect(bedroomThermostat.getTemperature()).toBe(24);
    remote.undoLastCommand();
    expect(bedroomThermostat.getTemperature()).toBe(22);
    remote.pressButton(lightOff);
    expect(livingRoomLight.getStatus()).toBe(false);
    remote.undoLastCommand();
    expect(livingRoomLight.getStatus()).toBe(true);
    remote.undoLastCommand();
    expect(bedroomThermostat.getTemperature()).toBe(20);
    remote.undoLastCommand();
    expect(livingRoomLight.getStatus()).toBe(false);
    remote.undoLastCommand();
  });
});