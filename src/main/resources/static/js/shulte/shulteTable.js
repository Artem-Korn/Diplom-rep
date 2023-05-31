export class ShulteTable {
    constructor(canvas, context) {
        this.canvas = canvas;
        this.context = context;

        this.header = {
            canvas_part: 5,
            height: this.canvas.height / 7,
            task_0: "",
            task_1: "",
            font_family: "Nunito",
            padding: 5
        }

        this.game = {
            type: "",
            topic_index: 0,
            count_difficulty: 0,
            show: false,
            wait: false,
            is_colorful: false,
            is_colorful_difficulty: 0,
            time: 0,
            time_difficulty: 0,
            timeout_id: 0
        }

        this.grid = {
            rows: 0,
            columns: 0,
            cell_w: 0,
            cell_h: 0,
            colors: [],
            grid_difficulty: 0
        }

        this.sequence_topics = [];

        let numbers = [];
        for (let i = 0; i < 49; i++) { numbers.push(i + 1); }
        let alphabet = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯ";

        this.sequence_topics.push({
            name: "цифри",
            elements: numbers
        });
        this.sequence_topics.push({
            name: "великі літери",
            elements: alphabet.split("")
        });
        this.sequence_topics.push({
            name: "малі літери",
            elements: alphabet.toLowerCase().split("")
        });

        this.counting_topics = [];
        this.counting_topics.push({
            name: "фігури",
            elements: ["квадрат", "зірка", "коло", "трикутник", "ромб", "п'ятикутник"]
        });
        this.counting_topics.push({
            name: "тварини",
            elements: ["собака", "кіт", "кінь", "слон", "лев", "лисиця"]
        });

        this.selected_index_arr = [];
        this.created_value_arr = [];

        const elements_sheet = new Image();
        elements_sheet.src = "/img/games/elements_sheet.png"
        this.elements_sheet = elements_sheet;

        this.element_sheet_fragment_size = 100;

        const tick = new Audio();
        tick.src = '/audio/tick.mp3';
        this.tick = tick;

        const bell = new Audio();
        bell.src = '/audio/bell.wav';
        this.bell = bell;
    }

    isStarted() {
        return (this.game.show || this.game.wait);
    }

    getOptimalTextSize(text, text_size, width) {
        this.context.font = text_size + "px " + this.header.font_family;
        let checkText = this.context.measureText(text).width;

        while (checkText > width) {
            text_size--;
            this.context.font = text_size + "px " + this.header.font_family;
            checkText = this.context.measureText(text).width;
        }

        return text_size;
    }

    updateSize() {
        this.header.height = this.canvas.height / this.header.canvas_part;
        this.grid.cell_w = this.canvas.width / this.grid.columns;
        this.grid.cell_h = (this.canvas.height - this.header.height) / this.grid.rows;
    }

    setHeaderText(type, topic_index, element_count) {
        this.game.type = type;
        this.game.topic_index = topic_index;

        switch (this.game.type) {
            case "0":
                this.header.task_0 = "Знайти усі "
                    + this.sequence_topics[this.game.topic_index].name
                    + " у порядку:";
                this.header.task_1 = Math.floor(Math.random() * 2) === 0 ? "спадання" : "зростання"
                return null;
            case "1":
                let opp_arr = this.counting_topics[this.game.topic_index].elements.slice();
                if (opp_arr === undefined) throw new Error("Помилка у виборі виду завдання!");
                let values_arr = [];
                let rand_index;

                this.selected_index_arr = [];
                this.header.task_0 = "Порахувати усі об'єкти (\""
                    + this.counting_topics[this.game.topic_index].name
                    + "\") за ознаками: ";
                this.header.task_1 = "";

                for (let i = 0; i < element_count; i++) {
                    rand_index = Math.floor(Math.random() * opp_arr.length);

                    values_arr.push(opp_arr[rand_index]);
                    opp_arr.splice(rand_index, 1);

                    this.selected_index_arr.push(this.counting_topics[this.game.topic_index].elements.indexOf(values_arr[i]));

                    this.header.task_1 += values_arr[i] + ", ";
                }
                this.header.task_1 = this.header.task_1.slice(0, -2);
                this.game.count_difficulty = element_count * 2;
                return values_arr;
            default:
                throw new Error("Помилка у виборі типу завдання!")
        }
    }

    setGameTime(game_time) {
        this.game.time = game_time * 1000;

        if (Number(game_time) <= 30)
            this.game.time_difficulty = 10 - Number(game_time) / 5;
        else
            this.game.time_difficulty = 10 - ((Number(game_time) / 10) + 3);
    }

    setGrid(rows, columns) {
        this.grid.rows = rows;
        this.grid.columns = columns;

        this.grid.grid_difficulty = Number(rows) + Number(columns) - 4;

        this.grid.cell_w = this.canvas.width / columns;
        this.grid.cell_h = (this.canvas.height - this.header.height) / rows;
    }

    setGridColors(is_colorful) {
        this.game.is_colorful = is_colorful === '1';
        if (this.game.is_colorful) {
            let max_index = this.grid.rows * this.grid.columns;
            this.grid.colors = [];
            for (let index = 0; index < max_index; index++) {
                this.grid.colors[index] = "hsl(" + Math.floor(Math.random() * 361) + ", 100%, 75%)";
            }
            this.game.is_colorful_difficulty = 2;
        } else this.game.is_colorful_difficulty = 0;
    }

    setSequence() {
        let rand_index, value;
        let max_index = this.grid.rows * this.grid.columns;
        let opp_arr = this.sequence_topics[this.game.topic_index].elements.slice(0, max_index);
        this.created_value_arr = [];

        while (opp_arr.length < max_index) opp_arr.push(" ");

        for (let index = 0; index < max_index; index++) {
            rand_index = Math.floor(Math.random() * opp_arr.length);
            value = opp_arr[rand_index];
            opp_arr.splice(rand_index, 1);
            this.created_value_arr.push(value);
        }
    }

    setCounting() {
        let max_index = this.grid.rows * this.grid.columns;
        let max_rand = this.counting_topics[this.game.topic_index].elements.length;
        this.created_value_arr = [];

        for (let index = 0; index < max_index; index++) {
            this.created_value_arr.push(Math.floor(Math.random() * max_rand));
        }
    }

    getDifficulty() {
        return this.game.type === '0' ? 0 : Math.floor(((this.game.time_difficulty +
            this.game.is_colorful_difficulty +
            this.game.count_difficulty +
            this.grid.grid_difficulty) * 100) / 33);
    }

    startGame(timer_panel) {
        let counter = this.game.time / 1000;
        timer_panel.value = counter--;
        this.tick.play();
        this.game.show = true;

        this.game.interval_id = setInterval(() => {
            timer_panel.value = counter--;

            if (counter >= 0) {
                this.tick.play();
            }
            else if (counter < 0) {
                clearInterval(this.game.interval_id);
                this.bell.play();
                timer_panel.classList.remove("_timer");
                timer_panel.value = "Перевірка";
                timer_panel.disabled = false;
                this.game.show = false;
                this.game.wait = true;
            }
        }, 1000);

        switch (this.game.type) {
            case "0":
                this.setSequence();
                break;
            case "1":
                this.setCounting();
                break;
            default:
                throw new Error("Помилка при старті! Невірний тип завдання!")
        }
    }

    stopGame(timer_panel) {
        clearInterval(this.game.interval_id);
        timer_panel.value = "Перевірка";
        this.game.show = false;
        this.game.wait = false;
    }

    updateGameStat(timer_panel) {
        if (!this.isStarted()) this.startGame(timer_panel)
        else this.stopGame(timer_panel);
    }

    checkGame(answers_arr) {
        if (this.isStarted()) {
            this.game.wait = false;
            this.game.show = true;

            switch (this.game.type) {
                case "0":
                    return -1;
                case "1":
                    let searching_elem, correct_answer;
                    let right = 0;

                    for (let i = 0; i < this.selected_index_arr.length; i++) {
                        searching_elem = this.selected_index_arr[i];
                        correct_answer = this.created_value_arr
                            .reduce((count, item) => count + (item === searching_elem), 0);

                        if (correct_answer === Number(answers_arr[i].value)) right += 100;
                        else {
                            let ans = Number(answers_arr[i].value);
                            let error = Math.abs(correct_answer - ans);

                            if(error < 10 && ans >= 0) right += (10 - error) * 10;
                        }
                    }
                    return Math.floor(right * 100 / (this.selected_index_arr.length * 100));
                default:
                    throw new Error("Помилка при старті! Невірний тип завдання!")
            }
        }
    }

    renderSequence() {
        this.context.fillStyle = "black";
        this.context.textBaseline = "middle";
        this.context.textAlign = "center";
        this.context.font = (this.grid.cell_h - this.header.padding * 5) + "px " + this.header.font_family;


        for (let r = 0; r < this.grid.rows; r++) {
            for (let c = 0; c < this.grid.columns; c++) {
                this.context.save();
                this.context.translate(c * this.grid.cell_w, this.header.height + r * this.grid.cell_h);

                this.context.fillText(
                    this.created_value_arr[this.grid.columns * r + c],
                    this.grid.cell_w / 2, this.grid.cell_h / 1.65
                );

                this.context.restore();
            }
        }
    }

    renderCounting() {
        let topic_index = this.game.topic_index * this.element_sheet_fragment_size;
        let img_size = Math.min(this.grid.cell_w, this.grid.cell_h);
        let padding_left = (this.grid.cell_w - img_size) / 2;
        let padding_top = (this.grid.cell_h - img_size) / 2;

        for (let r = 0; r < this.grid.rows; r++) {
            for (let c = 0; c < this.grid.columns; c++) {
                this.context.save();
                this.context.translate(c * this.grid.cell_w, this.header.height + r * this.grid.cell_h);

                this.context.drawImage(
                    this.elements_sheet,
                    this.created_value_arr[this.grid.columns * r + c] * this.element_sheet_fragment_size,
                    topic_index,
                    this.element_sheet_fragment_size, this.element_sheet_fragment_size,
                    padding_left, padding_top,
                    img_size, img_size
                );

                this.context.restore();
            }
        }
    }

    renderGame() {
        if (this.game.is_colorful) this.renderColorfulGrid();
        switch (this.game.type) {
            case "0":
                this.renderSequence();
                break;
            case "1":
                this.renderCounting();
                break;
            default:
                throw new Error("Помилка при рендері гри! Невірний тип завдання!")
        }
    }

    renderHeaderText() {
        this.header.height = this.canvas.height / this.header.canvas_part;

        let text_size = this.getOptimalTextSize(
            this.header.task_0.length > this.header.task_1.length ? this.header.task_0 : this.header.task_1,
            this.header.height / 3, this.canvas.width - this.header.padding * 2
        );

        this.context.fillStyle = "black";
        this.context.textBaseline = "middle";
        this.context.textAlign = "center";

        let start_y = (this.header.height - text_size * 2 - this.header.padding) / 2;

        this.context.fillText(this.header.task_0, this.canvas.width / 2, start_y + text_size / 2);
        this.context.fillStyle = "red";
        this.context.fillText(this.header.task_1, this.canvas.width / 2, start_y + text_size / 2 * 3 + this.header.padding);
    }

    renderGrid() {
        this.context.strokeStyle = "black";

        let x = 0, y = this.header.height;
        for (let r = 0; r < this.grid.rows; r++) {
            this.context.beginPath();
            this.context.moveTo(x, y);
            this.context.lineTo(this.canvas.width, y);
            this.context.stroke();
            y += this.grid.cell_h;
        }

        y = this.header.height;
        for (let c = 0; c < this.grid.columns - 1; c++) {
            x += this.grid.cell_w;
            this.context.beginPath();
            this.context.moveTo(x, y);
            this.context.lineTo(x, this.canvas.height);
            this.context.stroke();
        }
    }

    renderColorfulGrid() {
        for (let r = 0; r < this.grid.rows; r++) {
            for (let c = 0; c < this.grid.columns; c++) {
                this.context.save();
                this.context.translate(c * this.grid.cell_w, this.header.height + r * this.grid.cell_h);

                this.context.fillStyle = this.grid.colors[this.grid.columns * r + c];
                this.context.fillRect(0, 0, this.grid.cell_w, this.grid.cell_h);

                this.context.restore();
            }
        }
    }

    render() {
        this.renderHeaderText();
        if (this.game.show) this.renderGame();
        this.renderGrid();
    }
}