/**
 * Chain of Responsibility is a behavioral design pattern that lets you pass requests along a chain of handlers.
 * Upon receiving a request, each handler decides either to process the request or to pass it to the next handler in the chain.
 * Real-World Examples:
 * - Tech support escalation (Level 1 → Level 2 → Level 3)
 * - Approval workflows (Manager → Director → VP → CEO)
 * - Middleware pipelines (Express.js middleware)
 * - Exception handling (try different handlers)
 */
export interface Handler<Request, Result> {
  setNext(handler: Handler<Request, Result>): Handler<Request, Result>;
  handle(request: Request): Result | null;
}

export abstract class AbstractHandler<Request, Result> implements Handler<Request, Result> {
  private nextHandler?: Handler<Request, Result>;

  public setNext(handler: Handler<Request, Result>) {
    this.nextHandler = handler;
    return handler;
  }

  public handle(request: Request) {
    if (this.nextHandler) {
      return this.nextHandler.handle(request);
    }

    return null;
  }
}