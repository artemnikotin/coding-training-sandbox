// Visitor Interface
interface ShoppingCartVisitor {
    visitBook(book: Book): number;
    visitElectronics(electronics: Electronics): number;
    visitFruit(fruit: Fruit): number;
}

// Element Interface
export interface ShoppingItem {
    accept(visitor: ShoppingCartVisitor): number;
}

// Concrete Elements
export class Book implements ShoppingItem {
    constructor(public price: number, public isbn: string) {}

    accept(visitor: ShoppingCartVisitor): number {
        return visitor.visitBook(this);
    }
}

export class Electronics implements ShoppingItem {
    constructor(public price: number, public model: string) {}

    accept(visitor: ShoppingCartVisitor): number {
        return visitor.visitElectronics(this);
    }
}

export class Fruit implements ShoppingItem {
    constructor(public pricePerKg: number, public weight: number, public name: string) {}

    accept(visitor: ShoppingCartVisitor): number {
        return visitor.visitFruit(this);
    }
}

// Concrete Visitors
export class TaxVisitor implements ShoppingCartVisitor {
    visitBook(book: Book): number {
        return book.price * 1.05; // 5% tax
    }

    visitElectronics(electronics: Electronics): number {
        return electronics.price * 1.20; // 20% tax
    }

    visitFruit(fruit: Fruit): number {
        return (fruit.pricePerKg * fruit.weight) * 1.00; // 0% tax
    }
}

export class DiscountVisitor implements ShoppingCartVisitor {
    visitBook(book: Book): number {
        return book.price * 0.90; // 10% discount
    }

    visitElectronics(electronics: Electronics): number {
        return electronics.price * 0.95; // 5% discount
    }

    visitFruit(fruit: Fruit): number {
        return (fruit.pricePerKg * fruit.weight) * 0.85; // 15% discount
    }
}