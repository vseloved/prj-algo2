import shuffle from 'shuffle-array';
import request from 'request';

let dist = (v1, v2) => {
    let sum = 0;

    v1.forEach((val, index) => {
        sum += (val - v2[index]) ** 2;
    });

    return Math.sqrt(sum);
};

let updateMax = (val, arr) => {
    let max = 0;

    arr.forEach(obj => {
        max = Math.max(max, obj.d);
    });

    return max;
};

function mode(store) {
    let frequency = {};
    let max = 0;
    let result;

    for (let v in store) {
        frequency[store[v]] = (frequency[store[v]] || 0) + 1;

        if (frequency[store[v]] > max) {
            max = frequency[store[v]];
            result = store[v];
        }
    }

    return result;
}

let kNear = function (k) {
    let training = [];

    this.learn = (vector, label) => {
        let obj = {
            v: vector,
            lab: label
        };

        training.push(obj);
    };

    this.classify = v => {
        let voteBloc = [];
        let maxD = 0;

        training.forEach(obj => {
            let o = {
                d: dist(v, obj.v),
                vote: obj.lab
            };

            if (voteBloc.length < k) {
                voteBloc.push(o);
                maxD = updateMax(maxD, voteBloc);
            } else if (o.d < maxD) {
                let bool = true;
                let count = 0;

                while (bool) {
                    if (Number(voteBloc[count].d) === maxD) {
                        voteBloc.splice(count, 1, o);
                        maxD = updateMax(maxD, voteBloc);
                        bool = false;
                    } else if (count < voteBloc.length - 1) {
                        count++;
                    } else {
                        bool = false;
                    }
                }
            }
        });

        let votes = [];

        voteBloc.forEach(el => {
            votes.push(el.vote);
        });

        return mode(votes);
    };
};

request.get('https://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.data', (error, response, body) => {
    if (!error && response.statusCode == 200) {
        let csv = body;
        let k = 3;
        let machine = new kNear(k);
        let csvStrings = shuffle(csv.split('\n'));

        for (let i = 0; i < csvStrings.length; i++) {
            let strArr = csvStrings[i].split(',');

            if (i < 100) {
                machine.learn([+strArr[0], +strArr[1], +strArr[2], +strArr[3]], strArr[4]);
            } else {
                console.log(machine.classify([+strArr[0], +strArr[1], +strArr[2], +strArr[3]]));
            }
        }
    }
});
