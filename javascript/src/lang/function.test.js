import { describe, expect, test } from "vitest";

describe("Javascript/Function", () => {
  test("Named function expression", () => {
    /* A named function expression can be accessed by function name only within the function itself */
    const myVariable = function myFunction() {
      return `I'm a ${typeof myFunction}.`;
    };

    expect(typeof myFunction).toBe("undefined");
    expect(typeof myVariable).toBe("function");
    expect(myVariable()).toBe("I'm a function.");
  });

  test("Arrow function expression", () => {
    /* Arrow functions don't have their own context for arguments or this values */
    function myFunction() {
      let myArrowFunction = () => {
        return arguments[0];
      }
      return myArrowFunction(true);
    };

    expect(myFunction(false)).toBe(false);
  });
});