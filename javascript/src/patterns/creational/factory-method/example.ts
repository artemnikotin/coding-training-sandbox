interface PaymentMethod {
  pay(amount: number): string;
}

class CreditCard implements PaymentMethod {
  pay(amount: number): string {
    return `Paid $${amount} via Credit Card`;
  }
}

class PayPal implements PaymentMethod {
  pay(amount: number): string {
    return `Paid $${amount} via PayPal`;
  }
}

class BankTransfer implements PaymentMethod {
  pay(amount: number): string {
    return `Paid $${amount} via Bank Transfer`;
  }
}

type PaymentMethodType = 'credit-card' | 'paypal' | 'bank-transfer';

export class PaymentMethodFactory {
  public static createPaymentMethod(type: PaymentMethodType): PaymentMethod {
    switch (type) {
      case 'credit-card':
        return new CreditCard();
      case 'paypal':
        return new PayPal();
      case 'bank-transfer':
        return new BankTransfer();
      default:
        throw new Error(`Invalid payment method type: ${type}`);
    }
  }
}