/**
 * The Command pattern encapsulates a request as an object, allowing you to parameterize clients with different requests,
 * queue or log requests, and support undoable operations.
 * Key Benefits:
 * - Decoupling: Separates invoker from receiver
 * - Undo/Redo: Easy to implement command history
 * - Queueing: Commands can be queued for later execution
 * - Extensibility: Easy to add new commands
 */

export interface Command {
  execute(): void;
}

export interface ReversibleCommand extends Command {
  undo(): void;
}