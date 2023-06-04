const is_task = btn_check.type === "submit";

function screenUpdate() {
    let canvas = document.getElementById("game_canvas");
    let screen = document.getElementById("screen");
    canvas.width = screen.clientWidth;
    canvas.height = screen.clientHeight;
}

window.addEventListener("load", () => {
    preloader.classList.add("_hide");
    disabledAll(false);
    screenUpdate();
});

window.addEventListener("resize", screenUpdate);
document.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        e.preventDefault();
    }
})

document.getElementById("btn_start").addEventListener("click", function () {
    const btn_check = document.getElementById("btn_check");
    if (this.classList.contains("_clicked")) {
        disabledAll(false);
        this.value = "Старт";
        this.classList.remove("_clicked");
        btn_check.classList.remove("_timer");
        btn_check.disabled = true;
    }
    else {
        disabledAll(true);
        this.value = "Стоп";
        this.classList.add("_clicked");
        btn_check.classList.add("_timer");
        btn_check.disabled = true;
        if(is_task) this.classList.add("_hide");
    }
});

document.getElementById("btn_check").addEventListener("click", function() {
    if(!is_task) btn_check.disabled = true;
});

if(is_task) {
    document.getElementById("check_form").onsubmit = function (e) {
        e.preventDefault();
        fetch(this.action, {
            method: "post",
            body: new FormData(this)
        });
        btn_check.disabled = true;
        modal_mark.innerHTML = mark.value > -1 ? mark.value : 'Зроблено';
        modal_result.classList.add("open");
    }

    window.addEventListener('keydown', (e) => {
        if(e.key === 'Escape') modal_result.classList.remove("open");
    });

    document.getElementById("modal_content").addEventListener('click', (e) => {
        e._isClickWithInModal = true;
    });
    document.getElementById("modal_result").addEventListener('click', (e) => {
        if(!e._isClickWithInModal) e.currentTarget.classList.remove("open");
    });

    document.getElementById("modal_close").addEventListener("click", function() {
        modal_result.classList.remove("open");
    });
}

function disabledAll(disabled) {
    let selects = document.getElementsByClassName("_active");
    for (let i = 0; i < selects.length; i++) selects[i].disabled = disabled;
}