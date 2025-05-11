import { describe, expect, test } from "vitest";
import { UrlBuilder } from "./example";

describe("DesignPattern/Builder", () => {
  test("UrlBuilder", () => {
    const builder = new UrlBuilder();

    let url = builder.setProtocol('https')
      .setHostname('api.example.com')
      .setPort(8080)
      .addPathSegment('users')
      .addPathSegment('123')
      .build();
    expect(url).toBe("https://api.example.com:8080/users/123");

    url = builder
      .addQueryParam('sort', 'asc')
      .addQueryParam('page', '1')
      .build();
    expect(url).toBe("https://api.example.com:8080/users/123?sort=asc&page=1");
  });
});