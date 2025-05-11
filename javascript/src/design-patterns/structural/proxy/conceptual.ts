/**
 * Proxy is a structural design pattern that provides an object that acts as a substitute for a real service object used by a client.
 * Common Proxy Types:
 * - Virtual Proxy: Postpones expensive operations until needed (like lazy loading)
 * - Protection Proxy: Controls access to sensitive objects
 * - Remote Proxy: Represents an object in a different address space
 * - Logging Proxy: Adds logging to method calls
 * - Caching Proxy: Provides temporary storage for results
 */

// What clients will use - could be real object or proxy
interface Service {
  doWork(): void;
}

// The actual worker
class RealService implements Service {
  doWork(): void {
  }
}

// Proxy - stands in for the real service
class ServiceProxy implements Service {
  private realService: RealService;

  constructor(realService: RealService) {
    this.realService = realService;
  }

  doWork(): void {
    // Can add extra behavior before/after
    this.checkAccess();
    this.realService.doWork();
    this.logUsage();
  }

  private checkAccess(): void {
  }

  private logUsage(): void {
  }
}

// Client code works with Service interface
export function runApp(service: Service) {
  service.doWork();
}

// Usage
runApp(new RealService());
runApp(new ServiceProxy(new RealService()));