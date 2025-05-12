import { describe, expect, test, vi } from "vitest";
import { EmailAlert, NewsAgency, SMSAlert } from "./example";

describe("DesignPatter/Observer", () => {
  test("Notify subscribers", () => {
    const agancy = new NewsAgency();
    const emailAlert = new EmailAlert();
    const smsAlert = new SMSAlert();

    const email = vi.spyOn(emailAlert, "apply");
    const sms = vi.spyOn(smsAlert, "apply");

    agancy.subscribe(emailAlert);
    agancy.subscribe(smsAlert);
    agancy.publishNews("JavaScript is good");

    expect(email).toBeCalledTimes(1);
    expect(sms).toBeCalledTimes(1);

    agancy.unsubscribe(smsAlert);
    agancy.publishNews("Typescript is better");

    expect(email).toBeCalledTimes(2);
    expect(sms).toBeCalledTimes(1);
  });
});