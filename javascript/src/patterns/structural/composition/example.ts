interface Graphic {
  move(x: number, y: number): void;
  render(): string;
}

export class Circle implements Graphic {
  constructor(private x: number, private y: number, private radius: number) { }

  move(x: number, y: number): void {
    this.x += x;
    this.y += y;
  }

  render(): string {
    return `circle(${this.x},${this.y},${this.radius})`;
  }
}

export class Square implements Graphic {
  constructor(private x: number, private y: number, private side: number) { }

  move(x: number, y: number): void {
    this.x += x;
    this.y += y;
  }

  render(): string {
    return `square(${this.x},${this.y},${this.side})`;
  }
}

export class Container implements Graphic {
  private children: Graphic[] = [];

  add(graphic: Graphic): void {
    this.children.push(graphic);
  }

  remove(graphic: Graphic): void {
    const index = this.children.indexOf(graphic);
    if (index !== -1) {
      this.children.splice(index, 1);
    }
  }

  move(x: number, y: number): void {
    for (const child of this.children) {
      child.move(x, y);
    }
  }

  render(): string {
    return `[${this.children.map(el => el.render()).join(',')}]`;
  }
}