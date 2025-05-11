interface Builder<T> {
  build(): T;
}

export class UrlBuilder implements Builder<string> {
  private protocol: string = 'https';
  private hostname: string = '';
  private port: number | null = null;
  private pathSegments: string[] = [];
  private queryParams: Record<string, string> = {};

  /**
   * Sets the protocol (http or https)
   */
  setProtocol(protocol: 'http' | 'https'): this {
    this.protocol = protocol;
    return this;
  }

  /**
   * Sets the hostname (e.g., "example.com")
   */
  setHostname(hostname: string): this {
    this.hostname = hostname;
    return this;
  }

  /**
   * Sets the port (e.g., 8080)
   */
  setPort(port: number): this {
    this.port = port;
    return this;
  }

  /**
   * Adds a path segment (e.g., "api", "v1")
   */
  addPathSegment(segment: string): this {
    this.pathSegments.push(segment);
    return this;
  }

  /**
   * Adds a query parameter (e.g., "page=1")
   */
  addQueryParam(key: string, value: string): this {
    this.queryParams[key] = value;
    return this;
  }

  /**
   * Builds the final URL string
   */
  build(): string {
    if (!this.hostname) {
      throw new Error('Hostname must be set');
    }

    // Construct base URL (protocol + hostname + port)
    let url = `${this.protocol}://${this.hostname}`;
    if (this.port) {
      url += `:${this.port}`;
    }

    // Add path segments
    if (this.pathSegments.length > 0) {
      url += `/${this.pathSegments.join('/')}`;
    }

    // Add query parameters
    const queryString = Object.entries(this.queryParams)
      .map(([key, value]) => `${encodeURIComponent(key)}=${encodeURIComponent(value)}`)
      .join('&');

    if (queryString) {
      url += `?${queryString}`;
    }

    return url;
  }
}