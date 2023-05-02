import { ShulteTable } from "../shulteTable.js";

const canvas = document.getElementById('game_canvas');
const context = canvas.getContext('2d');
const table = new ShulteTable(canvas, context);

window.addEventListener("load", function () {
    updateSize();
    updateHeader();
    updateGameTime();
    updateGrid();
    select_type.disabled = true;
    select_type.classList.remove("_active");
    select_topic.disabled = true;
    select_topic.classList.remove("_active");
    render();
});

window.addEventListener("resize", updateSize);

document.getElementById("select_rows").addEventListener("change", updateGrid);
document.getElementById("select_columns").addEventListener("change", updateGrid);

document.getElementById("select_time").addEventListener("change", updateGameTime);

document.getElementById("btn_start").addEventListener("click", updateGameStat);

document.getElementById("btn_check").addEventListener("click", checkGame);

function updateSize() {
    table.updateSize();
}

function updateGameTime() {
    table.setGameTime(select_time.value);
}

function updateHeader() {
    table.setHeaderText(select_type.value, select_topic.value, select_element_count.value);
}

function updateGrid() {
    table.setGrid(select_rows.value, select_columns.value);
}

function updateGameStat() {
    table.setGridColors(select_is_colorfull.value);
    table.updateGameStat(btn_check);
}

function checkGame() {
    table.checkGame(document.getElementsByClassName("_check"));
    btn_check.disabled = true;
}

function clear() {
    context.clearRect(0, 0, canvas.width, canvas.height);
}

function render() {
    requestAnimationFrame(render);

    clear();
    table.render();
}