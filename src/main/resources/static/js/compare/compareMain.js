import { CompareGame } from "./compareGame.js";

const canvas = document.getElementById('game_canvas');
const context = canvas.getContext('2d');
const compareGame = new CompareGame(canvas, context);

window.addEventListener("load", function () {
    updateSize();
    updateOperations();
    updateTime();
    updateMax();
    render();
});

window.addEventListener("resize", updateSize);
window.addEventListener("keydown", e => {
    compareGame.checkUserKeyDown(e.code);
});

document.getElementById("select_operation").addEventListener('change', updateOperations);

document.getElementById("select_time").addEventListener('change', updateTime);

document.getElementById("select_max").addEventListener('change', updateMax);

document.getElementById("btn_start").addEventListener("click", updateGameStat);

document.getElementById("btn_check").addEventListener("click", checkGame);

canvas.addEventListener("mousedown", (e) => {
    compareGame.checkUserClick(e);
});

function updateSize() {
    compareGame.updateSize();
}

function updateOperations() {
    compareGame.setOperations(select_operation.value);
    updateDifficulty();
}

function updateTime() {
    compareGame.setTime(select_time.value);
    updateDifficulty();
}

function updateMax() {
    compareGame.setMax(select_max.value);
    updateDifficulty();
}

function updateDifficulty() {
    difficulty.value = compareGame.getDifficulty();
}

function updateGameStat() {
    compareGame.updateGameStat(btn_check);
}

function checkGame() {
    mark.value = compareGame.checkGame();
}

function clear() {
    context.clearRect(0, 0, canvas.width, canvas.height);
}

function render() {
    requestAnimationFrame(render);

    clear();
    compareGame.render();
}