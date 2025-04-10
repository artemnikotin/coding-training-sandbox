import { describe, expect, test } from "vitest";
import { jsonParse } from "./jsonParse";

describe("Leetcode | 2759. Convert JSON String to Object", () => {
  test("the object represented by the JSON string", () => {
    const str = '{"a":2,"b":[1,2,3]}';
    const obj = { "a": 2, "b": [1, 2, 3] };
    expect(jsonParse(str)).toEqual(obj);
  });

  test("primitive types are valid JSON", () => {
    const str = 'true';
    const obj = true;
    expect(jsonParse(str)).toEqual(obj);
  });

  test("the array represented by the JSON string", () => {
    const str = '[1,5,"false",{"a":2}]';
    const obj = [1, 5, "false", { "a": 2 }];
    expect(jsonParse(str)).toEqual(obj);
  });
});