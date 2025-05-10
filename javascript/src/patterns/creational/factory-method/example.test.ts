import { describe, expect, test } from "vitest";
import { PaymentMethodFactory } from "./example";

describe("Patterns/FactoryMethod - Example", () => {
  test("Create different implementations", () => {
    const payment1 = PaymentMethodFactory.createPaymentMethod('credit-card');
    expect(payment1.pay(100)).toBe("Paid $100 via Credit Card");

    const payment2 = PaymentMethodFactory.createPaymentMethod('paypal');
    expect(payment2.pay(200)).toBe("Paid $200 via PayPal");
  });
});