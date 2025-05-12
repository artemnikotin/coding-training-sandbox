import { AbstractObserver, Subscriber } from "./observer";

export class NewsAgency extends AbstractObserver<string> {
  publishNews(news: string): void {
    this.notifySubscribers(news);
  }
}

export class EmailAlert implements Subscriber<string> {
  apply(_: string): void {
  }
}

export class SMSAlert implements Subscriber<string> {
  apply(_: string): void {
  }
}