import { describe, expect, test } from "vitest";
import { Singleton } from "./Singleton";

describe("DesignPattern/Singleton", () => {
  test("Singleton via inheritance", () => {
    class Foo extends Singleton {
      public getFive() {
        return 5;
      }
    }

    const instance1 = Foo.getInstance();
    expect(instance1 instanceof Foo).toBe(true);
    expect(instance1.getFive()).toBe(5);

    const instance2 = Foo.getInstance();
    expect(instance1).toBe(instance2);
  });

  test("Parametrized singleton", () => {
    type SettingValue = string | number;
    type Settings = Record<string, SettingValue>;

    class Config {
      private static instance: Config;

      private readonly settings: Settings;

      private constructor(initialSettings: Settings) {
        this.settings = initialSettings;
      }

      public static initialize(settings: Settings): Config {
        if (!Config.instance) {
          Config.instance = new Config(settings);
        }
        return Config.instance;
      }

      public static getInstance(): Config {
        if (!Config.instance) {
          throw new Error("Config not initialized. Call initialize first.");
        }
        return Config.instance;
      }

      public getSetting(key: string): SettingValue {
        return this.settings[key];
      }
    }

    Config.initialize({ apiUrl: "https://api.example.com", timeout: 5000 });
    const config = Config.getInstance();

    expect(config.getSetting("apiUrl")).toBe("https://api.example.com");
  })
});