window.addEventListener("load", function () {
    updateNumStartPos.call(select_start_pos);
    if(document.getElementById("task_option_1")) setTaskOptions();
});

function setTaskOptions() {
    setValues(task_option_3, "Випадкова", "Задати");
    task_option_5.innerHTML = task_option_5.value + "с.";
    setValues(task_option_6, "ні", "так");
    setValues(task_option_7, "ні", "так");
}

function setValues(element, ...text_values) {
    element.innerHTML = text_values[element.value];
}

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