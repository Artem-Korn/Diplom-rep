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

// Array.from(document.querySelectorAll('.tr_checkbox'))
//     .forEach(e => {
//         e.addEventListener('click', tr => {
//             let checkboxes = tr.getElementsByClassName('checkbox')
//         });
//     });

function checkGroup(){
    let table = this.parentNode.parentNode.parentNode.parentNode;

    Array.from(table.querySelectorAll('.single_checkbox'))
        .forEach(e => {
            if(e.checked !== this.checked) {
                e.checked = this.checked
                checkSingle.call(e);
            }
        });
}

function checkSingle(){
    let table = this.parentNode.parentNode.parentNode.parentNode.parentNode;

    Array.from(table.parentNode.querySelectorAll('.single_checkbox'))
        .forEach(e => {
            if(e.value === this.value && e.checked !== this.checked) {
                e.checked = this.checked;
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
    }
    else {
        group_checkbox.indeterminate = false;
        group_checkbox.checked = is_checked;
    }
}