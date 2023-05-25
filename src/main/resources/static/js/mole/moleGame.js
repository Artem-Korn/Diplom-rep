export class MoleGame {
    constructor(canvas, context) {
        this.canvas = canvas;
        this.context = context;
        this.font_family = "Nunito"

        this.game = {
            start: false,
            show: true,
            wait: false,
            is_demo: false,
            hide_grid: false,
            hide_difficulty: 0,
            speed: 0,
            speed_difficulty: 0,
            steps_count: 0,
            steps_difficulty: 0,
            timer_id: 0
        }

        this.navigator = {
            canvas_part: 3,
            width: this.canvas.width / 3,
            padding: 0
        }

        this.steps = {
            pos_x: 0,
            pos_y: 0,
            font_size: 0,
            max: 4,
            value: 0
        }

        this.arrow = {
            pos_x: 0,
            pos_y: 0,
            size: 0,
            weight: 0,
            direction: 0
        }

        this.grid = {
            pos_x: 0,
            pos_y: 0,
            width: 0,
            rows: 0,
            columns: 0,
            color: "green",
            grid_difficulty: 0
        }

        this.cell = {
            size: 0,
            render_size: 0,
            padding: 0,
            text_size: 0,
            line_width: 0
        }

        this.mole = {
            pos_x: 0,
            pos_y: 0,
            index: 0,
            row: 0,
            column: 0,
            frame: 10,
            animation_id: 0
        }

        const mole_sprite_sheet = new Image();
        mole_sprite_sheet.src = "/img/games/mole_sprite_sheet.png";
        this.mole_sprite_sheet = mole_sprite_sheet;

        const tick = new Audio();
        tick.src = '/audio/tick.mp3';
        this.tick = tick;

        const bell = new Audio();
        bell.src = '/audio/bell.wav';
        this.bell = bell;

        this.voice_numbers = [];
        for (let i = 0; i < this.steps.max; i++) {
            this.voice_numbers[i] = new Audio();
            this.voice_numbers[i].src = '/audio/mole/num' + (i + 1) + '.mp3';
        }

        this.voice_directions = [];
        for (let i = 0; i < 4; i++) {
            this.voice_directions[i] = new Audio();
            this.voice_directions[i].src = '/audio/mole/dir' + i + '.mp3';
        }
    }

    updateSize() {
        this.navigator.width = this.canvas.width / this.navigator.canvas_part;
        this.navigator.padding = this.navigator.width / 4;

        this.grid.width = this.canvas.width - this.navigator.width;

        this.cell.size = Math.min(this.grid.width / this.grid.columns, this.canvas.height / this.grid.rows);
        this.cell.render_size = this.cell.size / 1.1;
        this.cell.padding = (this.cell.size - this.cell.render_size) / 2;
        this.cell.text_size = this.cell.size / 2;
        this.cell.line_width = this.cell.size / 30;

        this.mole.pos_x = this.mole.column * this.cell.size + this.cell.padding;
        this.mole.pos_y = this.mole.row * this.cell.size + this.cell.padding;

        this.grid.pos_x = (this.grid.width - this.cell.size * this.grid.columns) / 2;
        this.grid.pos_y = (this.canvas.height - this.cell.size * this.grid.rows) / 2;

        this.arrow.pos_x = this.canvas.width - this.navigator.width / 2;
        this.arrow.pos_y = this.canvas.height / 4;
        this.arrow.size = Math.min(this.canvas.height / 6, this.navigator.width / 2.8);
        this.arrow.weight = this.arrow.size / 4;

        this.steps.pos_x = this.canvas.width - this.navigator.width / 2;
        this.steps.pos_y = this.canvas.height - this.canvas.height / 5.2;

        this.steps.font_size = this.canvas.height / 2;
        this.context.font = this.steps.font_size + "px " + this.font_family;
        let checkText = this.context.measureText(this.steps.max).width;
        while (checkText > this.navigator.width - this.navigator.padding) {
            this.steps.font_size--;
            this.context.font = this.steps.font_size + "px " + this.font_family;
            checkText = this.context.measureText(this.steps.max).width;
        }
    }

    getDifficulty() {
        return this.game.is_demo ? 0 : (Math.floor(((this.game.hide_difficulty +
            this.game.speed_difficulty +
            this.game.steps_difficulty +
            this.grid.grid_difficulty) * 100) / 24));
    }

    setGrid(rows, columns) {
        this.grid.rows = rows;
        this.grid.columns = columns;

        this.grid.grid_difficulty = Number(rows) + Number(columns) - 4;

        this.mole.row = Math.floor(this.mole.index / this.grid.columns);
        this.mole.column = this.mole.index % this.grid.columns;

        this.updateSize();
    }

    updateMolePos() {
        this.mole.row = Math.floor(this.mole.index / this.grid.columns);
        this.mole.column = this.mole.index % this.grid.columns;

        this.mole.pos_x = this.mole.column * this.cell.size + this.cell.padding;
        this.mole.pos_y = this.mole.row * this.cell.size + this.cell.padding;
    }

    setMoleIndex(pos_index) {
        pos_index--;
        this.mole.index = pos_index;
        this.updateMolePos();
        return this.grid.rows * this.grid.columns;
    }

    setRandomMoleIndex() {
        this.mole.index = Math.floor(Math.random() * (this.grid.rows * this.grid.columns))
        this.updateMolePos();
    }

    setStepsCount(steps_count) {
        this.game.steps_count = steps_count;
        this.game.steps_difficulty = steps_count - 2;
    }

    setSpeed(speed) {
        this.game.speed = speed * 1000;
        this.game.speed_difficulty = 6 - speed;
    }

    setIsDemo(is_demo) {
        this.game.is_demo = is_demo !== '0';
    }

    setHideGrid(hide_grid) {
        this.game.hide_grid = hide_grid !== '0';
        this.game.hide_difficulty = this.game.hide_grid ? 5 : 0;
    }

    updateFrame(isHide) {
        let step = isHide ? -1 : 1;

        clearInterval(this.mole.animation_id);

        this.mole.animation_id = setInterval(() => {
            this.mole.frame += step;
            if (this.mole.frame < 0 || this.mole.frame > 9) {
                clearInterval(this.mole.animation_id);
                this.game.show = !isHide;
            }
        }, 1000 / 30);
    }

    voiceStep(direction, steps) {
        this.voice_numbers[steps - 1].play();

        setTimeout(() => {
            this.voice_directions[direction].play();
        }, this.voice_numbers[steps - 1].duration * 500);
    }

    moveMole() {
        switch (this.arrow.direction) {
            case 0:
                this.mole.index -= this.grid.columns * this.steps.value;
                break;
            case 1:
                this.mole.index += this.steps.value;
                break;
            case 2:
                this.mole.index += this.grid.columns * this.steps.value;
                break;
            case 3:
                this.mole.index -= this.steps.value;
                break;
            default:
                throw new Error("Помилка при виборі напрямку руху крота!")
        }
        this.updateMolePos();
    }

    getOpportunities() {
        let opportunities = [];

        opportunities.push({
            direction: 0,
            step: this.mole.row
        });
        opportunities.push({
            direction: 1,
            step: this.grid.columns - this.mole.column - 1
        });
        opportunities.push({
            direction: 2,
            step: this.grid.rows - this.mole.row - 1
        });
        opportunities.push({
            direction: 3,
            step: this.mole.column
        });

        return opportunities.filter(element => element.step !== 0);
    }

    makeStep() {
        let opportunities = this.getOpportunities();

        let random = Math.floor(Math.random() * opportunities.length);
        this.arrow.direction = opportunities[random].direction;
        this.steps.value = Math.floor(Math.random() * (Math.min(opportunities[random].step, this.steps.max)) + 1);

        this.moveMole();
        this.voiceStep(this.arrow.direction, this.steps.value);
    }

    startGame(timer_panel) {
        let counter = 3;
        this.game.start = true;

        this.updateFrame(true);

        const countdown = new Promise(resolve => {
            timer_panel.value = counter--;
            this.tick.play();

            this.game.timer_id = setInterval(() => {
                timer_panel.value = counter--;

                if (counter >= 0) {
                    this.tick.play();
                }
                else if (counter < 0) {
                    clearInterval(this.game.timer_id);
                    this.bell.play();
                    resolve(counter + 1);
                }
            }, 1000);
        });

        countdown.then(counter => {
            timer_panel.classList.remove("_timer");
            timer_panel.value = "Перевірка";

            this.makeStep();
            counter++;

            this.game.timer_id = setInterval(() => {
                this.makeStep();
                counter++;

                if (counter == this.game.steps_count) {
                    clearInterval(this.game.timer_id);
                    this.game.timer_id = setTimeout(() => {
                        this.bell.play();
                        this.arrow.direction = 0;
                        this.steps.value = 0;
                        this.game.start = false;
                        this.game.wait = true;
                        timer_panel.disabled = false;
                    }, this.game.speed);
                }
            }, this.game.speed);

        });
    }

    stopGame(timer_panel) {
        clearInterval(this.mole.animation_id);
        clearInterval(this.game.timer_id);
        clearTimeout(this.game.timer_id);

        if (this.mole.frame < 10) {
            this.game.show = true;
            this.updateFrame(false);
        }

        this.game.start = false;
        this.game.wait = false;

        timer_panel.disabled = true;
        timer_panel.value = "Перевірка";

        this.arrow.direction = 0;
        this.steps.value = 0;
    }

    updateGameStat(timer_panel) {
        if (this.game.start || this.game.wait) this.stopGame(timer_panel);
        else this.startGame(timer_panel);
    }

    checkGame(answer) {
        if (this.game.wait) {
            this.game.show = true;
            this.updateFrame(false);

            let answer_index = Number(answer) - 1;

            if(answer_index === this.mole.index) {
                return 100;
            }
            else {
                let answer_row = Math.floor(answer_index / this.grid.columns);
                let answer_column = answer_index % this.grid.columns;

                if(Math.abs(this.mole.row - answer_row) <= 1 && Math.abs(this.mole.column - answer_column) <= 1) {
                    return 50;
                }
            }
        }
        return 0;
    }

    renderGrid() {
        this.context.fillStyle = this.grid.color;
        this.context.fillRect(0, 0, this.grid.width, this.canvas.height);

        this.context.fillStyle = "black";
        this.context.strokeStyle = "black";
        this.context.font = this.cell.text_size + "px " + this.font_family;
        this.context.lineWidth = this.cell.line_width;
        this.context.textAlign = "center";
        this.context.textBaseline = "middle";

        let y;

        for (let r = 0; r < this.grid.rows; r++) {
            y = r * this.cell.size + this.grid.pos_y;
            for (let c = 0; c < this.grid.columns; c++) {
                this.context.save();
                this.context.translate(c * this.cell.size + this.grid.pos_x, y);

                this.context.strokeRect(
                    this.cell.padding,
                    this.cell.padding,
                    this.cell.render_size,
                    this.cell.render_size
                );

                this.context.fillText(
                    r * this.grid.columns + c + 1,
                    this.cell.size / 2,
                    this.cell.size / 1.8
                );

                this.context.restore();
            }
        }
    }

    renderArrow() {
        this.context.save();
        this.context.translate(this.arrow.pos_x, this.arrow.pos_y);

        this.context.strokeStyle = "black";

        this.context.rotate((Math.PI * 90 * this.arrow.direction) / 180);

        this.context.lineWidth = this.arrow.weight;
        this.context.lineCap = 'round';
        this.context.lineJoin = 'round';

        this.context.beginPath();
        this.context.moveTo(0, this.arrow.size);
        this.context.lineTo(0, -this.arrow.size);
        this.context.lineTo(this.arrow.size, 0);
        this.context.moveTo(0, -this.arrow.size);
        this.context.lineTo(-this.arrow.size, 0);
        this.context.stroke();

        this.context.restore();
    }

    renderStepsCount() {
        this.context.save();
        this.context.translate(this.steps.pos_x, this.steps.pos_y);

        this.context.fillStyle = "black";
        this.context.font = this.steps.font_size + "px " + this.font_family;
        this.context.textAlign = "center";
        this.context.textBaseline = "middle";
        this.context.fillText(this.steps.value, 0, 0);

        this.context.restore();
    }

    renderMoleCell() {
        this.context.strokeStyle = "red";

        this.context.save();
        this.context.translate(this.mole.pos_x + this.grid.pos_x, this.mole.pos_y + this.grid.pos_y);

        this.context.fillStyle = this.grid.color;
        this.context.fillRect(0, 0, this.cell.render_size, this.cell.render_size);

        this.context.strokeRect(0, 0, this.cell.render_size, this.cell.render_size);

        this.context.fillStyle = "red";
        this.context.font = this.cell.text_size / 2 + "px " + this.font_family;

        this.context.fillText(this.mole.index + 1, this.cell.size / 2.15, this.cell.size / 4);

        this.renderMole((this.cell.size - this.cell.size / 1.3) / 3, this.cell.size / 4.5, this.cell.size / 1.3);

        this.context.restore();
    }

    renderMole(x, y, size) {
        this.context.drawImage(
            this.mole_sprite_sheet,
            this.mole.frame * 400, 0, 400, 400,
            x, y, size, size
        );
    }

    render() {
        if (!this.game.hide_grid || !this.game.start) this.renderGrid();
        if (this.game.show || this.game.is_demo) this.renderMoleCell();
        this.renderArrow();
        this.renderStepsCount();
    }
}