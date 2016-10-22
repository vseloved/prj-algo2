const changeMaking = require('./change-making');

if (process.argv[2]) {
	console.log(changeMaking(process.argv[2]).join(', '));
} else {
	console.log('Please specify argument.');
}