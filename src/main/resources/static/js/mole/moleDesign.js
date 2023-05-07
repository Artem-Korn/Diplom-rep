window.addEventListener("load", function () {
    updateNumStartPos.call(select_start_pos);
});

document.getElementById("select_start_pos").addEventListener("change", updateNumStartPos);

function updateNumStartPos() {
    switch (this.value) {
        case '0':
            num_start_pos.classList.add("_hide");
            break;
        case '1':
            num_start_pos.classList.remove("_hide");
            break;
        default:
            throw new Error("Помилка у виборі стартової позиції!")
    }
}