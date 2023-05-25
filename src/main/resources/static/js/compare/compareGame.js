export class CompareGame {
    constructor(canvas, context) {
        this.canvas = canvas;
        this.context = context;

        this.font_family = "Nunito";
        this.font_size = 0;
        this.border_weight = 0;

        this.game = {
            start: false,
            wait: false,
            ui_on: false,
            right: 0,
            wrong: 0,
            max: 0,
            max_difficulty: 0,
            right_answer: 0,
            operation_index: 0,
            operation_difficulty: 0,
        };

        this.timer = {
            time: 0,
            time_difficulty: 0,
            format_time: "00:00",
            font_size: 0,
            x: 0,
            y: 0,
            width: 0,
            height: 0,
            text_x: 0,
            text_y: 0,
            back_color: "white",
            timer_id: 0
        }

        this.indicator = {
            colors: ["white", "#fa0000", "green"],
            color_index: 0
        }

        this.examples = [
            {
                back_color: "aquamarine", x: 0, y: 0, width: 0, height: 0,
                text: "?", text_x: 0, text_y: 0
            },
            {
                back_color: "cadetblue", x: 0, y: 0, width: 0, height: 0,
                text: "=", text_x: 0, text_y: 0
            },
            {
                back_color: "aquamarine", x: 0, y: 0, width: 0, height: 0,
                text: "?", text_x: 0, text_y: 0
            }
        ];

        this.operation = [
            (a, b) => { return { res: a + b, text: a + " + " + b } },
            (a, b) => { return { res: a - b, text: a + " - " + b } },
            (a, b) => { return { res: a * b, text: a + " * " + b } },
            (a, b) => { return { res: a / b, text: a + " / " + b } }
        ];

        const tick = new Audio();
        tick.src = '/audio/tick.mp3';
        this.tick = tick;

        const bell = new Audio();
        bell.src = '/audio/bell.wav';
        this.bell = bell;
    }

    getOptimalTextSize(text, text_size, width) {
        this.context.font = text_size + "px " + this.font_family;
        let checkText = this.context.measureText(text).width;

        while (checkText > width) {
            text_size--;
            this.context.font = text_size + "px " + this.font_family;
            checkText = this.context.measureText(text).width;
        }

        return text_size;
    }

    updateTimerSize() {
        this.timer.width = this.canvas.width / 5 * 2;
        this.timer.height = this.canvas.height / 5;

        if (this.canvas.width > this.canvas.height) {
            this.timer.x = this.canvas.width / 10 * 3;
            this.timer.y = 0;
        }
        else {
            this.timer.x = 0;
            this.timer.y = this.timer.height * 2;
        }

        this.timer.text_x = this.timer.x + this.timer.width / 2;
        this.timer.text_y = this.timer.y + this.timer.height / 1.7;

        this.timer.font_size = this.getOptimalTextSize(
            this.timer.format_time,
            this.timer.height,
            this.timer.width
        ) - 5;
    }

    updateExamplesSize() {
        if (this.canvas.width > this.canvas.height) {
            this.examples.forEach((e) => {
                e.width = this.canvas.width / 5 * 2;
                e.height = this.canvas.height;
                e.y = 0;
            });
            this.examples[1].width /= 2;
            this.examples[1].x = this.examples[0].width;
            this.examples[2].x = this.canvas.width - this.examples[2].width;
        }
        else {
            this.examples.forEach((e) => {
                e.height = this.canvas.height / 5 * 2;
                e.width = this.canvas.width;
                e.x = 0;
            });
            this.examples[1].height /= 2;
            this.examples[1].y = this.examples[0].height;
            this.examples[2].y = this.canvas.height - this.examples[2].height;
        }

        this.examples.forEach((e) => {
            e.text_x = e.x + e.width / 2;
            e.text_y = e.y + e.height / 1.65;
        });

        if (this.canvas.width < this.canvas.height) {
            this.examples[1].text_x += this.examples[1].width / 5;
        }

        this.font_size = Math.min(
            (this.getOptimalTextSize(this.examples[0].text, this.examples[0].height, this.examples[0].width) - 8),
            (this.getOptimalTextSize(this.examples[1].text, this.examples[1].height, this.examples[1].width) - 8),
            (this.getOptimalTextSize(this.examples[2].text, this.examples[2].height, this.examples[2].width) - 8)
        );
    }

    updateSize() {
        this.updateExamplesSize();
        this.updateTimerSize();
        this.border_weight = Math.min(this.canvas.height / 10, this.canvas.width / 10);
    }

    setFormatTime(seconds) {
        let s = (seconds % 60).toString();
        let m = Math.floor(seconds / 60 % 60).toString();
        this.timer.format_time = `${m.padStart(2, '0')}:${s.padStart(2, '0')}`;
    }

    getDifficulty() {
        return Math.floor(((this.game.operation_difficulty +
            this.timer.time_difficulty +
            this.game.max_difficulty) * 100) / 14);
    }

    setOperations(operation_index) {
        this.game.operation_index = Number(operation_index);
        switch (this.game.operation_index) {
            case 0:
                this.game.operation_difficulty = 1;
                break;
            case 1:
                this.game.operation_difficulty = 4;
                break;
            case 2 || 3:
                this.game.operation_difficulty = 2;
                break;
            case 4 || 5:
                this.game.operation_difficulty = 3;
                break;
        }
    }

    setTime(time) {
        this.timer.time = time * 1000;
        this.setFormatTime(0);
        switch (Number(time)) {
            case 30:
                this.timer.time_difficulty = 4;
                break;
            case 60:
                this.timer.time_difficulty = 3;
                break;
            case 120:
                this.timer.time_difficulty = 2;
                break;
            case 180:
                this.timer.time_difficulty = 1;
                break;
        }
    }

    setMax(max) {
        this.game.max = Number(max) + 1;
        switch (this.game.max - 1) {
            case 5:
                this.game.max_difficulty = 1;
                break;
            case 10:
                this.game.max_difficulty = 2;
                break;
            case 20:
                this.game.max_difficulty = 3;
                break;
            case 30:
                this.game.max_difficulty = 4;
                break;
            case 50:
                this.game.max_difficulty = 5;
                break;
            case 100:
                this.game.max_difficulty = 6;
                break;
        }
    }

    randomVal(max, min = 0) { return Math.floor(Math.random() * (max - min) + min); }

    generateExemple(index) {
        return this.operation[index](
            this.randomVal(this.game.max, 1),
            this.randomVal(this.game.max, 1)
        );
    }

    generateTask() {
        let ex1, ex2, a, b;

        if (this.game.operation_index === 0) {
            a = this.randomVal(this.game.max, 1);
            ex1 = { res: a, text: a }
            b = this.randomVal(this.game.max, 1);
            ex2 = { res: b, text: b }
        }
        else if (this.game.operation_index === 1) {
            ex1 = this.generateExemple(this.randomVal(this.operation.length));
            ex2 = this.generateExemple(this.randomVal(this.operation.length));
        }
        else {
            ex1 = this.generateExemple(this.game.operation_index - 2);
            ex2 = this.generateExemple(this.game.operation_index - 2);
        }

        this.game.right_answer = ex1.res > ex2.res ? 0 : ex1.res < ex2.res ? 2 : 1;

        this.examples[0].text = ex1.text;
        this.examples[2].text = ex2.text;

        this.updateExamplesSize();
    }

    startGame(timer_panel) {
        let counter = 3;
        this.game.start = true;

        const countdown = new Promise(resolve => {
            timer_panel.value = counter--;
            this.tick.play();

            this.timer.timer_id = setInterval(() => {
                timer_panel.value = counter--;

                if (counter >= 0) {
                    this.tick.play();
                }
                else if (counter < 0) {
                    clearInterval(this.timer.timer_id);
                    this.bell.play();
                    timer_panel.classList.remove("_timer");
                    timer_panel.value = "Перевірка";
                    resolve(counter + (this.timer.time / 1000) + 1);
                }
            }, 1000);
        });

        countdown.then(counter => {
            this.game.right = 0;
            this.game.wrong = 0;

            this.game.ui_on = true;
            this.setFormatTime(counter--);
            this.generateTask();
            this.updateExamplesSize();

            this.timer.timer_id = setInterval(() => {
                this.setFormatTime(counter--);

                if (counter == 0) {
                    clearInterval(this.timer.timer_id);
                    this.timer.timer_id = setTimeout(() => {
                        this.bell.play();

                        this.game.start = false;
                        this.game.wait = true;
                        this.game.ui_on = false;

                        timer_panel.disabled = false;

                        this.examples[0].text = "?";
                        this.examples[2].text = "?";
                        this.updateExamplesSize();

                        this.setFormatTime(counter);
                    }, 1000);
                }
            }, 1000);

        });
    }

    stopGame(timer_panel) {
        clearInterval(this.timer.timer_id);
        clearTimeout(this.timer.timer_id);

        this.game.right = 0;
        this.game.wrong = 0;

        this.game.start = false;
        this.game.wait = false;
        this.game.ui_on = false;

        this.examples[0].text = "?";
        this.examples[2].text = "?";
        this.updateExamplesSize();

        timer_panel.disabled = true;
        timer_panel.value = "Перевірка";

        this.setFormatTime(0);
    }

    checkUserClick(click) {
        if (this.game.ui_on) {
            let rect = this.canvas.getBoundingClientRect();
            const x = click.clientX - rect.left;
            const y = click.clientY - rect.top;

            this.examples.forEach((e, index) => {
                if (x >= e.x && y >= e.y && x <= (e.x + e.width) && y <= (e.y + e.height)) {
                    this.checkUserInput(index);
                }
            });
        }
    }

    checkUserKeyDown(key) {
        if (this.game.ui_on) {
            switch (key) {
                case "KeyA":
                    this.checkUserInput(0);
                    break;
                case "KeyD":
                    this.checkUserInput(2);
                    break;
                case "KeyW":
                    this.checkUserInput(1);
                    break;
            }
        }
    }

    checkUserInput(input) {
        if (input === this.game.right_answer) {
            this.game.right++;
            this.indicator.color_index = 2;
        }
        else {
            this.game.wrong++;
            this.indicator.color_index = 1;
        }
        setTimeout(() => {
            this.indicator.color_index = 0;
        }, 200);
        this.generateTask();
    }

    checkGame() {
        if (this.game.wait) {
            let examples_count = this.game.right + this.game.wrong;
            let correct = this.game.right * 100 / examples_count;
            let speed = ((examples_count / (this.timer.time / 1000)) * 100) / (1 / (this.getDifficulty() / 30));
            console.log(examples_count / (this.timer.time / 1000));
            console.log(speed);
            console.log(correct);
            if(speed > 100) speed = 0;
            else speed -= 100;
            return Math.round(correct + speed);
        }
        return 0;
    }

    updateGameStat(timer_panel) {
        if (this.game.start || this.game.wait) this.stopGame(timer_panel);
        else this.startGame(timer_panel);
    }

    renderTimer() {
        this.context.fillStyle = this.timer.back_color;
        this.context.fillRect(this.timer.x, this.timer.y, this.timer.width, this.timer.height);

        this.context.font = this.timer.font_size + "px " + this.font_family;
        this.context.fillStyle = "black";
        this.context.fillText(this.timer.format_time, this.timer.text_x, this.timer.text_y);
    }

    renderRects() {
        this.context.font = this.font_size + "px " + this.font_family;
        this.context.textAlign = "center";
        this.context.textBaseline = "middle";

        this.examples.forEach((e) => {
            this.context.fillStyle = e.back_color;
            this.context.fillRect(e.x, e.y, e.width, e.height);
            this.context.fillStyle = "black";
            this.context.fillText(e.text, e.text_x, e.text_y);
        });
    }

    renderBorder() {
        this.context.strokeStyle = this.indicator.colors[this.indicator.color_index];
        this.context.lineWidth = this.border_weight;
        this.context.strokeRect(0, 0, this.canvas.width, this.canvas.height);
    }

    render() {
        this.renderRects();
        if (this.indicator.color_index !== 0) this.renderBorder();
        this.renderTimer();
    }
}