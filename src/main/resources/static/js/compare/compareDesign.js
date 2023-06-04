window.addEventListener("load", function () {
    if(document.getElementById("task_option_1")) setTaskOptions();
});

function setTaskOptions() {
    setValues(task_option_1, "Немає", "Випадкові", "Додавання", "Віднімання", "Множення", "Ділення");
    if(Number(task_option_3.value) >= 60)
        task_option_3.innerHTML = (task_option_3.value / 60) + "хв.";
    else
        task_option_3.innerHTML = task_option_3.value + "с.";
}

function setValues(element, ...text_values) {
    element.innerHTML = text_values[element.value];
}