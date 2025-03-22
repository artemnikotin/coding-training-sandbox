import { beforeEach, expect, it } from 'vitest';

import { EventEmitter } from "./EventEmitter";

let eventEmitter: EventEmitter;

beforeEach(() => {
  eventEmitter = new EventEmitter;
});

it('subscribe and emits multiple callbacks', () => {
  eventEmitter.subscribe('event', (a) => a);
  eventEmitter.subscribe('event', (a, b) => [a, b]);
  eventEmitter.subscribe('event', (a, b, c) => a + b + c);
  expect(eventEmitter.emit('event', [1, 2, 3])).toStrictEqual([1, [1, 2], 6]);
});

it('subscribe to different events', () => {
  eventEmitter.subscribe('event 1', (a) => a);
  eventEmitter.subscribe('event 2', (a, b) => [a, b]);

  expect(eventEmitter.emit('event 1', [1, 2])).toStrictEqual([1]);
  expect(eventEmitter.emit('event 2', [1, 2])).toStrictEqual([[1, 2]]);
});

it('return empty array on emits event with no subscription', () => {
  expect(eventEmitter.emit('event', [1])).toStrictEqual([]);
});

it('unsubscribe and emit callbacks in insertion order', () => {
  eventEmitter.subscribe('event', (a) => a);
  const subscription = eventEmitter.subscribe('event', (a, b) => [a, b]);
  eventEmitter.subscribe('event', (a, b, c) => a + b + c);
  subscription.unsubscribe();
  expect(eventEmitter.emit('event', [1, 2, 3])).toStrictEqual([1, 6]);
});