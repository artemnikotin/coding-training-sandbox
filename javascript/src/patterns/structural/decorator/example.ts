export interface Coffee {
    cost(): number;
    description(): string;
}

export class BlackCoffee implements Coffee {
    cost(): number {
        return 5;
    }

    description(): string {
        return "Black coffee";
    }
}

abstract class CoffeeDecorator implements Coffee {
    constructor(protected coffee: Coffee) {}

    abstract cost(): number;
    abstract description(): string;
}

// Concrete decorators - add-ons
export class MilkDecorator extends CoffeeDecorator {
    cost(): number {
        return this.coffee.cost() + 2;
    }

    description(): string {
        return `${this.coffee.description()}, with milk`;
    }
}

export class SugarDecorator extends CoffeeDecorator {
    cost(): number {
        return this.coffee.cost() + 1;
    }

    description(): string {
        return `${this.coffee.description()}, with sugar`;
    }
}

export class WhippedCreamDecorator extends CoffeeDecorator {
    cost(): number {
        return this.coffee.cost() + 3;
    }

    description(): string {
        return `${this.coffee.description()}, with whipped cream`;
    }
}