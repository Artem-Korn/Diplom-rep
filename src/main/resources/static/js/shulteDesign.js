window.addEventListener("load", function () {
    updateType.call(select_type);
    updateElementCount.call(select_element_count);
});

select_type.addEventListener("change", updateType);
select_element_count.addEventListener("change", updateElementCount);

function updateType() {
    let sel_index = 0;
    let sequence_arr = document.getElementsByClassName("_sequence");
    let counting_arr = document.getElementsByClassName("_counting");

    switch (this.value) {
        case "sequence":
            for (let i = 0; i < counting_arr.length; i++) counting_arr[i].classList.add("_hide");
            for (let i = 0; i < sequence_arr.length; i++) {
                sequence_arr[i].classList.remove("_hide");
                if (sequence_arr[i].selected) sel_index = i;
            }
            sequence_arr[sel_index].selected = true;
            break;
        case "counting":
            for (let i = 0; i < sequence_arr.length; i++) sequence_arr[i].classList.add("_hide");
            for (let i = 0; i < counting_arr.length; i++) {
                counting_arr[i].classList.remove("_hide");
                if (counting_arr[i].selected) sel_index = i;
            }
            counting_arr[sel_index].selected = true;
            break;
        default:
            throw new Error("Помилка у виборі типу завдання!")
    }
}

function updateElementCount() {
    let i = 0;
    let answers = document.getElementsByClassName("_answer");

    if (this.value <= answers.length && this.value > 0) {
        for (i; i < this.value; i++) answers[i].classList.remove("_hide");
        for (i; i < answers.length; i++) answers[i].classList.add("_hide");
    }
}