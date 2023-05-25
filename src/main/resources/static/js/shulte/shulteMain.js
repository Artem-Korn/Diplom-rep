import { ShulteTable } from "./shulteTable.js";

const canvas = document.getElementById('game_canvas');
const context = canvas.getContext('2d');
const table = new ShulteTable(canvas, context);

window.addEventListener("load", function () {
    updateSize();
    updateHeader();
    updateGameTime();
    updateGrid();
    render();
});

window.addEventListener("resize", updateSize);

document.getElementById("select_type").addEventListener("change", updateHeader);
document.getElementById("select_topic").addEventListener("change", updateHeader);

document.getElementById("select_element_count").addEventListener("change", updateHeader);

document.getElementById("select_rows").addEventListener("change", updateGrid);
document.getElementById("select_columns").addEventListener("change", updateGrid);

document.getElementById("select_time").addEventListener("change", updateGameTime);

document.getElementById("select_is_colorfull").addEventListener("change", updateGridColors);

document.getElementById("btn_start").addEventListener("click", updateGameStat);

document.getElementById("btn_check").addEventListener("click", checkGame);

function updateSize() {
    table.updateSize();
}

function updateGameTime() {
    table.setGameTime(select_time.value);
    updateDifficulty();
}

function updateGridColors() {
    table.setGridColors(select_is_colorfull.value);
    updateDifficulty();
}

function updateHeader() {
    let searching_elem = table.setHeaderText(select_type.value, select_topic.value, select_element_count.value);
    if (searching_elem != null) {
        let count_arr = document.getElementsByClassName("_count_elem");
        for (let i = 0; i < searching_elem.length; i++) count_arr[i].innerHTML = searching_elem[i] + ":";
    }
    updateDifficulty();
}

function updateGrid() {
    table.setGrid(select_rows.value, select_columns.value);
    updateDifficulty();
}

function updateDifficulty() {
    difficulty.value = table.getDifficulty();
}

function updateGameStat() {
    table.updateGameStat(btn_check);
}

function checkGame() {
    mark.value = table.checkGame(document.getElementsByClassName("_check"));
}

function clear() {
    context.clearRect(0, 0, canvas.width, canvas.height);
}

function render() {
    requestAnimationFrame(render);

    clear();
    table.render();
}