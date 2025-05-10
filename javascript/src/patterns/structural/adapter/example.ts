export interface TemparatureService {
  getTemperature(): number;
}

export class WeatherService {
  getTemperatureInFahrenheit(city: string): number {
    if (city === "Moscow") return 28;
    if (city === "London") return 32;
    return 32;
  }
}

export class WeatherServiceAdapter implements TemparatureService {
  constructor(private adaptee: WeatherService) {
  }

  getTemperature(): number {
    const t = this.adaptee.getTemperatureInFahrenheit("London");
    return (t - 32) * 5 / 9;
  }
}

export class Client {
  constructor(private service: TemparatureService) {
  }

  getResult() {
    return `It's ${this.service.getTemperature()} degrees in London`;
  }
}