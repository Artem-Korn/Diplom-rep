Array.from(document.querySelectorAll('.group_check'))
    .forEach(e => {
        e.addEventListener('change', checkGroup);
        e.parentNode.parentNode.parentNode.addEventListener('click', () => {
            e.checked = !e.checked;
            checkGroup.call(e);
        });
    });

Array.from(document.querySelectorAll('.single_checkbox'))
    .forEach(e => {
        e.addEventListener('change', checkSingle);
        e.parentNode.parentNode.parentNode.addEventListener('click', () => {
            e.checked = !e.checked;
            checkSingle.call(e);
        });
        checkSingle.call(e);
    });

function checkGroup(){
    Array.from(this.parentNode.parentNode.parentNode.parentNode.querySelectorAll('.single_checkbox'))
        .forEach(e => {
            if(e.checked !== this.checked) {
                e.checked = this.checked
                checkSingle.call(e);
            }
        });
}

function checkSingle(){
    let table = this.parentNode.parentNode.parentNode.parentNode.parentNode;

    if (this.checked) this.parentNode.parentNode.parentNode.classList.add("_checked");
    else this.parentNode.parentNode.parentNode.classList.remove("_checked");

    Array.from(table.parentNode.querySelectorAll('.single_checkbox'))
        .forEach(e => {
            if(e.value === this.value && e.checked !== this.checked) {
                setCheckbox(e, this.checked);
                checkSingle.call(e);
            }
        });

    let checkboxes = Array.from(table.querySelectorAll('.single_checkbox'));
    let group_checkbox = table.querySelector('.group_check');

    let is_checked = checkboxes[0].checked;
    let is_changed = false;

    checkboxes.forEach(e => {
        if(e.checked !== is_checked)
            is_changed = true;
    });

    if(is_changed) {
        group_checkbox.indeterminate = true;
        group_checkbox.parentNode.parentNode.parentNode.classList.remove("_checked");
        group_checkbox.parentNode.parentNode.parentNode.classList.add("_indeterminate");
    }
    else {
        group_checkbox.indeterminate = false;
        group_checkbox.parentNode.parentNode.parentNode.classList.remove("_indeterminate");
        setCheckbox(group_checkbox, is_checked);
    }
}

function setCheckbox(elem, checked) {
    if(checked) {
        elem.checked = true;
        elem.parentNode.parentNode.parentNode.classList.add("_checked");
    }
    else {
        elem.checked = false;
        elem.parentNode.parentNode.parentNode.classList.remove("_checked");
    }
}