const fs = require('fs');
const Justifier = require('./justifier');

const DEFAULT_WIDTH = 17;

const response = fs.readFileSync('/dev/stdin').toString();
const justifier = new Justifier(response, process.argv[2] || DEFAULT_WIDTH);
justifier.solve();
console.log(justifier.getResult());