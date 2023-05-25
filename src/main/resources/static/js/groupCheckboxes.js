Array.from(document.querySelectorAll('.all_check'))
    .forEach(e => e.addEventListener('change', checkGroup));

Array.from(document.querySelectorAll('.checkbox'))
    .forEach(e => {
        e.addEventListener('change', checkSingle);
        checkSingle.call(e);
    });

function checkGroup(){
    Array.from(this.parentNode.parentNode.querySelectorAll('.checkbox'))
        .forEach(e => {
            if(e.checked !== this.checked) {
                e.checked = this.checked
                checkSingle.call(e);
            }
        });
}

function checkSingle(){
    Array.from(this.parentNode.parentNode.parentNode.querySelectorAll('.checkbox'))
        .forEach(e => {
            if(e.value === this.value && e.checked !== this.checked) {
                e.checked = this.checked;
                checkSingle.call(e);
            }
        });

    let checkboxes = Array.from(this.parentNode.parentNode.querySelectorAll('.checkbox'));
    let group_checkbox = this.parentNode.parentNode.querySelector('.all_check');

    let is_checked = checkboxes[0].checked;
    let is_changed = false;

    checkboxes.forEach(e => {
        if(e.checked !== is_checked)
            is_changed = true;
    });

    if(is_changed) {
        group_checkbox.indeterminate = true;
    }
    else {
        group_checkbox.indeterminate = false;
        group_checkbox.checked = is_checked;
    }
}