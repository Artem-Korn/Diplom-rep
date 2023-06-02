delete from mark_table;

insert into mark_table(id, date, difficulty, game_name, mark, task_id, user_id) values
(1, '2023-03-09', 20, 'Таблиця Шульте', 100, null, 1),
(2, '2023-03-09', 30, 'Знайди крота', 90, null, 1),
(3, '2023-03-09', 40, 'Де більше?', 80, null, 1);

alter sequence mark_table_seq restart with 10;