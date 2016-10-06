const fs = require('fs');
const childProcess = require('child_process');
const Canvas = require('canvas');

const CANVAS_SIZE = 800;
const COLOR_WHITE = 'rgb(255, 255, 255);';
const COLOR_BLACK = 'rgb(0, 0, 0);';
const COLOR_RED = 'rgb(255, 0, 0)';
const POINT_SIZE = 3;

function drawPoint(point, canvas) {
    canvas.beginPath();
    canvas.arc(point[0], point[1], POINT_SIZE, 0, 2 * Math.PI, true);
    canvas.stroke();
    canvas.fill();
}

function drawList(_list, resultPath) {
    const Image = Canvas.Image;
    const WIDTH = CANVAS_SIZE + POINT_SIZE * 2;
    const HEIGHT = CANVAS_SIZE + POINT_SIZE * 2;
    const canvas = new Canvas(WIDTH, HEIGHT);
    const ctx = canvas.getContext('2d');
    ctx.fillStyle = COLOR_WHITE;
    ctx.fillRect(0, 0, WIDTH, HEIGHT);
    ctx.strokeStyle = COLOR_BLACK;

    let factor = Math.max(
        Math.max.apply(Math, _list.map(x => x[0])),
        Math.max.apply(Math, _list.map(x => x[1]))
    );

    function transformCoords(point) {
        const x = point[0] / factor * CANVAS_SIZE;
        const y = point[1] / factor * CANVAS_SIZE;
        return [x, y];
    }

    const list = _list.map(x => transformCoords(x));

    ctx.fillStyle = COLOR_BLACK;
    // Draw points
    for (let i = 1; i < list.length; ++i) {
        drawPoint(list[i], ctx);
    }
    ctx.fillStyle = COLOR_RED;
    drawPoint(list[0], ctx);

    // draw path
    ctx.beginPath();
    for (let i = 1; i < list.length; ++i) {
        ctx.moveTo(list[i - 1][0], list[i - 1][1]);
        ctx.lineTo(list[i][0], list[i][1]);
    }
    ctx.stroke();
    fs.writeFileSync(resultPath, canvas.toBuffer());
}

module.exports = drawList;
