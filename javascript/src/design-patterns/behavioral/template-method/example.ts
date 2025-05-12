interface BeverageState {
  temperature: number;
  ingredients: string[];
  taste: string;
}

abstract class Beverage {
  protected state: BeverageState = {
    temperature: 23,
    ingredients: [],
    taste: "",
  };

  public getTaste() {
    return this.state.taste;
  }

  public getTemperature() {
    return this.state.temperature;
  }

  // Template method
  public prepareBeverage(): void {
    this.addWater();
    this.prepareWater();
    this.addIngredients();
    this.optionalHook();
    this.state.taste = this.defineTaste();
  }

  // Concrete methods
  private addWater() {
    this.state.ingredients.push("water");
  }

  private defineTaste() {
    if (this.state.ingredients.includes("tea")) {
      return "tea";
    }
    if (this.state.ingredients.includes("coffee")) {
      return "coffee";
    }
    return "sweet";
  }

  // Abstract methods
  protected abstract prepareWater(): void;
  protected abstract addIngredients(): void;

  // Hook method (optional override)
  protected optionalHook(): void { }
}

export class Tea extends Beverage {
  protected prepareWater(): void {
    this.state.temperature = 100;
  }

  protected addIngredients(): void {
    this.state.ingredients.push("tea");
    this.state.ingredients.push("lemon");
  }
}

export class Coffee extends Beverage {
  protected prepareWater(): void {
    this.state.temperature = 100;
  }

  protected addIngredients(): void {
    this.state.ingredients.push("coffee");
    this.state.ingredients.push("milk");
    this.state.temperature = 80;
  }

  protected optionalHook(): void {
    this.state.ingredients.push("chocolate topping");
  }
}

export class Cocktail extends Beverage {
  protected prepareWater(): void {
    this.state.temperature = 20;
  }

  protected addIngredients(): void {
    this.state.ingredients.push("juice");
    this.state.ingredients.push("vodka");
    this.state.ingredients.push("ice");
  }
}