import { MoleGame } from "./moleGame.js";

const canvas = document.getElementById('game_canvas');
const context = canvas.getContext('2d');
const moleGame = new MoleGame(canvas, context);

window.addEventListener("load", function () {
    updateSize();
    updateGrid();
    updateStartPos();
    updateStepsCount();
    updateSpeed();
    updateHideGrid();
    updateIsDemo();
    render();
});

window.addEventListener("resize", updateSize);

document.getElementById("select_rows").addEventListener("change", updateGrid);
document.getElementById("select_columns").addEventListener("change", updateGrid);

document.getElementById("select_start_pos").addEventListener("change", updateStartPos);
document.getElementById("num_start_pos").addEventListener("change", updateStartPos);

document.getElementById("select_steps_count").addEventListener("change", updateStepsCount);

document.getElementById("select_speed").addEventListener("change", updateSpeed);

document.getElementById("select_hide").addEventListener("change", updateHideGrid);

document.getElementById("select_demo").addEventListener("change", updateIsDemo);

document.getElementById("btn_start").addEventListener("click", updateGameStat);

document.getElementById("btn_check").addEventListener("click", checkGame);

function updateSize() {
    moleGame.updateSize();
}

function updateGrid() {
    moleGame.setGrid(select_rows.value, select_columns.value);
    num_start_pos.max = select_rows.value * select_columns.value;
    num_start_pos.value = 1;
    updateStartPos();
}

function updateStepsCount() {
    moleGame.setStepsCount(select_steps_count.value);
}

function updateSpeed() {
    moleGame.setSpeed(select_speed.value);
}

function updateHideGrid() {
    moleGame.setHideGrid(select_hide.value);
}

function updateIsDemo() {
    moleGame.setIsDemo(select_demo.value);
}

function updateStartPos() {
    if (select_start_pos.value === '1') moleGame.setMoleIndex(num_start_pos.value);
    else moleGame.setRandomMoleIndex();
}

function updateGameStat() {
    moleGame.updateGameStat(btn_check);
}

function checkGame() {
    mark.value = moleGame.checkGame(answer.value);
}

function clear() {
    context.clearRect(0, 0, canvas.width, canvas.height);
}

function render() {
    requestAnimationFrame(render);

    clear();
    moleGame.render();
}