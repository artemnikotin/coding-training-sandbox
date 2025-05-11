import { describe, expect, test } from "vitest";
import { AdvancedRemote, BasicRemote, Radio, TV } from "./example";

describe("Pattern/Bridge - Example", () => {
  test("Separates abstraction (RemoteControl) from implementation (Device)", () => {
    const tv = new TV();
    const radio = new Radio();

    const basicRemoteForTV = new BasicRemote(tv);
    const advancedRemoteForRadio = new AdvancedRemote(radio);

    basicRemoteForTV.togglePower();
    basicRemoteForTV.volumeUp();
    basicRemoteForTV.channelUp();
    expect(tv.getVolume()).toBe(60);

    advancedRemoteForRadio.togglePower();
    advancedRemoteForRadio.mute();
    advancedRemoteForRadio.presetChannel(102.3);
    expect(radio.getVolume()).toBe(0);
    expect(radio.getChannel()).toBe(102.3);
  });
});