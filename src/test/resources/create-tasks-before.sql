delete from user_task_table;
delete from task_table;

insert into task_table(id, difficulty, game_name, options, user_id) values
(1, 29, 'Таблиця Шульте', '3;3;0;1;0;1;5', 1),
(2, 31, 'Знайди крота', '3;3;0;1;3;2;0;0', 1),
(3, 49, 'Де більше?', '0;5;30', 1);

insert into user_task_table(user_id, task_id) values
(1, 1), (1, 2), (1,3);

alter sequence task_table_seq restart with 10;