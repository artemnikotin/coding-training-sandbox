/**
 * Mediator is a behavioral design pattern that lets you reduce chaotic dependencies between objects.
 * The pattern restricts direct communications between the objects and forces them to collaborate only via a mediator object.
 * Key Benefits:
 * - Reduced Coupling: Components communicate through mediator rather than directly
 * - Centralized Control: Mediator contains interaction logic
 * - Simplified Objects: Colleagues become simpler and more focused
 * - Easier Maintenance: Communication logic is in one place
 */

export interface Mediator<Component, Event> {
  notify(sender: Component, event: Event): void;
}