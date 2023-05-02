function screenUpdate() {
    let canvas = document.getElementById("game_canvas");
    let screen = document.getElementById("screen");
    canvas.width = screen.clientWidth;
    canvas.height = screen.clientHeight;
}

window.addEventListener("load", () => {
    preloader.classList.add("_hide");
    screenUpdate();
});

window.addEventListener("resize", screenUpdate);

btn_start.addEventListener("click", function () {
    const btn_check = document.getElementById("btn_check");
    if (this.classList.contains("_clicked")) {
        disabledAll(false);
        this.value = "Старт";
        this.classList.remove("_clicked");
        btn_check.classList.remove("_timer");

    }
    else {
        disabledAll(true);
        this.value = "Стоп";
        this.classList.add("_clicked");
        btn_check.disabled = true;
        btn_check.classList.add("_timer");
    }
});

function disabledAll(disabled) {
    let selects = document.getElementsByClassName("_active");
    for (let i = 0; i < selects.length; i++) selects[i].disabled = disabled;
}