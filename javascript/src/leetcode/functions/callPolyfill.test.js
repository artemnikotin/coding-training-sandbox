import { expect, test } from 'vitest';
import './callPolyfill';

const fn = function() {
  return this?.value;
}

test('polyfill call function with passed contex', () => {
  const context = {
    value: 3,
  }
  expect(fn()).toBe(undefined);
  expect(fn.callPolyfill(context)).toBe(3);
});

test('polyfill with sealed objects', () => {
  const context = {
    value: 3,
  }
  Object.seal(context);
  expect(fn.callPolyfill(context)).toBe(3);
});
