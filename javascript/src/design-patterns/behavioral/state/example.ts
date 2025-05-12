interface VendingState {
  insertMoney(amount: number): void;
  selectItem(item: string): void;
  dispenseItem(): void;
  returnMoney(): void;
}

export class VendingMachine {
  private state: VendingState;
  private currentAmount: number = 0;
  private selectedItem: string | null = null;
  private items: { [key: string]: number } = {
    "Soda": 150,
    "Chips": 100,
    "Candy": 75
  };

  private dispensedItem: string | null = null;
  private message: string = "";

  constructor() {
    this.state = new IdleState(this);
  }

  setState(state: VendingState): void {
    this.state = state;
  }

  getCurrentAmount(): number {
    return this.currentAmount;
  }

  addMoney(amount: number): void {
    this.currentAmount += amount;
  }

  resetAmount(): void {
    this.currentAmount = 0;
  }

  setSelectedItem(item: string | null): void {
    this.selectedItem = item;
  }

  getSelectedItem(): string | null {
    return this.selectedItem;
  }

  getItemPrice(item: string): number {
    return this.items[item] || 0;
  }

  setMessage(msg: string) {
    this.message = msg;
  }

  setDispensedItem(item: string) {
    this.dispensedItem = item;
  }

  // #region Client methods
  getMessage() {
    return this.message;
  }

  takeItem() {
    const item = this.dispensedItem;
    this.dispensedItem = null;
    return item;
  }
  // #endregion

  // #region Delegate to current state
  insertMoney(amount: number): void {
    this.state.insertMoney(amount);
  }

  selectItem(item: string): void {
    this.state.selectItem(item);
  }

  dispenseItem(): void {
    this.state.dispenseItem();
  }

  returnMoney(): void {
    this.state.returnMoney();
  }
  // #endregion
}

class IdleState implements VendingState {
  constructor(private machine: VendingMachine) { }

  insertMoney(amount: number): void {
    this.machine.addMoney(amount);
    this.machine.setMessage(`Inserted ${amount} cents`);
    this.machine.setState(new HasMoneyState(this.machine));
  }

  selectItem(_: string): void {
    this.machine.setMessage("Please insert money");
  }

  dispenseItem(): void {
    this.machine.setMessage("Please insert money");
  }

  returnMoney(): void {
    this.machine.setMessage("No money to return");
  }
}

class HasMoneyState implements VendingState {
  constructor(private machine: VendingMachine) { }

  insertMoney(amount: number): void {
    this.machine.setMessage(`Inserted ${amount} cents`);
    this.machine.addMoney(amount);
  }

  selectItem(item: string): void {
    const price = this.machine.getItemPrice(item);
    if (price === 0) {
      this.machine.setMessage("Invalid item selection");
      return;
    }

    if (this.machine.getCurrentAmount() >= price) {
      this.machine.setMessage(`Selected ${item} (${price} cents)`);
      this.machine.setSelectedItem(item);
      this.machine.setState(new ItemSelectedState(this.machine));
    } else {
      this.machine.setMessage(`Not enough money for ${item}. Need ${price - this.machine.getCurrentAmount()} more cents`);
    }
  }

  dispenseItem(): void {
    this.machine.setMessage("Please select an item first");
  }

  returnMoney(): void {
    this.machine.setMessage(`Returning ${this.machine.getCurrentAmount()} cents`);
    this.machine.resetAmount();
    this.machine.setState(new IdleState(this.machine));
  }
}

class ItemSelectedState implements VendingState {
  constructor(private machine: VendingMachine) { }

  insertMoney(_: number): void {
    this.machine.setMessage("Cannot insert money after selection");
  }

  selectItem(_: string): void {
    this.machine.setMessage("Already selected an item");
  }

  dispenseItem(): void {
    const item = this.machine.getSelectedItem()!;
    const price = this.machine.getItemPrice(item);
    const change = this.machine.getCurrentAmount() - price;

    this.machine.setMessage(`Dispensing ${item}`);
    this.machine.setDispensedItem(item);
    this.machine.setSelectedItem(null);
    if (change > 0) {
      this.machine.setState(new HasMoneyState(this.machine));
    } else {
      this.machine.resetAmount();
      this.machine.setState(new IdleState(this.machine));
    }
  }

  returnMoney(): void {
    this.machine.setMessage(`Returning ${this.machine.getCurrentAmount()} cents`);
    this.machine.resetAmount();
    this.machine.setSelectedItem(null);
    this.machine.setState(new IdleState(this.machine));
  }
}