window.addEventListener("load", function () {
    updateType.call(select_type);
    updateElementCount.call(select_element_count);
    if(document.getElementById("task_option_1")) setTaskOptions();
});

document.getElementById("select_type").addEventListener("change", updateType);
document.getElementById("select_element_count").addEventListener("change", updateElementCount);

function updateType() {

    let sequence_arr = document.getElementsByClassName("_sequence");
    let counting_arr = document.getElementsByClassName("_counting");

    switch (this.value) {
        case "0":
            swapTopics(counting_arr, sequence_arr);
            break;
        case "1":
            swapTopics(sequence_arr, counting_arr);
            break;
        default:
            throw new Error("Помилка у виборі типу завдання!")
    }
}

function swapTopics(hid_element, show_element) {
    for (let i = 0; i < hid_element.length; i++) hid_element[i].classList.add("_hide");
    for (let i = 0; i < show_element.length; i++) show_element[i].classList.remove("_hide");
    if(!document.getElementById("task_option_1")) show_element[0].selected = true;
}

function updateElementCount() {
    let i = 0;
    let answers = document.getElementsByClassName("_answer");

    if (this.value <= answers.length && this.value > 0) {
        for (i; i < this.value; i++) answers[i].classList.remove("_hide");
        for (i; i < answers.length; i++) answers[i].classList.add("_hide");
    }
}

function setTaskOptions() {
    setValues(task_option_1, "Без кольору", "Кольорові");
    setValues(task_option_2, "Послідовність", "Підрахунок");
    if(task_option_2.value === String(0))
        setValues(task_option_3, "Цифри", "Великі літери", "Малі літери");
    else
        setValues(task_option_3, "Фігури", "Тварини");
    task_option_4.innerHTML = task_option_4.value + "с.";
}

function setValues(element, ...text_values) {
    element.innerHTML = text_values[element.value];
}