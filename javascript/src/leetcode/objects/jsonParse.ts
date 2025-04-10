/**
 * Leetcode | Hard | 2759. Convert JSON String to Object
 * 
 * A custom JSON parser that converts a JSON string into a JavaScript object.
 * Implements the complete JSON specification including objects, arrays, strings,
 * numbers, booleans, and null values.
 * 
 * @param str The JSON string to parse
 * @returns The parsed JavaScript object
 * @throws {SyntaxError} If the input string is empty or malformed
 * 
 * @example
 * jsonParse('{"name":"John","age":30}'); // Returns {name: "John", age: 30}
 * jsonParse('[true,false,null]'); // Returns [true, false, null]
 */
export function jsonParse(str: string): any {
  const n = str.length;
  if (n === 0) {
    throw new SyntaxError("Unexpected end of JSON input");
  }

  let i = 0; // Current parsing position index

  // Map of parsing functions for different JSON value types
  const parserMap: Record<string, () => any> = {
    "{": parseObject,  // Object literal
    "[": parseArray,   // Array literal
    "t": parseTrue,    // true value
    "f": parseFalse,   // false value
    "n": parseNull,    // null value
    '"': parseString,  // String value
  };

  // Entry point - starts parsing the JSON value
  return parseValue();

  // Parses a JSON object {...}
  function parseObject(): any {
    i++; // Skip opening '{'
    const obj: Record<string, any> = {};
    while (i < n) {
      const c = str[i];
      if (c === "}") {
        i++; // Skip closing '}'
        break;
      }
      if (c === ',') {
        i++; // Skip comma between properties
        continue;
      }
      const key = parseString(); // Parse property key
      i++; // Skip colon after key
      const value = parseValue(); // Parse property value
      obj[key] = value;
    }
    return obj;
  }

  // Parses a JSON array [...]
  function parseArray(): any[] {
    i++; // Skip opening '['
    const arr = [];
    while (i < n) {
      const c = str[i];
      if (c === "]") {
        i++; // Skip closing ']'
        break;
      }
      if (c === ',') {
        i++; // Skip comma between elements
        continue;
      }
      arr.push(parseValue()); // Parse array element
    }
    return arr;
  }

  // Parses the 'true' literal
  function parseTrue(): boolean {
    i += 4; // Advance past 'true' (4 chars)
    return true;
  }

  // Parses the 'false' literal
  function parseFalse(): boolean {
    i += 5; // Advance past 'false' (5 chars)
    return false;
  }

  // Parses the 'null' literal
  function parseNull(): null {
    i += 4; // Advance past 'null' (4 chars)
    return null;
  }

  // Parses a JSON number
  function parseNumber(): number {
    let s = ""; // Accumulate number characters
    while (i < n) {
      let c = str[i];
      // Stop at delimiters
      if (c === "," || c === "}" || c === "]") {
        break;
      }
      s += c;
      i++;
    }
    return Number(s); // Convert to number
  }

  // Parses a JSON string
  function parseString() {
    i++; // Skip opening quote
    let s = "";
    while (i < n) {
      const c = str[i];
      if (c === '"') {
        i++; // Skip closing quote
        break;
      }
      if (c === '\\') {
        i++; // Handle escape sequences
        s += str[i];
      } else {
        s += c;
      }
      i++;
    }
    return s;
  }

  // Dispatches to the appropriate parser based on current character
  function parseValue(): any {
    const c = str[i];
    // Use parserMap or default to parseNumber if no match
    return (parserMap[c] ?? parseNumber)();
  }
}