console.log("Performance test of array's add and remove method");
/** Array push, pop */
let p = performance.now();
let arr: number[] = [];
for (let i = 0; i < 1_000_000; i++) {
  arr.push(i);
}
p = performance.now() - p;
console.log("array.push() x 1.000.000 elements", p.toFixed(2));

p = performance.now();
for (let i = 0; i < 1_000_000; i++) {
  arr.pop();
}
p = performance.now() - p;
console.log("array.pop() x 1.000.000 elements", p.toFixed(2));

/** Array unshift, shift */
p = performance.now();
arr = [];
for (let i = 0; i < 100_000/*!*/; i++) {
  arr.unshift(i);
}
p = performance.now() - p;
console.log("array.unshift() x 100.000 elements", p.toFixed(2));

p = performance.now();
for (let i = 0; i < 10_000/*!!!*/; i++) {
  arr.shift();
}
p = performance.now() - p;
console.log("array.shift() x 10.000 elements", p.toFixed(2));

/** Array splice */
p = performance.now();
arr = [];
for (let i = 0; i < 100_000/*!*/; i++) {
  arr.splice(0, 0, i);
}
p = performance.now() - p;
console.log("array.splice() - add x 100.000 elements", p.toFixed(2));

p = performance.now();
for (let i = 0; i < 10_000/*!!!*/; i++) {
  arr.splice(0, 1);
}
p = performance.now() - p;
console.log("array.splice() - remove x 100.000 elements", p.toFixed(2));

/** pop with overhead */
arr = [];
for (let i = 0; i < 1_000_000; i++) {
  arr.push(i);
}
p = performance.now();
for (let i = 0; i < 1_000_000; i++) {
  if (i % 2 === 0) {
    arr.pop();
  } else {
    arr.pop();
  }
}
p = performance.now() - p;
console.log("array.pop() x 1.000.000 elements (overhead costs for comparison)", p.toFixed(2));

/** pop / shift */
arr = [];
for (let i = 0; i < 100_000; i++) {
  arr.push(i);
}
p = performance.now();
for (let i = 0; i < 100_000/*!*/; i++) {
  if (i % 2 === 0) {
    arr.pop();
  } else {
    arr.shift();
  }
}
p = performance.now() - p;
console.log("array.pop()/array.shift() x 100.000 elements", p.toFixed(2));

/** pop / slice */
arr = [];
for (let i = 0; i < 100_000; i++) {
  arr.push(i);
}
p = performance.now();
for (let i = 0; i < 100_000/*!*/; i++) {
  if (i % 2 === 0) {
    arr.pop();
  } else {
    arr.splice(0, 1);
  }
}
p = performance.now() - p;
console.log("array.pop()/array.splice() x 100.000 elements", p.toFixed(2));

// TODO implement Deque with following methods:
/* [
  'constructor', 'pushFront',
  'pushBack',    'popFront',
  'popBack',     'front',
  'back',        'size',
  'isEmpty',     'toArray',
  'clear',       'clone'
]*/