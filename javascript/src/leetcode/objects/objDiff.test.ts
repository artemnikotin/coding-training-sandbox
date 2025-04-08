import { describe, expect, test } from "vitest";

import { objDiff } from "./objDiff";

describe("2700. Differences Between Two Objects", () => {
  test("add new keys", () => {
    const obj1 = {};
    const obj2 = {
      a: 1,
      b: 2,
    };
    expect(objDiff(obj1, obj2)).toEqual({});
  });

  test("existent property modification", () => {
    const obj1 = {
      a: 1,
      v: 3,
      x: [],
      z: {
        a: null,
      },
    };
    const obj2 = {
      a: 2,
      v: 4,
      x: [],
      z: {
        a: 2,
      },
    };
    expect(objDiff(obj1, obj2)).toEqual({
      a: [1, 2],
      v: [3, 4],
      z: {
        a: [null, 2],
      },
    });
  });

  test("array values modification", () => {
    const obj1 = {
      a: 5,
      v: 6,
      z: [1, 2, 4, [2, 5, 7]],
    };
    const obj2 = {
      a: 5,
      v: 7,
      z: [1, 2, 3, [1]],
    }
    expect(objDiff(obj1, obj2)).toEqual({
      v: [6, 7],
      z: {
        2: [4, 3],
        3: {
          0: [2, 1],
        },
      }
    });
  });

  test("type change", () => {
    const obj1 = {
      a: { b: 1 },
    };
    const obj2 = {
      a: [5],
    };
    expect(objDiff(obj1, obj2)).toEqual({ a: [{ b: 1 }, [5]] });
  });

  test("no modidification, order changec", () => {
    const obj1 = {
      a: [1, 2, {}],
      b: false,
    };
    const obj2 = {
      b: false,
      a: [1, 2, {}]
    };
    expect(objDiff(obj1, obj2)).toEqual({});
  });
});