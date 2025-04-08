import { describe, expect, test } from "vitest";
import { makeImmutable } from "./makeImmutable";

describe("Leetcode | 2692. Make Object Immutable", () => {
  test("modifying object property", () => {
    const obj = {
      "x": 5
    };
    const immutableObj = makeImmutable(obj);
    expect(() => immutableObj.x = 5).toThrowError("Error Modifying: x");
    expect(() => obj.x = 5).not.toThrow();
  });

  test("modifying array value", () => {
    const obj = [1, 2, 3];
    const immutableObj = makeImmutable(obj);
    expect(() => immutableObj[1] = 2).toThrowError("Error Modifying Index: 1");
    expect(() => obj[1] = 2).not.toThrow();
  });

  test("modifying array by method call", () => {
    const obj = [1, 2, 3];
    const immutableObj = makeImmutable(obj);
    expect(() => immutableObj.push(4)).toThrowError("Error Calling Method: push");
    expect(() => obj.push(4)).not.toThrow();
  });

  test("no mutations", () => {
    const obj = {
      "x": 2,
      "y": 2,
    };
    const immutableObj = makeImmutable(obj);
    expect(() => Object.keys(immutableObj)).not.toThrow();
  });
});