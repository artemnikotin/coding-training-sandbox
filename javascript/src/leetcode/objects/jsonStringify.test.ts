import { expect, test } from 'vitest';

import { jsonStringify } from './jsonStringify';

test('primetives', () => {
  const primetives = [
    1,
    false,
    'str',
    null,
    undefined,
  ];
  for (let value of primetives) {
    expect(jsonStringify(value)).toEqual(JSON.stringify(value));
  }
});

test('arrays', () => {
  const primetives = [
    [],
    ['str'],
    [1, false, 'str', null, undefined],
    new Array(10),
  ];
  for (let value of primetives) {
    expect(jsonStringify(value)).toEqual(JSON.stringify(value));
  }
});

test('objects', () => {
  const primetives = [
    {},
    {a: 'str'},
    {a: 1, b: false, c: 'str', d: null, e: undefined},
  ];
  for (let value of primetives) {
    expect(jsonStringify(value)).toEqual(JSON.stringify(value));
  }
});

test('nested array', () => {
  const arr = [
    [1, 2],
    ['a', 'b'],
    [
      [1], ['a'],
    ],
    [
      [2], [[[]]],
    ],
  ];
  expect(jsonStringify(arr)).toEqual(JSON.stringify(arr));
});

test('nested object', () => {
  const obj = {
    [1]: {
      a: {
        b: {},
      },
      c: null,
    },
    ['2']: {
      a: {},
      b: {
        c: undefined,
      }
    }
  };
  expect(jsonStringify(obj)).toEqual(JSON.stringify(obj));
});