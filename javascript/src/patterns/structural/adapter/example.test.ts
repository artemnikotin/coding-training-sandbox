import { describe, expect, test } from "vitest";
import { WeatherService, WeatherServiceAdapter, Client } from "./example";

describe("Pattern/Adapter - Example", () => {
  test("Adapt third-party lib service", () => {
    const weatherService = new WeatherService();
    const weatherServiceAdapter = new WeatherServiceAdapter(weatherService);
    const client = new Client(weatherServiceAdapter);

    expect(client.getResult()).toBe("It's 0 degrees in London");
  });
});