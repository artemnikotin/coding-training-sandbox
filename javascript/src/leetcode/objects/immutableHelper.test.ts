import { beforeEach, describe, expect, test } from "vitest";

import { ImmutableHelper } from "./immutableHelper";

type OriginalObject = {
  val: number,
  arr: number[],
  obj: {
    val: {
      x: number,
      y: number,
    }
  }
  newVal?: number,
}

let originalObject: OriginalObject;
let originalHelper: ImmutableHelper<OriginalObject>;

describe("Leetcode | 2691. Immutability Helper", () => {
  beforeEach(() => {
    originalObject = {
      val: 10,
      arr: [1, 2, 3],
      obj: {
        val: {
          x: 10,
          y: 20,
        },
      },
    };

    originalHelper = new ImmutableHelper(originalObject);
  });

  test("in-object single-depth mutation", () => {
    expect(originalHelper.produce(proxy => { proxy.val += 1; })).toEqual({...originalObject, val: 11 });
    expect(originalHelper.produce(proxy => { proxy.val -= 1; })).toEqual({...originalObject, val: 9 });
  });

  test("in-object array mutation", () => {
    const cloneObject = structuredClone(originalObject);

    const mutator = ((proxy: OriginalObject) => {
      proxy.arr[0] = 5;
      proxy.newVal = proxy.arr[0] + proxy.arr[1];
    });

    expect(originalHelper.produce(mutator)).toEqual({
      ...originalObject,
      arr: [5, 2, 3],
      newVal: 7,
    });
    expect(originalObject).toEqual(cloneObject);
  });

  test("nested object mutation", () => {
    const cloneObject = structuredClone(originalObject);

    const mutator = ((proxy: OriginalObject) => {
      let data = proxy.obj.val;
      let temp = data.x;
      data.x = data.y;
      data.y = temp;
    });

    expect(originalHelper.produce(mutator)).toEqual({
      ...originalObject,
      obj: {
        val: {
          x: 20,
          y: 10,
        },
      },
    });
    expect(originalObject).toEqual(cloneObject);
  });

  test("array mutation", () => {
    const arr = [1, 2, 3];
    const clone = [...arr];

    const helper = new ImmutableHelper(arr);
    
    expect(helper.produce(proxy => proxy[1] = 5)).toEqual([1, 5, 3]);
    expect(arr).toEqual(clone);
  });
});