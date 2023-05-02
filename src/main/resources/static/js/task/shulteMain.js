import { ShulteTable } from "../shulteTable.js";

const canvas = document.getElementById('game_canvas');
const context = canvas.getContext('2d');
const table = new ShulteTable(canvas, context);

window.addEventListener("load", function () {
    updateSize();
    updateHeader();
    table.setGameTime(select_time.value);
    table.setGrid(select_rows.value, select_columns.value);
    table.setGridColors(select_is_colorfull.value);
    render();
});

window.addEventListener("resize", updateSize);

document.getElementById("btn_start").addEventListener("click", updateGameStat);

document.getElementById("btn_check").addEventListener("click", checkGame);

function updateSize() {
    table.updateSize();
}

function updateGameStat() {
    if (!table.isStarted())
        table.updateGameStat(btn_check);
    btn_start_container.style.display = "none";
}

function checkGame() {
    mark.value = table.checkGame(document.getElementsByClassName("_check"));
}

function updateHeader() {
    let searching_elem = table.setHeaderText(select_type.value, select_topic.value, select_element_count.value);
    if (searching_elem != null) {
        let count_arr = document.getElementsByClassName("_count_elem");
        for (let i = 0; i < searching_elem.length; i++) count_arr[i].innerHTML = searching_elem[i] + ":";
    }
}

function clear() {
    context.clearRect(0, 0, canvas.width, canvas.height);
}

function render() {
    requestAnimationFrame(render);

    clear();
    table.render();
}