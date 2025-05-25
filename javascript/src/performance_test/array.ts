console.log("Performance test of array's add and remove method");
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

// TODO implement Deque with following methods:
/* [
  'constructor', 'pushFront',
  'pushBack',    'popFront',
  'popBack',     'front',
  'back',        'size',
  'isEmpty',     'toArray',
  'clear',       'clone'
]*/