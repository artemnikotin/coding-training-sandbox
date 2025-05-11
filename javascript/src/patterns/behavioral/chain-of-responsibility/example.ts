import { AbstractHandler } from "./CoR";

abstract class ApprovalHandler extends AbstractHandler<string, string> {
}

export class ManagerHandler extends ApprovalHandler {
  public handle(request: string): string | null {
    if (request === "vacation") {
      return `Approved by Manager`;
    }
    return super.handle(request);
  }
}

export class DirectorHandler extends ApprovalHandler {
  public handle(request: string): string | null {
    if (request === "raise") {
      return `Approved by Director`;
    }
    return super.handle(request);
  }
}

export class CEOHandler extends ApprovalHandler {
  public handle(request: string): string | null {
    if (request === "new position") {
      return `Approved by CEO`;
    }
    return super.handle(request);
  }
}

export function approve(handler: ApprovalHandler, request: string) {
  return handler.handle(request) || "Not approved";
}